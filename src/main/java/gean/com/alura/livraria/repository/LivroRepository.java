package gean.com.alura.livraria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gean.com.alura.livraria.model.Autor;
import gean.com.alura.livraria.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, String> {
  Optional<Livro> findByTituloContainingIgnoreCase(String livro);

  @Query("SELECT a FROM Livro l JOIN l.autor a WHERE a.nome = :nome")
  Autor buscarAutoresPorNome(String nome);

  @Query("SELECT a FROM Livro l JOIN l.autor a")
  List<Autor> listarAutores();

  @Query("SELECT a FROM Livro l JOIN l.autor a WHERE :ano BETWEEN a.anoNascimento AND a.anoFalecimento")
  List<Autor> listarAutoresVivosPorAno(Integer ano);

  List<Livro> findByIdiomaContainingIgnoreCase(String idioma);
}
