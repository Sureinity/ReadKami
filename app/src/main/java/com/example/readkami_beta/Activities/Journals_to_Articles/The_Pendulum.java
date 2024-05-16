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

public class The_Pendulum extends AppCompatActivity {

    private TextView descriptionTextView;
    private ImageView expandCollapseButton;
    private RecyclerView recyclerView;
    private journal1_adapter adapter;
    private List<journal1_model> articleList;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_4th);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusBarColor));
        }

        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://readkami.appspot.com/The Pendulum");

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
        articleList.add(new journal1_model("Three Green Wheels of Life: The Venture of Tricycle Drivers in Tagum City and their Financial Burden During Covid-19 Pandemic", "Jessa Mae Baraquia, Tiffani Rose Batiao, Jovit Cain", "Three_Green_Wheel", "Three_Green_Wheel.pdf", "The Pendulum"));
        articleList.add(new journal1_model("Influence of Facebook Live Selling on Purchase Intention among Online Shoppers", "Jane Rose Egot, Edsel Jay Perez, Elira Ji Villegas, Maria Teresa Ozoa", "Influence_of_Facebook_Live_Selling", ".pdf", "The Pendulum"));
        articleList.add(new journal1_model("‘P**** I**’: A Pragmatic Analysis On President Rodrigo Duterte’s First and Last State of the Nation Addresses (SONAS 2016 - 2021)", "Arvin James Lója Lupas, Earl Jones Muico", "Pragmatic_Analysis_On_President_Rodrigo_Duterte", "Pragmatic_Analysis_On_President_Rodrigo_Duterte.pdf", "The Pendulum"));
        articleList.add(new journal1_model("Investigating Social Loafing among Student-Researchers: A Mixed-Methods Inquiry", "CherryLouisse Noreen Tapiz, Hazel Faith Opeña, Jesus Emmanuel Saclot, Jehane Sadane", "Investigating_Social_Loafing", "Investigating_Social_Loafing.pdf", "The Pendulum"));
        articleList.add(new journal1_model("Cao-nalyzer: An Android-Based Mold Detection in Cacao Beans Using Faster R-CNN Algorithm", "Benjamin Mahinay Jr, Jedy Matt Tabasco, Zhyr Narciso, Dandreb Inguito", "Cao-nalyzer", "Cao-nalyzer.pdf", "The Pendulum"));

        adapter = new journal1_adapter(articleList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(The_Pendulum.this, ImageViewerActivity.class);
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
