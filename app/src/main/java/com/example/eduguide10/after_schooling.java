package com.example.eduguide10;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class after_schooling extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_after_schooling);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button buttonInter = findViewById(R.id.inter);
        Button buttonDiploma = findViewById(R.id.diploma);
        Button buttonIIT = findViewById(R.id.iiit);
        Button buttonITI = findViewById(R.id.iti);

        buttonInter.setOnClickListener(v -> openCoursesActivity("Inter"));
        buttonDiploma.setOnClickListener(v -> openCoursesActivity("Diploma"));
        buttonIIT.setOnClickListener(v -> openCoursesActivity("IIT"));
        buttonITI.setOnClickListener(v -> openCoursesActivity("ITI"));
    }
    private void openCoursesActivity(String category) {
        Intent intent = new Intent(after_schooling.this,related_courses.class);
        intent.putExtra("CATEGORY", category); // Pass the selected category
        startActivity(intent);
    }
}