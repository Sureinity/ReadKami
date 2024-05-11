package com.example.readkami_beta.Model;

public class journal1_model {
    private String title;
    private String authors;
    private String folderName; // Add folderName field

    public journal1_model(String title, String authors, String folderName) {
        this.title = title;
        this.authors = authors;
        this.folderName = folderName; // Initialize folderName
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
}
