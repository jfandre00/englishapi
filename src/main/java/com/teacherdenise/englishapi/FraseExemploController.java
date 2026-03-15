package com.teacherdenise.englishapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/frases")
public class FraseExemploController {

    @Autowired
    private FraseExemploRepository repository;

    @GetMapping
    public List<FraseExemplo> listarTodas() {
        return repository.findAll();
    }

    @PostMapping
    public FraseExemplo adicionar(@RequestBody FraseExemplo novaFrase) {
        return repository.save(novaFrase);
    }
}