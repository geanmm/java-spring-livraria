package gean.com.alura.livraria.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "autores")
public class Autor {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Column(unique = true)
  private String nome;
  private Integer anoNascimento;
  private Integer anoFalecimento;

  @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Livro> livros = new ArrayList<>();

  public Autor() {
  }

  public Autor(DadosAutor dados) {
    this.nome = dados.nome();
    this.anoNascimento = dados.anoNascimento();
    this.anoFalecimento = dados.anoFalecimento();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Integer getAnoNascimento() {
    return anoNascimento;
  }

  public void setAnoNascimento(Integer anoNascimento) {
    this.anoNascimento = anoNascimento;
  }

  public Integer getAnoFalecimento() {
    return anoFalecimento;
  }

  public void setAnoFalecimento(Integer anoFalecimento) {
    this.anoFalecimento = anoFalecimento;
  }

  public List<Livro> getLivros() {
    return livros;
  }

  public void setLivros(List<Livro> livros) {
    this.livros = livros;
  }

  @Override
  public String toString() {
    return Color.YELLOW + "\nAutor: " + Color.RESET + nome +
        Color.YELLOW + "\nAno de nascimento: " + Color.RESET + anoNascimento +
        Color.YELLOW + "\nAno de falecimento: " + Color.RESET + anoFalecimento +
        Color.YELLOW + "\nLivros: " + Color.RESET
        + livros.stream().map(l -> l.getTitulo()).collect(Collectors.toList());
  }
}
