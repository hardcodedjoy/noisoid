/*

MIT License

Copyright © 2024 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

*/

package com.hardcodedjoy.noisoid;

abstract public class Source {

    static protected final double PI = Math.PI;
    static protected final double TWO_PI = Math.PI * 2;

    static private int nextId = 0;
    protected int id;

    protected final Object sync;

    protected int sampleRate;
    protected double k;
    protected double alpha;

    protected float volumeL;
    protected float volumeR;

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }

    public Source() {
        sync = new Object();
        setId(nextId);
        nextId++;
        if(nextId < 0) { nextId = 0; }

        this.volumeL = 1.0f;
        this.volumeR = 1.0f;
    }

    public void setVolume(float left, float right) {
        synchronized (sync) {
            this.volumeL = left;
            this.volumeR = right;
        }
    }

    synchronized public void setFrequency(float frequency) {
        synchronized (sync) {
            this.k = TWO_PI * frequency / this.sampleRate;
        }
    }

    protected float getNextSample() { return 0; } // to  be overridden by implementations

    public void readTo(float[] buf, int offset, int len) { // also can be overridden if needed
        float sample;
        for(int i=0; i<len; ) {
            synchronized (sync) {
                sample = getNextSample();
                buf[offset + (i++)] += sample * volumeL; // left
                buf[offset + (i++)] += sample * volumeR; // right
                // using "+=" because we are mixing with what is already in buf
            }
        }
    }
}
