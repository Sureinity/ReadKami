package com.example.readkami_beta.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.readkami_beta.Fragments.BookmarkFragment;
import com.example.readkami_beta.Fragments.DownloadFragment;
import com.example.readkami_beta.Fragments.HistoryFragment;
import com.example.readkami_beta.Fragments.JournalFragment;
import com.example.readkami_beta.R;
import com.example.readkami_beta.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repFragment(new JournalFragment());


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.journal) {
                repFragment(new JournalFragment());
            }else if (item.getItemId() == R.id.download) {
                repFragment(new DownloadFragment());
            }

            return true;
        });
    }




    private void repFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }
}