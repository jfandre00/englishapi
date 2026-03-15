package com.teacherdenise.englishapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

@Service
public class QuizService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PalavraRepository palavraRepository;

    @Autowired
    private PalavraService palavraService; // Usa o outro serviço para atualizar a maestria

    // Assinatura do TP4 adaptada: Retorna uma lista de palavras para o Quiz
    public List<Palavra> gerarSessaoEstudo(Long alunoId) {
        Usuario aluno = usuarioRepository.findById(alunoId).orElseThrow();
        
        // Prioriza as palavras favoritadas pelo aluno para o Quiz
        List<Palavra> palavrasParaQuiz = new ArrayList<>(aluno.getFavoritos());
        
        // Se ele não favoritou nada ainda, pega do banco geral
        if (palavrasParaQuiz.isEmpty()) {
            palavrasParaQuiz = palavraRepository.findAll();
        }
        
        Collections.shuffle(palavrasParaQuiz);
        return palavrasParaQuiz.size() > 5 ? palavrasParaQuiz.subList(0, 5) : palavrasParaQuiz;
    }

    // Assinatura do TP4: public boolean validarResposta(int palavraId, String resposta)
    public boolean validarResposta(Long alunoId, Long palavraId, String resposta) {
        Palavra palavra = palavraRepository.findById(palavraId).orElseThrow();
        
        // Ignora maiúsculas/minúsculas e espaços extras para facilitar a vida do aluno
        boolean acertou = palavra.getTermo().trim().equalsIgnoreCase(resposta.trim());
        
        // Chama a regra de negócio do outro serviço para atualizar o nível da palavra
        palavraService.atualizarMaestria(palavraId, acertou);
        
        if (acertou) {
            Usuario aluno = usuarioRepository.findById(alunoId).orElseThrow();
            aluno.adicionarXP(10); // Dá 10 pontos de experiência por acerto
            usuarioRepository.save(aluno);
        }
        
        return acertou;
    }
}