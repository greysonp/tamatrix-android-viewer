package com.greysonparrelli.tamatrix.models;

public class Tama {
    public int id;
    public String pixels;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tama tama = (Tama) o;

        return id == tama.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
