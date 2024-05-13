package com.example.readkami_beta.Activities.Journals_to_Articles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readkami_beta.Activities.ImageViewerActivity;
import com.example.readkami_beta.Adapter.journal1_adapter;
import com.example.readkami_beta.Model.journal1_model;
import com.example.readkami_beta.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UM_Digos_Research_Journal extends AppCompatActivity {

    private TextView descriptionTextView;
    private ImageView expandCollapseButton; //For expand-collapse button
    private RecyclerView recyclerView;
    private journal1_adapter adapter;
    private List<journal1_model> articleList;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_2nd);

        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://readkami.appspot.com/UM Digos Research Journal");

        // Initialize RecyclerView and layout manager
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Description Expansion feature
        descriptionTextView = findViewById(R.id.description);
        expandCollapseButton = findViewById(R.id.expandCollapseButton);

        expandCollapseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (descriptionTextView.getMaxLines() == 2) {
                    // Expand the description
                    descriptionTextView.setMaxLines(Integer.MAX_VALUE);
                    expandCollapseButton.setImageResource(R.drawable.baseline_arrow_drop_up_24);
                } else {
                    // Collapse the description
                    descriptionTextView.setMaxLines(2);
                    expandCollapseButton.setImageResource(R.drawable.baseline_arrow_drop_down_24);
                }
            }
        });

        // Initialize sample article data
        articleList = new ArrayList<>();
        articleList.add(new journal1_model("Technical Efficiency of Cacao (Theobroma cacao) Farms in Davao City, Philippines", "Bonna May Ponpon, John Vianne Murcia    1-13", "Technical_Efficiency_of_Cacao", "Technical_Efficiency_of_Cacao.pdf"));
        articleList.add(new journal1_model("Selection of Insurance Company Among Variable Unit Life Policy Holders: An Exploratory Factor Analysis", "Christian Paul Moyon, Marvin Cruz    14-25", "Selection_of_Insurance", "Selection_of_Insurance.pdf"));
        articleList.add(new journal1_model("Agricultural Cooperatives’ Impact on its Members’ Socio-Economic Status: The Case of the Binhian ng Timog Kutabato Multi-Purpose Agricultural Cooperative", "Mario Campo Jr., Vicente Salvador Montaño", "Agricultural_Cooperative", "Agricultural_Cooperative.pdf"));

        // Initialize adapter and set it to RecyclerView
        adapter = new journal1_adapter(articleList);
        recyclerView.setAdapter(adapter);

        // Set click listener for RecyclerView items
        adapter.setOnItemClickListener(position -> {
            // Handle click event
            // Launch ImageViewerActivity and pass the folder name
            Intent intent = new Intent(UM_Digos_Research_Journal.this, ImageViewerActivity.class);
            intent.putExtra("folderName", articleList.get(position).getFolderName());
            startActivity(intent);
        });

        // Set click listener for download icon
        adapter.setOnDownloadClickListener(position -> {
            // Handle download event
            String pdfFileName = articleList.get(position).getPdfFileName();
            downloadPDF(pdfFileName);
        });
    }

    private void downloadPDF(String pdfFileName) {
        // Check if the PDF file name is valid
        if (pdfFileName == null || pdfFileName.isEmpty()) {
            Toast.makeText(this, "Invalid PDF file name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the "Downloads" directory
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // Create a folder for your app if it doesn't exist
        File appFolder = new File(downloadsDir, "ReadKami");
        if (!appFolder.exists()) {
            if (!appFolder.mkdirs()) {
                // Failed to create directory
                Toast.makeText(this, "Failed to create app directory", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Create a local file to store the downloaded PDF in the app folder
        File localFile = new File(appFolder, pdfFileName);

        // Check if the file already exists
        if (localFile.exists()) {
            // File already downloaded
            Toast.makeText(this, "File is already downloaded", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a reference to the PDF file in Firebase Storage
        StorageReference pdfRef = storageRef.child(pdfFileName);

        // Download the PDF file to the local file
        pdfRef.getFile(localFile)
                .addOnSuccessListener(taskSnapshot -> {
                    // File downloaded successfully
                    Toast.makeText(this, "PDF file downloaded successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure: Unable to download the PDF file
                    Toast.makeText(this, "Failed to download PDF file", Toast.LENGTH_SHORT).show();
                });
    }


}