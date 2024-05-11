package com.example.readkami_beta.Activities.Journals_to_Articles;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readkami_beta.Activities.ImageViewerActivity;
import com.example.readkami_beta.Adapter.journal1_adapter;
import com.example.readkami_beta.Model.journal1_model;
import com.example.readkami_beta.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Journal_of_Economy_and_Enterprise extends AppCompatActivity {

    private RecyclerView recyclerView;
    private journal1_adapter adapter;
    private List<journal1_model> articleList;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_1st);

        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://readkami.appspot.com/Journal of Economy and Enterprise");

        // Initialize RecyclerView and layout manager
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize sample article data
        articleList = new ArrayList<>();
        articleList.add(new journal1_model("Title 1", "Author 1, Author 2", "Agricultural_Cooperative"));
        articleList.add(new journal1_model("Title 2", "Author 3, Author 4", "Selection_of_Insurance"));

        // Initialize adapter and set it to RecyclerView
        adapter = new journal1_adapter(articleList);
        recyclerView.setAdapter(adapter);

        // Set click listener for RecyclerView items
        adapter.setOnItemClickListener(position -> {
            // Handle click event
            // Launch ImageViewerActivity and pass the folder name
            Intent intent = new Intent(Journal_of_Economy_and_Enterprise.this, ImageViewerActivity.class);
            intent.putExtra("folderName", articleList.get(position).getFolderName());
            startActivity(intent);
        });
    }
}
