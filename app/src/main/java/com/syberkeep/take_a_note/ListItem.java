package com.syberkeep.take_a_note;

public class ListItem {

    private String fileName;
    private String date;

    public ListItem(String fileName, String date) {
        this.fileName = fileName;
        this.date = date;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDate() {
        return date;
    }
}