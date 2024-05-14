package com.example.readkami_beta.Activities;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.readkami_beta.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImageViewerActivity extends AppCompatActivity {

    private WebView webView;
    private StorageReference storageRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        // Retrieve parentFolderName and folderName from intent
        String parentFolderName = getIntent().getStringExtra("parentFolderName");
        String folderName = getIntent().getStringExtra("folderName");

        // Create the StorageReference
        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://readkami.appspot.com/" + parentFolderName + "/" + folderName);

        // Start WebView
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        // Load images into WebView
        loadImagesFromStorage();
    }

    private void loadImagesFromStorage() {
        storageRef.listAll().addOnSuccessListener(listResult -> {
            StringBuilder htmlContent = new StringBuilder("<html><body>");
            for (StorageReference item : listResult.getItems()) {
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    htmlContent.append("<img src='").append(uri.toString()).append("' style='width:100%; height:auto;'/>");
                    webView.loadDataWithBaseURL(null, htmlContent.toString(), "text/html", "utf-8", null);
                });
            }
            htmlContent.append("</body></html>");
        });
    }
}
