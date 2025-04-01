package com.example.project;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    TextView userName, totalWorkouts;
    ProgressBar progressBar;
    Button btnStartWorkout, btnViewProgress, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DatabaseHelper(this);
        userName = findViewById(R.id.userName);
        totalWorkouts = findViewById(R.id.totalWorkouts);
        progressBar = findViewById(R.id.progressBar);
        btnStartWorkout = findViewById(R.id.btnStartWorkout);
        btnViewProgress = findViewById(R.id.btnViewProgress);
        btnSettings = findViewById(R.id.btnSettings);

        loadUserData();

        btnStartWorkout.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, WorkoutSelectionActivity.class)));

        btnViewProgress.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ProgressActivity.class)));

        btnSettings.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
    }

    private void loadUserData() {
        Cursor res = db.getUserData();
        if (res.moveToFirst()) {
            userName.setText("Welcome, " + res.getString(1)); // User Name
        }

        int workoutCount = db.getWorkoutCount();
        totalWorkouts.setText("Total Workouts: " + workoutCount);
        progressBar.setProgress(workoutCount * 10); // Example Progress Calculation
    }
}