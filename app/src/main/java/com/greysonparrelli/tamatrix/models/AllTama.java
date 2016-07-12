package com.greysonparrelli.tamatrix.models;

import java.util.List;

public class AllTama {
    public int notama;
    public long lastseq;
    public List<Tama> tama;

    @Override
    public String toString() {
        return tama.get(0).toString();
    }
}
