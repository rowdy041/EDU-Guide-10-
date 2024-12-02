package com.example.eduguide10;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class related_courses extends AppCompatActivity {
    private LinearLayout courseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_courses);

        courseLayout = findViewById(R.id.courseLayout);

        // Get the category passed from MainActivity
        String category = getIntent().getStringExtra("CATEGORY");

        // Dynamically add buttons based on the category
        showCourses(category);
    }

    private void showCourses(String category) {
        // Clear any previous courses
        courseLayout.removeAllViews();

        // Set up layout parameters for the buttons
        LayoutParams buttonLayoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.button_height));  // Use dimension for button height
        buttonLayoutParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.button_margin_top), 0, 0);

        // If category is "inter", load courses from JSON
        if ("inter".equalsIgnoreCase(category)) {
            // Get courses from the JSON file
            JSONArray coursesList = getCoursesFromJSON();

            // Dynamically add buttons for each course in the JSON list
            for (int i = 0; i < coursesList.length(); i++) {
                try {
                    String course = coursesList.getString(i);

                    Button courseButton = new Button(this);
                    courseButton.setText(course);  // Set the course name dynamically

                    // Set the text color, size, and other styles
                    courseButton.setTextColor(getResources().getColor(R.color.black));  // Use the color resource for text
                    courseButton.setTextSize(30);  // Set text size to 30sp (not dp)

                    // Set the background with rounded corners
                    GradientDrawable drawable = new GradientDrawable();
                    drawable.setShape(GradientDrawable.RECTANGLE);
                    drawable.setCornerRadius(30f);  // Set the corner radius to 30px
                    drawable.setColor(getResources().getColor(R.color.white));  // Set button color

                    // Apply the drawable as the background
                    courseButton.setBackground(drawable);

                    // Set additional styles
                    courseButton.setTypeface(courseButton.getTypeface(), android.graphics.Typeface.ITALIC);

                    // Apply layout parameters
                    courseButton.setLayoutParams(buttonLayoutParams);

                    // Set a unique ID for each button based on the course name
                    courseButton.setId(View.generateViewId());

                    // Set an OnClickListener to open the new activity
                    final String courseName = course;  // Get course name for Intent
                    courseButton.setOnClickListener(v -> {
                        // Create an Intent to start the CourseDetailsActivity
                        Intent intent = new Intent(related_courses.this, course_details.class);
                        intent.putExtra("COURSE_NAME", courseName);  // Pass the course name
                        startActivity(intent);
                    });

                    // Add the button to the layout
                    courseLayout.addView(courseButton);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // If the category is not "inter", show a generic list of courses with numbers
            for (int i = 1; i <= 8; i++) {
                Button courseButton = new Button(this);
                String courseName = "Course " + i;  // Generic course name

                courseButton.setText(courseName);

                // Set the text color, size, and other styles
                courseButton.setTextColor(getResources().getColor(R.color.black));
                courseButton.setTextSize(30);

                // Set the background with rounded corners
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setCornerRadius(30f);
                drawable.setColor(getResources().getColor(R.color.white));

                // Apply the drawable as the background
                courseButton.setBackground(drawable);

                // Apply layout parameters
                courseButton.setLayoutParams(buttonLayoutParams);

                // Set an OnClickListener
                courseButton.setOnClickListener(v -> {
                    // Create an Intent to start the CourseDetailsActivity with a generic course name
                    Intent intent = new Intent(related_courses.this, course_details.class);
                    intent.putExtra("COURSE_NAME", courseName);
                    startActivity(intent);
                });

                // Add the button to the layout
                courseLayout.addView(courseButton);
            }
        }
    }


    private JSONArray getCoursesFromJSON() {
        String json = null;
        try {
            // Load the JSON file from the assets folder
            InputStream is = getAssets().open("courses.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the bytes to a string
            json = new String(buffer, StandardCharsets.UTF_8);

            // Parse the JSON string
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getJSONArray("inter");
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }
}
