package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KoalaKontroller {
    public Map<Integer, Koala> koalas;
    @PostConstruct
    public void init() {
        koalas = new HashMap<>();

    }

    @GetMapping("/koalas")
    public List<Koala> findAll(){
        return koalas.values().stream().toList();
    }

    @GetMapping("/koalas/{id}")
    public Koala findOne(@PathVariable int id){
        if (id<= 0 || !koalas.containsKey(id)) {
            throw new ZooException("Eksik veya geçersiz Koala verisi gönderildi.", HttpStatus.BAD_REQUEST);
        }
        return koalas.get(id);
    }

    @PostMapping("/koalas")
    public Koala create(@RequestBody Koala koala){

        if (koala == null ||
                koala.getId() == 0 ||
                koala.getName() == null || koala.getName().isBlank() ||

                koala.getWeight() <= 0 ||
                koala.getGender() == null) {

            throw new ZooException("Eksik veya geçersiz kanguru verisi gönderildi.", HttpStatus.BAD_REQUEST);
        }
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/koalas/{id}")
    public Koala update(@PathVariable int id,@RequestBody Koala koala){
        koalas.put(id,koala);
        return  koalas.get(id);
    }

    @DeleteMapping("/koalas/{id}")
    public Koala delete(@PathVariable int id){
        if(id<=0){
            throw  new IllegalArgumentException("Geçersiz Argüman");
        }
        return koalas.remove(id);
    }
}
