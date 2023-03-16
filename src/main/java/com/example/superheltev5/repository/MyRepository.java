package com.example.superheltev5.repository;

import com.example.superheltev5.DTO.CityDTO;
import com.example.superheltev5.DTO.CountPowerDTO;
import com.example.superheltev5.DTO.HeroPowerDTO;
import com.example.superheltev5.DTO.SuperheroDTO;
import com.example.superheltev5.Entity.Superhero;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MyRepository implements Irepository {

    @Value("${spring.datasource.url}")
    private String db_url;

    @Value("${spring.datasource.username}")
    private String uid;

    @Value("${spring.datasource.password}")
    private String pwd;

    public List<SuperheroDTO> getSuperheroes() {
        List<SuperheroDTO> superheroes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url, uid, pwd)) {
            String SQL = "SELECT HERO_NAME, REAL_NAME, CREATION_YEAR, SUPERPOWER_ID, CITY_ID FROM SUPERHERO;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                String heroName = rs.getString("HERO_NAME");
                String realName = rs.getString("REAL_NAME");
                int creationYear = rs.getInt("CREATION_YEAR");
                int superpowerID = rs.getInt("SUPERPOWER_ID");
                String cityID = rs.getString("CITY_ID");

                superheroes.add(new SuperheroDTO( heroName, realName, creationYear, superpowerID, cityID));
            }
            return superheroes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HeroPowerDTO heroPower(String name) {  //localhost:8080/superhelte/batman
        HeroPowerDTO herpowerdto = null;
        try (Connection con = DriverManager.getConnection(db_url, uid, pwd)) {
            String SQL = "SELECT s.HERO_NAME,s.REAL_NAME,sp.SUPERPOWER FROM SUPERHERO s JOIN SUPERPOWER sp ON s.SUPERHERO_ID = sp.SUPERPOWER_ID  WHERE HERO_NAME = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String heroName = rs.getString("HERO_NAME");
                String realName = rs.getString("REAL_NAME");
                String superpower = rs.getString("SUPERPOWER");
                herpowerdto = new HeroPowerDTO(heroName, realName, superpower);
            }
            return herpowerdto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public CityDTO heroCity(String name){
        CityDTO cityDTO = null;
        try (Connection con = DriverManager.getConnection(db_url, uid, pwd)) {
            String SQL = "SELECT CITY_NAME,HERO_NAME FROM SUPERHERO JOIN CITY ON SUPERHERO.CITY_ID = CITY.CITY_ID WHERE CITY_NAME = ? LIMIT 0, 1000;\n";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String heroName = rs.getString("HERO_NAME");
                String cityName = rs.getString("CITY_NAME");
                cityDTO = new CityDTO(cityName,heroName);
            }
            return cityDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CountPowerDTO heroPowerCount(String name){
        CountPowerDTO countPowerDTO = null;
        try (Connection con = DriverManager.getConnection(db_url, uid, pwd)) {
            String SQL = "SELECT superhero.hero_name, COUNT(superpower.superpower_id) AS superpowerCount FROM superhero JOIN superpower ON superhero.superhero_id = superpower.superpower_id WHERE superhero.hero_name = ? GROUP BY superhero.hero_name;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String heroName = rs.getString("HERO_NAME");
                int countpower = rs.getInt("superpowerCount");
                countPowerDTO = new CountPowerDTO(heroName,countpower);
            }
            return countPowerDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    }








