package com.natalieperna.researchstudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;


public class SurveyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
    }

    public void goToPractice(View view) {
        char gender, handedness;
        int age;

        // Get gender from radio buttons
        if (((RadioButton)findViewById(R.id.genderFemale)).isChecked()) {
            gender = 'F';
        } else if (((RadioButton)findViewById(R.id.genderMale)).isChecked()) {
            gender = 'M';
        } else {
            gender = 'O';
        }

        // Get handedness from radio buttons
        if (((RadioButton)findViewById(R.id.handednessLeft)).isChecked()) {
            handedness = 'L';
        } else if (((RadioButton)findViewById(R.id.handednessRight)).isChecked()) {
            handedness = 'R';
        } else {
            handedness = 'O';
        }

        // Get age from input
        age = Integer.parseInt(((EditText)findViewById(R.id.age)).getText().toString());

        // Switch to main activity and pass survey results
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("gender", gender);
        intent.putExtra("handedness", handedness);
        intent.putExtra("age", age);

        startActivity(intent);
    }
}
