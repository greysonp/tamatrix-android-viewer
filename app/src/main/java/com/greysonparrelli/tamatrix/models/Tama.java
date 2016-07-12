package com.greysonparrelli.tamatrix.models;

public class Tama {
    public int id;
    public String pixels;

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        int start = 0;
        for (int i = 0; i < 32; i++) {
            out.append(pixels.substring(start, start + 48)).append('\n');
            start += 48;
        }
        String outString = out.toString();
        outString = outString.replaceAll("A", " ");
        outString = outString.replaceAll("B", ".");
        outString = outString.replaceAll("C", "u");
        outString = outString.replaceAll("D", "m");
        return outString;
    }
}