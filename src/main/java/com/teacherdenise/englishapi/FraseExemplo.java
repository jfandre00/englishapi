package com.teacherdenise.englishapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class FraseExemplo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String textoIngles;
    private String textoPortugues;

    // MUITAS frases podem pertencer a UMA palavra
    @ManyToOne
    @JoinColumn(name = "palavra_id") // Cria a coluna de chave estrangeira no banco
    @JsonIgnore // Evita um loop infinito na hora de gerar o JSON na tela
    private Palavra palavra;

    public FraseExemplo() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTextoIngles() { return textoIngles; }
    public void setTextoIngles(String textoIngles) { this.textoIngles = textoIngles; }

    public String getTextoPortugues() { return textoPortugues; }
    public void setTextoPortugues(String textoPortugues) { this.textoPortugues = textoPortugues; }

    public Palavra getPalavra() { return palavra; }
    public void setPalavra(Palavra palavra) { this.palavra = palavra; }
}