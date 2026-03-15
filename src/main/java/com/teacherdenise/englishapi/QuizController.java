package com.teacherdenise.englishapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    // Rota do Diagrama: GET /api/quiz/gerar
    @GetMapping("/gerar")
    public List<Palavra> gerarQuiz(@RequestParam Long alunoId) {
        return quizService.gerarSessaoEstudo(alunoId);
    }

    // Rota do Diagrama: POST /api/quiz/validar
    @PostMapping("/validar")
    public boolean validar(@RequestParam Long alunoId, @RequestParam Long palavraId, @RequestParam String resposta) {
        return quizService.validarResposta(alunoId, palavraId, resposta);
    }
}