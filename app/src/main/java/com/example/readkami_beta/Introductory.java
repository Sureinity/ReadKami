package com.example.readkami_beta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.readkami_beta.Fragments.JournalFragment;

public class Introductory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusBarColor));
            // Initially load the JournalFragment

        Button button = findViewById(R.id.arrowButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of the JournalFragment
                Fragment journalFragment = new JournalFragment();

                // Replace the current fragment with the JournalFragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, journalFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }
}