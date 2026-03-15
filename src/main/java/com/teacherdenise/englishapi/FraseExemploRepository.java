package com.teacherdenise.englishapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraseExemploRepository extends JpaRepository<FraseExemplo, Long> {
}