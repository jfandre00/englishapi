package com.teacherdenise.englishapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conjuntos")
@CrossOrigin(origins = "http://localhost:5173")
public class ConjuntoDeCardsController {

    @Autowired
    private ConjuntoDeCardsRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PalavraRepository palavraRepository;

    @GetMapping
    public List<ConjuntoDeCards> listarPorAluno(@RequestParam Long alunoId) {
        return repository.findByAlunoId(alunoId);
    }

    @PostMapping
    public ConjuntoDeCards criar(@RequestParam Long alunoId, @RequestBody ConjuntoDeCards novoConjunto) {
        Usuario aluno = usuarioRepository.findById(alunoId).orElseThrow();
        novoConjunto.setAluno(aluno);
        return repository.save(novoConjunto);
    }

    // Interruptor de palavras do Deck
    @PostMapping("/{id}/toggle-palavra/{palavraId}")
    @Transactional
    public ResponseEntity<ConjuntoDeCards> togglePalavra(@PathVariable Long id, @PathVariable Long palavraId) {
        ConjuntoDeCards conjunto = repository.findById(id).orElseThrow();
        Palavra palavra = palavraRepository.findById(palavraId).orElseThrow();
        
        // Se já tem no deck, tira. Se não tem, bota!
        if (conjunto.getPalavras().contains(palavra)) {
            conjunto.getPalavras().remove(palavra);
        } else {
            conjunto.getPalavras().add(palavra);
        }
        return ResponseEntity.ok(repository.save(conjunto));
    }

    // Limpa as palavras de dentro do deck antes de apagar a pasta
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            ConjuntoDeCards conjunto = repository.findById(id).orElseThrow();
            conjunto.getPalavras().clear(); // Impede o banco de travar a exclusão!
            repository.delete(conjunto);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}