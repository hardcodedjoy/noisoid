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

package com.hardcodedjoy.dev.noisoid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.hardcodedjoy.noisoid.Noisoid;
import com.hardcodedjoy.noisoid.SawtoothGeneratorAsc;
import com.hardcodedjoy.noisoid.SawtoothGeneratorDesc;
import com.hardcodedjoy.noisoid.SineGenerator;
import com.hardcodedjoy.noisoid.Source;
import com.hardcodedjoy.noisoid.SquareGenerator;
import com.hardcodedjoy.noisoid.TriangleGenerator;

public class MainActivity extends Activity {

    private Source source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // we use our own title bar in "layout_main"
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Noisoid noisoid = new Noisoid(48000, 10);
        noisoid.start();

        long delay = 100;

        runDelayed(() -> {
            source = new SineGenerator(48000, 440);
            source.setVolume(0.8f, 0.8f);
            noisoid.addSource(source);
        }, delay);
        delay += 1000;

        runDelayed(() -> {
            noisoid.removeSource(source.getId());
            source = new TriangleGenerator(48000, 440);
            source.setVolume(0.8f, 0.8f);
            noisoid.addSource(source);
        }, delay);
        delay += 1000;

        runDelayed(() -> {
            noisoid.removeSource(source.getId());
            source = new SawtoothGeneratorAsc(48000, 440);
            source.setVolume(0.8f, 0.8f);
            noisoid.addSource(source);
        }, delay);
        delay += 1000;

        runDelayed(() -> {
            noisoid.removeSource(source.getId());
            source = new SawtoothGeneratorDesc(48000, 440);
            source.setVolume(0.8f, 0.8f);
            noisoid.addSource(source);
        }, delay);
        delay += 1000;

        runDelayed(() -> {
            noisoid.removeSource(source.getId());
            source = new SquareGenerator(48000, 440);
            source.setVolume(0.8f, 0.8f);
            noisoid.addSource(source);
        }, delay);
        delay += 1000;

        runDelayed(() -> noisoid.removeSource(source.getId()), delay);
        delay += 500;



        runDelayed(() -> {
            noisoid.removeSource(source.getId());
            source = new SquareGenerator(48000, 440.00f);
            source.setVolume(0.8f, 0.8f);
            noisoid.addSource(source);
        }, delay);
        delay += 100;

        runDelayed(() -> source.setFrequency(493.88f), delay);
        delay += 100;

        runDelayed(() -> source.setFrequency(523.25f), delay);
        delay += 100;

        runDelayed(() -> source.setFrequency(587.33f), delay);
        delay += 100;

        runDelayed(() -> source.setFrequency(659.25f), delay);
        delay += 100;

        runDelayed(() -> source.setFrequency(698.45f), delay);
        delay += 100;

        runDelayed(() -> source.setFrequency(783.99f), delay);
        delay += 100;

        runDelayed(() -> {
            noisoid.removeSource(source.getId());
            noisoid.stop();
        }, delay);
    }

    static private void runDelayed(Runnable r, long delay) {
        new Thread() {
            @Override
            public void run() {
                try { Thread.sleep(delay); } catch (Exception e) { /**/ }
                r.run();
            }
        }.start();
    }
}