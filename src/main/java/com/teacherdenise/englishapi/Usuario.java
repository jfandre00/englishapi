package com.teacherdenise.englishapi;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;
    
    // define se é ADMIN, ALUNO_VIP ou ALUNO_COMUM
    private String tipoPerfil; 
    
    // --- Atributos de Gamificação (TP4) ---
    private Integer streakDias = 0; // Começa com 0 
    private Integer xpTotal = 0;    // Começa com 0 

    // --- Relacionamento: Favoritos do Aluno (TP4) ---
    @jakarta.persistence.ManyToMany
    @jakarta.persistence.JoinTable(
        name = "usuario_favoritos",
        joinColumns = @jakarta.persistence.JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @jakarta.persistence.JoinColumn(name = "palavra_id")
    )
    private java.util.List<Palavra> favoritos = new java.util.ArrayList<>();

    // Construtor vazio -> exigido pelo JPA
    public Usuario() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTipoPerfil() { return tipoPerfil; }
    public void setTipoPerfil(String tipoPerfil) { this.tipoPerfil = tipoPerfil; }

    // CORREÇÃO: Getters blindados contra valores nulos (null) do banco antigo!
    public Integer getStreakDias() {
        return streakDias == null ? 0 : streakDias;
    }

    public void setStreakDias(Integer streakDias) {
        this.streakDias = streakDias;
    }

    public Integer getXpTotal() {
        return xpTotal == null ? 0 : xpTotal;
    }

    public void setXpTotal(Integer xpTotal) {
        this.xpTotal = xpTotal;
    }

    public java.util.List<Palavra> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(java.util.List<Palavra> favoritos) {
        this.favoritos = favoritos;
    }
    
    // --- Métodos de Gamificação - TP4 ---
    // Usamos os Getters aqui para garantir que ele some 0 + 1 e não "null" + 1
    public void incrementarStreak() {
        this.streakDias = getStreakDias() + 1;
    }

    public void adicionarXP(int pontos) {
        this.xpTotal = getXpTotal() + pontos;
    }
}