package com.example.project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class WorkoutDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);

        TextView workoutTitle = findViewById(R.id.workoutTitle);
        EditText repsInput = findViewById(R.id.repsInput);
        EditText setsInput = findViewById(R.id.setsInput);
        Button startWorkoutBtn = findViewById(R.id.startWorkoutBtn);

        String workoutType = getIntent().getStringExtra("workout_type");
        workoutTitle.setText(workoutType);

        startWorkoutBtn.setOnClickListener(v -> {
            String reps = repsInput.getText().toString();
            String sets = setsInput.getText().toString();
            if (!reps.isEmpty() && !sets.isEmpty()) {
                Toast.makeText(this, "Workout Started! Reps: " + reps + " Sets: " + sets, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter reps and sets", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
