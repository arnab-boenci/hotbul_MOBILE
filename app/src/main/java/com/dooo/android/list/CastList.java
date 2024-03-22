package com.dooo.android.list;

public class CastList {
    private String name;

    private long castid;
    private String role;
    private String image;

    public CastList(String name, String role, String image, long castid) {
        this.name = name;
        this.role = role;
        this.image = image;
        this.castid = castid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getCastid() {
        return castid;
    }

    public void setCastid(long castid) {
        this.castid = castid;
    }
}
