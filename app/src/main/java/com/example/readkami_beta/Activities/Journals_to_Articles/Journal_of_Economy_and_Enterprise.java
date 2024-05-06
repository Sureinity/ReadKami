package com.example.readkami_beta.Activities.Journals_to_Articles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readkami_beta.Adapter.journal1_adapter;
import com.example.readkami_beta.Model.journal1_model;
import com.example.readkami_beta.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class Journal_of_Economy_and_Enterprise extends AppCompatActivity {

    private TextView descriptionTextView;
    private ImageView expandCollapseButton;
    private RecyclerView recyclerView;
    private journal1_adapter adapter;
    private List<journal1_model> articleList;

    // Firebase Storage reference
    private StorageReference storageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_1st);

        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://readkami.appspot.com/Journal of Economy and Enterprise");

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
        // Initialize RecyclerView and layout manager
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize sample article data
        articleList = new ArrayList<>();
        articleList.add(new journal1_model("Title 1", "Author 1, Author 2"));
        articleList.add(new journal1_model("Title 2", "Author 3, Author 4"));

        // Initialize adapter and set it to RecyclerView
        adapter = new journal1_adapter(articleList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new journal1_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle click event
                // Retrieve the corresponding PDF from Firebase Storage and open it
                viewPdfFromStorage(articleList.get(position).getTitle());
            }
        });

    }
    // Define a method to retrieve and view PDF from Firebase Storage
    // Define a method to retrieve and view PDF from Firebase Storage
    private void viewPdfFromStorage(String articleTitle) {
        // Map article titles to corresponding PDF file names
        String pdfFileName;
        switch (articleTitle) {
            case "Title 1":
                pdfFileName = "Agricultural Cooperativ.pdf";
                break;
            case "Title 2":
                pdfFileName = "Selection of Insurance.pdf";
                break;
            case "Title 3":
                pdfFileName = "Technical Efficiency of Cacao.pdf";
                break;
            default:
                // Default to empty string if no matching PDF file name found
                pdfFileName = "";
                break;
        }

        // Construct the reference to the PDF file
        StorageReference pdfRef = storageRef.child(pdfFileName);

        // Download the PDF file to a temporary location
        pdfRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Open PDF using the obtained URI
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors
                Toast.makeText(Journal_of_Economy_and_Enterprise.this, "Failed to retrieve PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
