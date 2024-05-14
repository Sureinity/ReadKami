package com.example.readkami_beta.Activities.Journals_to_Articles;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readkami_beta.Activities.ImageViewerActivity;
import com.example.readkami_beta.Adapter.journal1_adapter;
import com.example.readkami_beta.Model.journal1_model;
import com.example.readkami_beta.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Gomanan extends AppCompatActivity {

    private TextView descriptionTextView;
    private ImageView expandCollapseButton;
    private RecyclerView recyclerView;
    private journal1_adapter adapter;
    private List<journal1_model> articleList;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_1st);

        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://readkami.appspot.com/Gomanan");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        descriptionTextView = findViewById(R.id.description);
        expandCollapseButton = findViewById(R.id.expandCollapseButton);

        expandCollapseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (descriptionTextView.getMaxLines() == 2) {
                    descriptionTextView.setMaxLines(Integer.MAX_VALUE);
                    expandCollapseButton.setImageResource(R.drawable.baseline_arrow_drop_up_24);
                } else {
                    descriptionTextView.setMaxLines(2);
                    expandCollapseButton.setImageResource(R.drawable.baseline_arrow_drop_down_24);
                }
            }
        });

        articleList = new ArrayList<>();
        articleList.add(new journal1_model("Narratives of Domestic Violence against Women: A Multiple Case Study", "Mary Grace Aclon, Jayver Padulip, Ronald Taparan, Erick Baloran, Jenny Hernan, Erick Pajaron", "Narrative_of_Domestic", "Narrative_of_Domestic.pdf", "Gomanan"));
        articleList.add(new journal1_model("Tales of Homosexual Daughters: Insights and Lessons", "Jojie Bryan Gomintong, Angelica Patlunag, Marc Rowil Valdez, Erick Baloran, Jenny Hernan, Eric Pajaron", "Tales_of_Homosexual_Daughters", "Tales_of_Homosexual_Daughters.pdf", "Gomanan"));
        articleList.add(new journal1_model("Travails of Direct Sellers in Davao del Sur: A Phenomenological Inquiry", "Quiny Christine Deparine, Anna May Canoy, Joanna Rañoa, Erick Baloran, Jenny Hernan, Eric Pajaron", "Travails_of_Direct_Sellers", "Travails_of_Direct_Sellers.pdf", "Gomanan"));
        articleList.add(new journal1_model("Social Responsibility Practices and Customer Satisfaction among Cooperatives in Davao del Sur", "Cherry Jay Donque, May Anne Orbita, April Kaye Diez, Eric Paul Susada, Erick Baloran, Jenny Hernan, Eric Pajaron", "Social_Responsibility_Practices", "Social_Responsibility_Practices.pdf", "Gomanan"));
        articleList.add(new journal1_model("Narratives of Domestic Violence against Women: A Multiple Case Study", "Mary Grace Aclon, Jayver Padulip, Ronald Taparan, Erick Baloran, Jenny Hernan, Erick Pajaron", "Narrative_of_Domestic", "Narrative_of_Domestic.pdf", "Gomanan"));

        adapter = new journal1_adapter(articleList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(Gomanan.this, ImageViewerActivity.class);
            intent.putExtra("parentFolderName", articleList.get(position).getParentFolderName());
            intent.putExtra("folderName", articleList.get(position).getFolderName());
            startActivity(intent);
        });

        adapter.setOnDownloadClickListener(position -> {
            String pdfFileName = articleList.get(position).getPdfFileName();
            downloadPDF(pdfFileName);
        });
    }

    private void downloadPDF(String pdfFileName) {
        if (pdfFileName == null || pdfFileName.isEmpty()) {
            Toast.makeText(this, "Invalid PDF file name", Toast.LENGTH_SHORT).show();
            return;
        }

        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File appFolder = new File(downloadsDir, "ReadKami");
        if (!appFolder.exists() && !appFolder.mkdirs()) {
            Toast.makeText(this, "Failed to create app directory", Toast.LENGTH_SHORT).show();
            return;
        }

        File localFile = new File(appFolder, pdfFileName);
        if (localFile.exists()) {
            Toast.makeText(this, "File is already downloaded", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference pdfRef = storageRef.child(pdfFileName);
        pdfRef.getFile(localFile)
                .addOnSuccessListener(taskSnapshot -> Toast.makeText(this, "PDF file downloaded successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to download PDF file", Toast.LENGTH_SHORT).show());
    }
}
