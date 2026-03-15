package com.teacherdenise.englishapi;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConjuntoDeCardsRepository extends JpaRepository<ConjuntoDeCards, Long> {
    // Busca automática do Spring: Traz apenas os decks do aluno logado
    List<ConjuntoDeCards> findByAlunoId(Long alunoId);
}