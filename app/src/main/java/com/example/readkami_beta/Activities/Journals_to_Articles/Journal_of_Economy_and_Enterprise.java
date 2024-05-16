package com.example.readkami_beta.Activities.Journals_to_Articles;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

public class Journal_of_Economy_and_Enterprise extends AppCompatActivity {

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusBarColor));
        }
        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://readkami.appspot.com/Journal of Economy and Enterprise");

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
        articleList.add(new journal1_model("Technical Efficiency of Cacao (Theobroma cacao) Farms in Davao City, Philippines", "Bonna May Ponpon, John Vianne Murcia 1-13", "Technical_Efficiency_of_Cacao", "Technical_Efficiency_of_Cacao.pdf", "Journal of Economy and Enterprise"));
        articleList.add(new journal1_model("Selection of Insurance Company Among Variable Unit Life Policy Holders: An Exploratory Factor Analysis", "Christian Paul Moyon, Marvin Cruz 14-25", "Selection_of_Insurance", "Selection_of_Insurance.pdf", "Journal of Economy and Enterprise"));
        articleList.add(new journal1_model("Agricultural Cooperatives’ Impact on its Members’ Socio-Economic Status: The Case of the Binhian ng Timog Kutabato Multi-Purpose Agricultural Cooperative", "Mario Campo Jr., Vicente Salvador Montaño", "Agricultural_Cooperative", "Agricultural_Cooperative.pdf", "Journal of Economy and Enterprise"));

        adapter = new journal1_adapter(articleList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(Journal_of_Economy_and_Enterprise.this, ImageViewerActivity.class);
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
