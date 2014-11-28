package com.natalieperna.researchstudy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {

    private int test = 0;
    private CanvasView canvas;
    private View circle;
    private Button button;
    private TextView titleView, textView;

    private Results results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvas = (CanvasView) findViewById(R.id.canvas);

        circle = findViewById(R.id.circle);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        titleView = (TextView) findViewById(R.id.titleView);
        textView = (TextView) findViewById(R.id.textView);


        results = new Results();
    }

    private void goToNext() {

        canvas.clear();
        if (test == 0) { // Practice
            test++;
            titleView.setText("Test #" + test);
            textView.setText("Trace the circle below.");
            button.setText("Next Test ❯");
            circle.setVisibility(View.VISIBLE);
        } else if (test > 0 && test < 24) { // Tests
            test++;
            titleView.setText("Test #" + test);
        } else if (test == 24) {
            test++;
            titleView.setText("Test #" + test);
            button.setText("Finish");
        } else if (test == 25) {
            new AlertDialog.Builder(this)
                    .setTitle("Thank You")
                    .setMessage("Thank you for participating in the study. Please return the device to the researcher.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            results.save();
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
}
