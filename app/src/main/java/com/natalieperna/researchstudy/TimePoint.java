package com.natalieperna.researchstudy;

/**
 * Represents a drawing coordinate (x, y) and the time, t, that it was collected
 */
public class TimePoint {
    float x, y; // Coordinate
    long t; // Time at that position

    TimePoint(float x, float y, long t) {
        this.x = x;
        this.y = y;
        this.t = t;
    }
}
