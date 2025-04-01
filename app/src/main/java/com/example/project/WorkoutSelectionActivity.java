package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class WorkoutSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_selection);

        ListView workoutList = findViewById(R.id.workoutList);
        String[] workouts = {"Chest", "Arms", "Legs", "Back", "Shoulders"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, workouts);
        workoutList.setAdapter(adapter);

        workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WorkoutSelectionActivity.this, WorkoutDetailActivity.class);
                intent.putExtra("workout_type", workouts[position]);
                startActivity(intent);
            }
        });
    }

}
