package com.teacherdenise.englishapi;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.List;

@Entity
public class ConjuntoDeCards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    
    // Relacionamento TP3: Vários cards pertencem a 1 Aluno
    @jakarta.persistence.ManyToOne
    @jakarta.persistence.JoinColumn(name = "aluno_id")
    private Usuario aluno;

    // Relação Muitos para Muitos com Palavras
    @ManyToMany
    @JoinTable(
        name = "conjunto_palavra", // tabela auxiliar que o Spring vai criar
        joinColumns = @JoinColumn(name = "conjunto_id"),
        inverseJoinColumns = @JoinColumn(name = "palavra_id")
    )
    private List<Palavra> palavras;

    public ConjuntoDeCards() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public List<Palavra> getPalavras() { return palavras; }
    public void setPalavras(List<Palavra> palavras) { this.palavras = palavras; }

    public Usuario getAluno() { return aluno; }
    public void setAluno(Usuario aluno) { this.aluno = aluno; }


}