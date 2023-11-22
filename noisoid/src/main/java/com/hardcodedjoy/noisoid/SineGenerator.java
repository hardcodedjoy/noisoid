/*

MIT License

Copyright © 2023 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

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

public class SineGenerator extends Source {

    static private final double TWO_PI = Math.PI * 2;

    private final double k;
    private double alpha;

    public SineGenerator(int sampleRate, float frequency) {
        this.k = TWO_PI * frequency / sampleRate;
    }

    public float getNextSample() {
        float res = (float) Math.sin(alpha);
        alpha += k;
        if(alpha > TWO_PI) { alpha -= TWO_PI; }
        return res;
    }

    @Override
    void readTo(float[] buf, int offset, int len) {
        float sample;
        for(int i=0; i<len; ) {
            sample = getNextSample();
            buf[offset + (i++)] += sample * volumeL; // left
            buf[offset + (i++)] += sample * volumeR; // right

            // using "+=" because we are mixing with what is already in buf
        }
    }
}