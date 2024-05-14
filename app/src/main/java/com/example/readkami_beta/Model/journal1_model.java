package com.example.readkami_beta.Model;

public class journal1_model {
    private String title;
    private String authors;
    private String folderName;
    private String pdfFileName;
    private String parentFolderName; // Add parentFolderName field

    public journal1_model(String title, String authors, String folderName, String pdfFileName, String parentFolderName) {
        this.title = title;
        this.authors = authors;
        this.folderName = folderName;
        this.pdfFileName = pdfFileName;
        this.parentFolderName = parentFolderName; // Initialize parentFolderName
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getPdfFileName() {
        return pdfFileName;
    }

    public String getParentFolderName() {
        return parentFolderName; // Getter method for parentFolderName
    }
}
