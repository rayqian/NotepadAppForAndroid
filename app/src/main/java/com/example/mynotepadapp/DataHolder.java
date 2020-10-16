package com.example.mynotepadapp;

import java.io.Serializable;

public class DataHolder implements Serializable {
    private String title;
    private String content;

    DataHolder(String title, String content) {
        this.title = title;
        this.content = content;
    }

    String getTitle() {
        return title;
    }

    String getContent() {
        return content;
    }


}
