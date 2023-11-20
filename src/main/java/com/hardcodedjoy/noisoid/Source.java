package com.hardcodedjoy.noisoid;

abstract public class Source {

    static private int nextId = 0;
    protected int id;

    protected float volumeL;
    protected float volumeR;

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }

    public Source() {
        setId(nextId);
        nextId++;
        if(nextId < 0) { nextId = 0; }

        this.volumeL = 1.0f;
        this.volumeR = 1.0f;
    }

    public void setVolume(float left, float right) {
        this.volumeL = left;
        this.volumeR = right;
    }

    abstract void readTo(float[] buf, int offset, int len);
}
