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

public class Tinubdan extends AppCompatActivity {

    private TextView descriptionTextView;
    private ImageView expandCollapseButton;
    private RecyclerView recyclerView;
    private journal1_adapter adapter;
    private List<journal1_model> articleList;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_6th);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusBarColor));
        }

        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://readkami.appspot.com/Tinubdan");

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
        articleList.add(new journal1_model("Awareness and Practices on Solid Waste Management Among Grade 6 Pupils", "Nil Christopher Anania, Ma. Desiree Laurito, Gemma Biol, Ceazane Lou Quimpan", "Awareness_and_Practices_on_Solid_Waste_Management", "Awareness_and_Practices_on_Solid_Waste_Management.pdf", "Tinubdan"));
        articleList.add(new journal1_model("Photovoice Analysis on Environmental Issues through the Lens of BSED English Students", "Patricia Nadine L. Chico, Joyce Hernando", "Photovoice_Analysis", "Photovoice_Analysis.pdf", "Tinubdan"));
        articleList.add(new journal1_model("Epekto ng Kakayahan sa Pagtuturo sa Kaganyakan ng mga Mag-aaral sa Pag-aaral ng Asignaturang Filipino Dulot ng Pandemya", "Joyce Hernando, Glea Fe Cabudbud, Ryan Mangente, Jeala Perez", "Epekto_ng_Kakayahan", "Epekto_ng_Kakayahan.pdf", "Tinubdan"));
        articleList.add(new journal1_model("Initiatives to COVID-19 Cases: A Trend Analysis", "Liezel V. Chan, Leejane P. Cogollodo, Rhoebegen P. Burgos", "Initiatives_to_COVID-19", "Initiatives_to_COVID-19.pdf", "Tinubdan"));
        articleList.add(new journal1_model("Green Marketing and Consumer Behavior among Selected Fastfood Chain in Davao City", "Amelie L. Chico, Gerald Khein D. Nollora, Denmark B. Oxien, Karen Jane M. Yanoyan", "Green_Marketing_and_Consumer", "Green_Marketing_and_Consumer.pdf", "Tinubdan"));

        adapter = new journal1_adapter(articleList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(Tinubdan.this, ImageViewerActivity.class);
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
