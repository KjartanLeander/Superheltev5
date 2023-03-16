package com.example.superheltev5.repository;

import com.example.superheltev5.DTO.CityDTO;
import com.example.superheltev5.DTO.CountPowerDTO;
import com.example.superheltev5.DTO.HeroPowerDTO;
import com.example.superheltev5.DTO.SuperheroDTO;
import com.example.superheltev5.Entity.Superhero;

import java.util.List;

public interface Irepository {
    public List<SuperheroDTO> getSuperheroes();
    public HeroPowerDTO heroPower(String name);
    public CityDTO heroCity(String name);
    public CountPowerDTO heroPowerCount(String name);

}
