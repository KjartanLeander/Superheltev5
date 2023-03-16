package com.example.superheltev5.DTO;

public class SuperheroDTO {
    private String heroName;
    private String realName;
    private int creationYear;
    private int superpowerID;
    private String cityID;

    public SuperheroDTO(String heroName, String realName, int creationYear, int superpowerID, String cityID) {
        this.heroName = heroName;
        this.realName = realName;
        this.creationYear = creationYear;
        this.superpowerID = superpowerID;
        this.cityID = cityID;
    }

    public String getHeroName() {
        return heroName;
    }

    public String getRealName() {
        return realName;
    }

    public int getCreationYear() {
        return creationYear;
    }

    public int getSuperpowerID() {
        return superpowerID;
    }

    public String getCityID() {
        return cityID;
    }
}

