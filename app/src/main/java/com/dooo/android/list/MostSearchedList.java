package com.dooo.android.list;

public class MostSearchedList {
    private int ID;
    private int Type;
    private String Title;
    private String Year;
    private String Thumbnail;
    private int contentType;

    public MostSearchedList(int ID, int type, String title, String year, String thumbnail, int contentType) {
        this.ID = ID;
        Type = type;
        Title = title;
        Year = year;
        Thumbnail = thumbnail;
        this.contentType = contentType;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }
}
