package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KangarooController {
    public Map<Integer, Kangaroo> kangaroos;
    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();

    }

    @GetMapping("/kangaroos")
    public List<Kangaroo> findAll(){
        return kangaroos.values().stream().toList();
    }

    @GetMapping("/kangaroos/{id}")
    public Kangaroo findOne(@PathVariable int id){
        if (id<= 0 || !kangaroos.containsKey(id)) {
            throw new ZooException("Eksik veya geçersiz Koala verisi gönderildi.", HttpStatus.NOT_FOUND);
        }
        return kangaroos.get(id);
    }

    @PostMapping("/kangaroos")
    public Kangaroo create(@RequestBody Kangaroo kangaroo){

        if (kangaroo == null ||
                kangaroo.getId() == 0 ||
                kangaroo.getName() == null || kangaroo.getName().isBlank() ||
                kangaroo.getHeight() <= 0 ||
                kangaroo.getWeight() <= 0 ||
                kangaroo.getGender() == null) {

            throw new ZooException("Eksik veya geçersiz kanguru verisi gönderildi.", HttpStatus.BAD_REQUEST);
        }
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }

    @PutMapping("/kangaroos/{id}")
    public Kangaroo update(@PathVariable int id,@RequestBody Kangaroo kangaroo){
        kangaroos.put(id,kangaroo);
        return  kangaroos.get(id);
    }

    @DeleteMapping("/kangaroos/{id}")
    public Kangaroo delete(@PathVariable int id){
        return kangaroos.remove(id);
    }
}
