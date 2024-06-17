package gean.com.alura.livraria.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Column(unique = true)
  private String titulo;
  private String idioma;
  private Integer numeroDownloads;

  @ManyToOne(cascade = CascadeType.ALL)
  private Autor autor;

  public Livro() {
  }

  public Livro(DadosLivro dados) {
    this.titulo = dados.titulo();
    this.idioma = dados.idiomas().get(0);
    this.numeroDownloads = dados.numeroDownloads();

    Autor autorLivro = new Autor(dados.autores().get(0));
    this.autor = autorLivro;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getIdioma() {
    return idioma;
  }

  public void setIdioma(String idioma) {
    this.idioma = idioma;
  }

  public Integer getNumeroDownloads() {
    return numeroDownloads;
  }

  public void setNumeroDownloads(Integer numeroDownloads) {
    this.numeroDownloads = numeroDownloads;
  }

  public Autor getAutor() {
    return autor;
  }

  public void setAutor(Autor autor) {
    this.autor = autor;
  }

  @Override
  public String toString() {
    return Color.YELLOW + "\nTítulo: " + Color.RESET + titulo +
        Color.YELLOW + "\nAutor: " + Color.RESET + autor.getNome() +
        Color.YELLOW + "\nIdioma: " + Color.RESET + idioma +
        Color.YELLOW + "\nNúmero de downloads: " + Color.RESET + numeroDownloads;
  }
}
