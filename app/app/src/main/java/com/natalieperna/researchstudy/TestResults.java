package com.natalieperna.researchstudy;

import java.util.ArrayList;

public class TestResults {
    int test;
    int lag;
    long start;
    long end;
    ArrayList<TimePoint> path;

    public TestResults() {
        this.test = 0;
        this.lag = 0;
        this.start = 0;
        this.end = 0;
        this.path = new ArrayList<TimePoint>();
    }

    public TestResults(int test, int lag) {
        this.test = test;
        this.lag = lag;
        this.start = 0;
        this.end = 0;
        this.path = new ArrayList<TimePoint>();
    }
}
