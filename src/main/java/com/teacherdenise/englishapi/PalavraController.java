package com.teacherdenise.englishapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/palavras")
@CrossOrigin(origins = "http://localhost:5173")
public class PalavraController {

    @Autowired
    private PalavraRepository repository;
    
    @Autowired
    private PalavraService palavraService; 

    @GetMapping
    public List<Palavra> listarTodas() {
        return repository.findAll();
    }
    
    @GetMapping("/buscar")
    public List<Palavra> buscarPalavras(@RequestParam String query) {
        return repository.findByTermoContainingIgnoreCaseOrTraducaoContainingIgnoreCase(query, query);
    }
    
    @PostMapping
    public Palavra adicionar(@RequestBody Palavra novaPalavra) {
        return repository.save(novaPalavra);
    }
    
    @PutMapping("/{id}")
    @Transactional 
    public ResponseEntity<Palavra> atualizar(@PathVariable Long id, @RequestBody Palavra palavraAtualizada) {
        return repository.findById(id)
                .map(palavra -> {
                    palavra.setTermo(palavraAtualizada.getTermo());
                    palavra.setTraducao(palavraAtualizada.getTraducao());
                    palavra.setAudioUrl(palavraAtualizada.getAudioUrl());
                    palavra.setNivelMaestria(palavraAtualizada.getNivelMaestria());
                    
                    palavra.getFrases().clear();
                    
                    if (palavraAtualizada.getFrases() != null) {
                        palavraAtualizada.getFrases().forEach(frase -> {
                            frase.setPalavra(palavra); 
                            palavra.getFrases().add(frase);
                        });
                    }
                    
                    return ResponseEntity.ok(repository.save(palavra));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{palavraId}/favoritar")
    public ResponseEntity<String> favoritar(@PathVariable Long palavraId, @RequestParam Long alunoId) {
        palavraService.favoritarPalavra(alunoId, palavraId);
        return ResponseEntity.status(201).body("Palavra favoritada com sucesso!");
    }
}