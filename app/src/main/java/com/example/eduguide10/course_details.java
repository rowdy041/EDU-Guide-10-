package com.example.eduguide10;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class course_details extends AppCompatActivity {
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        // Initialize the layout
        layout = findViewById(R.id.courseDetailsLayout);

        // Get the course name from the intent (if passed from the previous activity)
        String courseName = getIntent().getStringExtra("COURSE_NAME");

        // Initialize the TextView for the course name (if you want to display it)
        TextView courseNameTextView = findViewById(R.id.courseNameTextView);
        courseNameTextView.setText(courseName);

        // Load the JSON data from the file
        String jsonString = loadJSONFromAsset();
        try {
            // Parse the JSON data
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject intermediateCourses = jsonObject.getJSONObject("intermediate");

            // Check if the specific course exists
            if (intermediateCourses.has(courseName)) {
                JSONObject courseDetails = intermediateCourses.getJSONObject(courseName);

                // Dynamically add content
                Iterator<String> keys = courseDetails.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    Object content = courseDetails.get(key);
                    addSectionToLayout(key, content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Function to load JSON data from the assets folder
    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("course_details.json"); // Assuming the file is in the 'assets' folder
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    // Function to dynamically add content to the layout
    private void addSectionToLayout(String title, Object content) {
        // Add title
        TextView titleTextView = new TextView(this);
        titleTextView.setText(title);
        titleTextView.setTextSize(20);
        titleTextView.setPadding(16, 16, 16, 8);
        layout.addView(titleTextView);

        // Handle content based on type
        if (content instanceof JSONObject) {
            // If the content is another JSONObject (nested structure)
            JSONObject contentObject = (JSONObject) content;
            Iterator<String> keys = contentObject.keys();
            while (keys.hasNext()) {
                String subKey = keys.next();
                try {
                    Object subContent = contentObject.get(subKey);
                    addSectionToLayout(subKey, subContent); // Recursive call to handle nested objects
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (content instanceof JSONArray) {
            // If the content is a JSONArray (list of items)
            JSONArray contentArray = (JSONArray) content;
            for (int i = 0; i < contentArray.length(); i++) {
                try {
                    String item = contentArray.getString(i);
                    TextView itemTextView = new TextView(this);
                    itemTextView.setText("- " + item);  // Prefix with a bullet point for list items
                    itemTextView.setPadding(32, 0, 16, 8);
                    layout.addView(itemTextView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (content instanceof String) {
            // If the content is a simple string (e.g., "about" field)
            TextView contentTextView = new TextView(this);
            contentTextView.setText(content.toString());
            contentTextView.setPadding(32, 0, 16, 8);
            layout.addView(contentTextView);
        }
    }
}
