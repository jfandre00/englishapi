package com.teacherdenise.englishapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PalavraService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PalavraRepository palavraRepository;

    // Assinatura do TP4: public void favoritarPalavra(int alunoId, int palavraId)
    public void favoritarPalavra(Long alunoId, Long palavraId) {
        Usuario aluno = usuarioRepository.findById(alunoId).orElseThrow();
        Palavra palavra = palavraRepository.findById(palavraId).orElseThrow();
        
        // Se já tem, desfavorita. Se não tem, adiciona. (UC02 do TP2)
        if (aluno.getFavoritos().contains(palavra)) {
            aluno.getFavoritos().remove(palavra);
        } else {
            aluno.getFavoritos().add(palavra);
        }
        usuarioRepository.save(aluno);
    }

    // Assinatura do TP4: public void atualizarMaestria(int palavraId, boolean acertou)
    public void atualizarMaestria(Long palavraId, boolean acertou) {
        Palavra palavra = palavraRepository.findById(palavraId).orElseThrow();
        
        if (acertou) {
            palavra.setContadorAcertos(palavra.getContadorAcertos() + 1);
            // Máquina de Estado do TP4: 3 acertos = Aprendida
            if (palavra.getContadorAcertos() >= 3) {
                palavra.setNivelMaestria("APRENDIDA");
            } else {
                palavra.setNivelMaestria("EM_REVISAO");
            }
        } else {
            // Máquina de Estado do TP4: Erro no Quiz volta para Em Revisão
            palavra.setContadorAcertos(0);
            palavra.setNivelMaestria("EM_REVISAO");
        }
        
        palavraRepository.save(palavra);
    }
}