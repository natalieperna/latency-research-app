package com.natalieperna.researchstudy;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.UUID;

public class Results {
    private String id;
    private File directory;
    private File file;
    private ArrayList<Point> path;

    public Results() {
        id = UUID.randomUUID().toString();
        directory = new File(Environment.getExternalStorageDirectory() + "/ResearchResults");
        directory.mkdirs();
        file = new File(directory, id + ".txt");
    }

    public void setPath(ArrayList<Point> path) {
        this.path = path;
    }

    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write("Participant ID: " + id + "\n");
            osw.write("That's all for now!");
            osw.flush();
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
