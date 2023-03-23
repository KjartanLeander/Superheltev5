package com.example.superheltev5.service;

import com.example.superheltev5.DTO.*;
import com.example.superheltev5.Entity.Superhero;
import com.example.superheltev5.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyService {

    MyRepository myRepository;

    @Autowired
    public MyService(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    public List<SuperheroDTO> getSuperheroes() {return myRepository.getSuperheroes();}
    public HeroPowerDTO heroPowerDTO(String name){return myRepository.heroPower(name);}

    public CityDTO cityDTO(String name){return myRepository.heroCity(name);}

    public CountPowerDTO countPowerDTO(String name){return myRepository.heroPowerCount(name);}

    public List<String> getCities(){
        return myRepository.getCities();
    }

    public List<String> getPowers(){
        return myRepository.getPowers();
    }

    public void addSuperhero(SuperheroFormDTO form) {
        myRepository.addSuperHero(form);
    }


}
