package com.example.superheltev5.DTO;

public class CountPowerDTO {
    public void setSuperHeroName(String superHeroName) {
        this.superHeroName = superHeroName;
    }


    public void setCountPower(int countPower) {
        this.countPower = countPower;
    }

    private String superHeroName;
    private int countPower;

    public String getSuperHeroName() {
        return superHeroName;
    }


    public int getCountPower() {
        return countPower;
    }

    public CountPowerDTO(String superHeroName, int countPower) {
        this.superHeroName = superHeroName;

        this.countPower = countPower;
    }


}
