package com.example.readkami_beta.Activities;

import android.content.Intent;
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

        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://readkami.appspot.com/Journal of Economy and Enterprise");

        // Retrieve folder name from intent
        String folderName = getIntent().getStringExtra("folderName");

        // Initialize WebView
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        // Load images into WebView
        loadImagesFromStorage(folderName);
    }

    // Define a method to load images from Firebase Storage into WebView
    private void loadImagesFromStorage(String folderName) {
        // Construct the reference to the folder containing images
        StorageReference imagesRef = storageRef.child(folderName);

        // Load the HTML content to display images in WebView
        imagesRef.listAll().addOnSuccessListener(listResult -> {
            StringBuilder htmlContent = new StringBuilder("<html><body>");
            for (StorageReference item : listResult.getItems()) {
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    // Append image tags to the HTML content
                    htmlContent.append("<img src='").append(uri.toString()).append("' style='width:100%; height:auto;'/>");
                    // Load the HTML content into WebView
                    webView.loadDataWithBaseURL(null, htmlContent.toString(), "text/html", "utf-8", null);
                });
            }
            htmlContent.append("</body></html>");
        });
    }
}
