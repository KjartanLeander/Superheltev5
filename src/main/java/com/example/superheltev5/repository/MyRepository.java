package com.example.superheltev5.repository;

import com.example.superheltev5.DTO.*;
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
                herpowerdto = new HeroPowerDTO(heroName,realName,superpower);
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

    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url, uid, pwd)){
            String SQL = "SELECT city_name FROM city ORDER BY city_name ASC;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String power = rs.getString("city_name");
                cities.add(power);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public List<String> getPowers() {
        List<String> powers = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url, uid, pwd)) {
            String SQL = "SELECT SUPERPOWER FROM SUPERPOWER";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                String power = rs.getString("SUPERPOWER");
                powers.add(power);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return powers;
    }

    public void addSuperHero(SuperheroFormDTO form) {
        try (Connection con = DriverManager.getConnection(db_url, uid, pwd)) {
            int cityId = 0;
            int heroId = 0;
            List<Integer> powerIDs = new ArrayList<>();
            String SQL1 = "select city_id from city where name = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL1);
            pstmt.setString(1, form.getCity());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cityId = rs.getInt("city_id");
            }
            String SQL2 = "insert into superhero (hero_name, real_name, creation_year, city_id) " +
                    "values(?, ?, ?, ?);";
            pstmt = con.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS); // return autoincremented key
            pstmt.setString(1, form.getHeroName());
            pstmt.setString(2, form.getRealName());
            pstmt.setInt(3, form.getCreationYear());
            pstmt.setInt(4, cityId);
            int rows = pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                heroId = rs.getInt(1);
            }
            String SQL3 = "select power_id from superpower where name = ?;";
            pstmt = con.prepareStatement(SQL3);
            for (String power : form.getPowerList()) {
                pstmt.setString(1, power);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    powerIDs.add(rs.getInt("power_id"));
                }
            }
            String SQL4 = "insert into superhero_powers values (?,?,'high');";
            pstmt = con.prepareStatement(SQL4);

            for (int i = 0; i < powerIDs.size(); i++) {
                pstmt.setInt(1, heroId);
                pstmt.setInt(2, powerIDs.get(i));
                rows = pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    }








