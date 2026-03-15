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
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ConjuntoDeCardsRepository conjuntoRepository;

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

    // Deletar uma palavra (Regra de Negócio: Bloqueia se estiver em uso)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            Palavra palavra = repository.findById(id).orElseThrow();

            // 1. Verifica se algum aluno favoritou a palavra
            java.util.List<Usuario> usuarios = usuarioRepository.findAll();
            for (Usuario u : usuarios) {
                if (u.getFavoritos().contains(palavra)) {
                    return ResponseEntity.status(409)
                            .body("Bloqueado: Esta palavra está nos favoritos de um ou mais alunos.");
                }
            }

            // 2. Verifica se a palavra está em algum deck
            java.util.List<ConjuntoDeCards> conjuntos = conjuntoRepository.findAll();
            for (ConjuntoDeCards c : conjuntos) {
                if (c.getPalavras().contains(palavra)) {
                    return org.springframework.http.ResponseEntity.status(409)
                            .body("Bloqueado: Esta palavra está sendo usada em um Deck de estudos.");
                }
            }

            // Se passou ilesa pelas verificações acima, ninguém está usando. Pode apagar!
            repository.delete(palavra);
            return org.springframework.http.ResponseEntity.ok().build();
        }
        return org.springframework.http.ResponseEntity.notFound().build();
    }

    @PostMapping("/{palavraId}/favoritar")
    public ResponseEntity<String> favoritar(@PathVariable Long palavraId, @RequestParam Long alunoId) {
        palavraService.favoritarPalavra(alunoId, palavraId);
        return ResponseEntity.status(201).body("Palavra favoritada com sucesso!");
    }
}