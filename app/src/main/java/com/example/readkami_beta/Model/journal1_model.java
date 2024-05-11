package com.example.readkami_beta.Model;

public class journal1_model {
    private String title;
    private String authors;
    private String folderName; // Add folderName field
    private String pdfFileName; // Add pdfFileName field

    public journal1_model(String title, String authors, String folderName, String pdfFileName) {
        this.title = title;
        this.authors = authors;
        this.folderName = folderName; // Initialize folderName
        this.pdfFileName = pdfFileName; // Initialize pdfFileName
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getFolderName() {
        return folderName; // Getter method for folderName
    }

    public String getPdfFileName() {
        return pdfFileName; // Getter method for pdfFileName
    }
}
