package com.example.readkami_beta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.readkami_beta.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


  //  int[] journalImages = {R.drawable.journal1,R.drawable.journal2,R.drawable.journal3,R.drawable.journal4,R.drawable.journal5};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repFragment(new JournalFragment());

      /*  RecyclerView recyclerView = findViewById(R.id.frame_layout);
        setUpJournals();
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(this, journals);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.journal) {
                repFragment(new JournalFragment());
            } else if (item.getItemId() == R.id.bookmark) {
                repFragment(new BookmarkFragment());
            } else if (item.getItemId() == R.id.history) {
                repFragment(new HistoryFragment());
            }else if (item.getItemId() == R.id.download) {
                repFragment(new DownloadFragment());
            }

            return true;
        });
    }


    /*    private void setUpJournals(){
            String [] title = getResources().getStringArray(R.array.book_title);
            String [] subtitle = getResources().getStringArray(R.array.book_subtitle);
            String [] description = getResources().getStringArray(R.array.book_description);

            for(int i=0; i<title.length; i++){
                journals.add(new Journals(title[i],subtitle[i],description[i],journalImages[i]));
            }
        }*/




    private void repFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }
}