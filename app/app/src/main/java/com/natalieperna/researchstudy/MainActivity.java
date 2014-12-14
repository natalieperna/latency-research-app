package com.natalieperna.researchstudy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener {

    private int test;

    private CanvasView canvas;
    private View circle;
    private Button button;
    private TextView titleView, textView;

    private List<Integer> trials;
    private ParticipantResults results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        canvas = (CanvasView) findViewById(R.id.canvas);

        circle = findViewById(R.id.circle);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        titleView = (TextView) findViewById(R.id.titleView);
        textView = (TextView) findViewById(R.id.textView);

        test = 0;

        // Randomize lag order
        trials = Arrays.asList(
                0, 0, 0, 0, 0,
                50, 50, 50, 50, 50,
                100, 100, 100, 100, 100,
                250, 250, 250, 250, 250,
                500, 500, 500, 500, 500
        );
        Collections.shuffle(trials);

        results = new ParticipantResults();
        results.gender = intent.getCharExtra("gender", 'X');
        results.handedness = intent.getCharExtra("handedness", 'X');
        results.age = intent.getIntExtra("age", 0);
    }

    private void goToNext() {
        results.tests.add(canvas.getTest()); // Save current test results

        if (test < 25) {
            test++;
            canvas.newTest(test, trials.get(test - 1)); // Start next test
            updateView(test);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Thank You")
                    .setMessage("Thank you for participating in the study. Please return the device to the researcher.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            results.save();
                            restart();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            goToNext();
        }
    }

    private void updateView(int test) {
        titleView.setText("Test #" + test);
        textView.setText("Trace the circle below.");
        if (test < 25)
            button.setText("Next Test ❯");
        else
            button.setText("Finish");
        circle.setVisibility(View.VISIBLE);
    }

    private void restart() {
        Intent intent = new Intent(this, SurveyActivity.class);
        startActivity(intent);
    }
}
