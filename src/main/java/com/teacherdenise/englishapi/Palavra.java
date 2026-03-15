package com.teacherdenise.englishapi;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // avisa ao Spring que esta classe deve virar uma tabela no banco
public class Palavra {

    @Id // que este campo é a pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco vai gerar o ID sozinho
    private Long id;

    private String termo;        // A palavra em inglês 
    private String traducao;     
    private String audioUrl;     // Link para o áudio 
    private String nivelMaestria; // Como definido no TP4 (Iniciante, Bronze, etc)
    private Integer contadorAcertos;
    
    // UMA palavra tem MUITAS frases
    // CascadeType.ALL -> se apagarmos a palavra, as frases dela somem junto
    @jakarta.persistence.OneToMany(mappedBy = "palavra", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private java.util.List<FraseExemplo> frases = new java.util.ArrayList<>();
    
    // UMA palavra pode estar em MUITOS conjuntos
    @com.fasterxml.jackson.annotation.JsonIgnore // Evita o loop infinito no JSON
    @jakarta.persistence.ManyToMany(mappedBy = "palavras")
    private java.util.List<ConjuntoDeCards> conjuntos;

    // Construtor vazio - para o JPA funcionar
    public Palavra() {
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTermo() { return termo; }
    public void setTermo(String termo) { this.termo = termo; }

    public String getTraducao() { return traducao; }
    public void setTraducao(String traducao) { this.traducao = traducao; }

    public String getAudioUrl() { return audioUrl; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }

    public String getNivelMaestria() { return nivelMaestria; }
    public void setNivelMaestria(String nivelMaestria) { this.nivelMaestria = nivelMaestria; }

    public Integer getContadorAcertos() { return contadorAcertos; }
    public void setContadorAcertos(Integer contadorAcertos) { this.contadorAcertos = contadorAcertos; }
    
    public java.util.List<FraseExemplo> getFrases() { return frases; }
    public void setFrases(java.util.List<FraseExemplo> frases) { this.frases = frases; }
    
    public java.util.List<ConjuntoDeCards> getConjuntos() { return conjuntos; }
    public void setConjuntos(java.util.List<ConjuntoDeCards> conjuntos) { this.conjuntos = conjuntos; }
}