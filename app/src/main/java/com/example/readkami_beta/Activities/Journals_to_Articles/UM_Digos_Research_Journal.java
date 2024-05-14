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

public class UM_Digos_Research_Journal extends AppCompatActivity {

    private TextView descriptionTextView;
    private ImageView expandCollapseButton;
    private RecyclerView recyclerView;
    private journal1_adapter adapter;
    private List<journal1_model> articleList;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_2nd);

        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://readkami.appspot.com/UM Digos Research Journal");

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
        articleList.add(new journal1_model("An analysis of Paul Grice’s Maxims cooperative principle in sari-sari store conversation", "Khareen Culajara, Wendy Rose Laguerta","An_Analysis_of_Paul_Grice_Maxim", "An_Analysis_of_Paul_Grice_Maxim.pdf", "UM Digos Research Journal"));
        articleList.add(new journal1_model("Depiction of domestic violence in Nicholas Sparks' \"Safe Haven\": A feminist approach", "Khareen Culajara, Arly Khryss Joyce Obeso","Depiction_of_Domestic_Violence", "Depiction_of_Domestic_Violence.pdf", "UM Digos Research Journal"));
        articleList.add(new journal1_model("Light and shades: Political life of Vice Mayor Reynaldo S. Hermosisima", "Garnette Mae Balacy, Johanna Faith Botohan", "Light_and_Shades", "Light_and_Shades.pdf", "UM Digos Research Journal"));
        articleList.add(new journal1_model("Revenue collections and government expenditures of Barangay Zone II, Digos City", "Melody Malnegro, Shelyte Escabosa, Elda Joy Quirante, Aubrey Salarza, Rosemarie Sanchez",
                "Revenue_Collections", "Revenue_Collections.pdf", "UM Digos Research Journal"));
        articleList.add(new journal1_model("Transformational leadership and quality of work-life of faculty members of UM Digos College", "Leah Magdamo, Mary Grace Dolendo, Christine Anne Padilla, Riche James Abelleja",
                "Transformational_Leadership", "Transformational_Leadership.pdf", "UM Digos Research Journal"));

        adapter = new journal1_adapter(articleList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(UM_Digos_Research_Journal.this, ImageViewerActivity.class);
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
