package com.teacherdenise.englishapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PalavraRepository extends JpaRepository<Palavra, Long> {
	
	// Busca ignorando maiúsculas e minúsculas (UC06)
    java.util.List<Palavra> findByTermoContainingIgnoreCaseOrTraducaoContainingIgnoreCase(String termo, String traducao);
}