package com.teacherdenise.englishapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DadosIniciaisSeeder implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PalavraRepository palavraRepository;
    @Autowired
    private FraseExemploRepository fraseRepository;
    @Autowired
    private ConjuntoDeCardsRepository conjuntoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (palavraRepository.count() == 0) {
            System.out.println("Banco de dados vazio. Iniciando a população de dados...");

            Usuario teacher = new Usuario();
            teacher.setNome("Teacher Denise");
            teacher.setEmail("denise@teacher.com");
            teacher.setSenha("senha123");
            teacher.setTipoPerfil("ADMIN");

            Usuario aluno = new Usuario();
            aluno.setNome("André");
            aluno.setEmail("andre@email.com");
            aluno.setSenha("123");
            aluno.setTipoPerfil("ALUNO_VIP");
            
            Usuario alunoComum = new Usuario();
            alunoComum.setNome("Bruno");
            alunoComum.setEmail("bruno@email.com");
            alunoComum.setSenha("123");
            alunoComum.setTipoPerfil("ALUNO_COMUM");

            usuarioRepository.saveAll(Arrays.asList(teacher, aluno, alunoComum));

            Palavra p1 = new Palavra();
            p1.setTermo("Journey");
            p1.setTraducao("Jornada / Viagem");
            p1.setNivelMaestria("NOVA");
            p1.setContadorAcertos(0);

            Palavra p2 = new Palavra();
            p2.setTermo("Achievement");
            p2.setTraducao("Conquista");
            p2.setNivelMaestria("NOVA");
            p2.setContadorAcertos(0);

            Palavra p3 = new Palavra();
            p3.setTermo("Schedule");
            p3.setTraducao("Cronograma / Horário");
            p3.setNivelMaestria("NOVA");
            p3.setContadorAcertos(0);

            palavraRepository.saveAll(Arrays.asList(p1, p2, p3));

            FraseExemplo f1 = new FraseExemplo();
            f1.setTextoIngles("Life is a journey, not a destination.");
            f1.setTextoPortugues("A vida é uma jornada, não um destino.");
            f1.setPalavra(p1);

            FraseExemplo f2 = new FraseExemplo();
            f2.setTextoIngles("Finishing the marathon was a huge achievement.");
            f2.setTextoPortugues("Terminar a maratona foi uma conquista enorme.");
            f2.setPalavra(p2);

            FraseExemplo f3 = new FraseExemplo();
            f3.setTextoIngles("I need to check my schedule for tomorrow.");
            f3.setTextoPortugues("Eu preciso checar meu cronograma para amanhã.");
            f3.setPalavra(p3);

            fraseRepository.saveAll(Arrays.asList(f1, f2, f3));

            ConjuntoDeCards conjunto = new ConjuntoDeCards();
            conjunto.setTitulo("Vocabulário Essencial");
            conjunto.setDescricao("Palavras-chave para conversação diária");
            conjunto.setPalavras(Arrays.asList(p1, p2, p3));
            
            conjuntoRepository.save(conjunto);

            System.out.println("Dados iniciais populados com sucesso!");
        } else {
            System.out.println("O banco já tem dados. O Seeder não precisou rodar.");
        }
    }
}