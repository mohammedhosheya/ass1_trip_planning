package com.example.ass1_tripplanning;

public class TripTask {

    private int id;
    private String title;
    private String date;
    private String type;
    private String notes;
    private boolean isDone;

    public TripTask(int id, String title, String date, String type,
                    String notes, boolean isDone) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.type = type;
        this.notes = notes;
        this.isDone = isDone;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}