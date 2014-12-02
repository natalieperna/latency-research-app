package com.natalieperna.researchstudy;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ParticipantResults {
    private String id;
    private File file;
    public char gender, handedness;
    public int age;
    public ArrayList<TestResults> tests;

    public ParticipantResults() {
        id = UUID.randomUUID().toString();
        File directory = new File(Environment.getExternalStorageDirectory() + "/ResearchResults");
        directory.mkdirs();
        file = new File(directory, id + ".txt");
        tests = new ArrayList<TestResults>();
    }

    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write("ID: " + id + "\n");
            osw.write("Time: " + new Date() + "\n");
            osw.write("Gender: " + gender + "\n");
            osw.write("Handedness: " + handedness + "\n");
            osw.write("Age: " + age + "\n");
            osw.write("\n");
            for (TestResults test : tests) {
                osw.write("Test #" + test.test + "\n");
                osw.write("Lag: " + test.lag + "ms \n");
                osw.write("Start: " + test.start + "ms \n");
                osw.write("End: " + test.end + "ms \n");
                osw.write("Path:\n");
                for (TimePoint point : test.path) {
                    osw.write("(" + point.x + ", " + point.y + ") @ " + point.t + "\n");
                }
                osw.write("\n");
            }
            osw.flush();
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
