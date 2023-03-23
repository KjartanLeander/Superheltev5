package com.example.superheltev5.controller;

import com.example.superheltev5.DTO.*;
import com.example.superheltev5.repository.MyRepository;
import com.example.superheltev5.service.MyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path="/add")
    public String addSuperhero (Model model) {
        SuperheroFormDTO superheroFormDTO = new SuperheroFormDTO();
        model.addAttribute("CreateSuperhero", superheroFormDTO);
        model.addAttribute("city", myService.getCities());
        model.addAttribute("power", myService.heroPowerDTO("name"));
        return "add";
    }

    @PostMapping(path = "/add")
    public String addHero(@ModelAttribute("superhero") SuperheroFormDTO superheroFormDTO){
        myService.addSuperhero(superheroFormDTO);
        System.out.println(superheroFormDTO.toString());
        return "redirect:/kea/superhero";
    }


}



