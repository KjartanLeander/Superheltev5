package com.example.superheltev5.controller;

import com.example.superheltev5.DTO.CityDTO;
import com.example.superheltev5.DTO.CountPowerDTO;
import com.example.superheltev5.DTO.HeroPowerDTO;
import com.example.superheltev5.DTO.SuperheroDTO;
import com.example.superheltev5.Entity.Superhero;
import com.example.superheltev5.service.MyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
@RequestMapping(path = "kea")
public class SuperHeroController {

    private MyService myService;

    public SuperHeroController(MyService myService) {
        this.myService = myService;
    }

    /*@GetMapping(path = "/superheroes")
    public ResponseEntity<List<Superhero>> getSuperheroes() {
        List<Superhero> superheroesList = myService.getSuperheroes();
        return new ResponseEntity<>(superheroesList, HttpStatus.OK);
    }*/
    @GetMapping(path = "/superheroes/superpower/{name}")
    public String heroPowerByName(Model model, @PathVariable String name){
        HeroPowerDTO heroPowerByName = myService.heroPowerDTO(name);
        model.addAttribute("heroPowerByName",heroPowerByName);
        return "Powers";
    }
    @GetMapping(path = "/city/{name}")
    public ResponseEntity<CityDTO> cityByHeroName(@PathVariable String name){
        CityDTO cityDTO = myService.cityDTO(name);
        return new ResponseEntity<>(cityDTO,HttpStatus.OK);
    }
    @GetMapping(path = "/superpower/count/{name}")
    public ResponseEntity<CountPowerDTO> powerCountByName(@PathVariable("name") String name) {
        CountPowerDTO countPowerDTO = myService.countPowerDTO(name);
        return new ResponseEntity<>(countPowerDTO, HttpStatus.OK);
    }
    @GetMapping(path = "/superheroes")
    public String getAllSuperheroes(Model model) {
        List<SuperheroDTO> superheroesList = myService.getSuperheroes();
        model.addAttribute("Heroes",superheroesList);
        return "index";
    }
}



