package com.example.superheltev5.DTO;

public class CityDTO {

    public String getCity() {
        return city;
    }

    public String getSuperHeroName() {
        return SuperHeroName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSuperHeroName(String superHeroName) {
        SuperHeroName = superHeroName;
    }

    public CityDTO(String city, String superHeroName) {
        this.city = city;
        SuperHeroName = superHeroName;
    }

    private String city;
    private String SuperHeroName;



}
