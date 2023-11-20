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