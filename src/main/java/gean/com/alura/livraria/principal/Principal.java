package gean.com.alura.livraria.principal;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import gean.com.alura.livraria.model.Autor;
import gean.com.alura.livraria.model.Color;
import gean.com.alura.livraria.model.DadosLivro;
import gean.com.alura.livraria.model.Livro;
import gean.com.alura.livraria.repository.LivroRepository;
import gean.com.alura.livraria.service.ConsumoAPI;
import gean.com.alura.livraria.service.ConverteDados;

public class Principal {
  private LivroRepository repository;
  private Scanner leitura = new Scanner(System.in);
  private ConsumoAPI api = new ConsumoAPI();
  private ConverteDados conversor = new ConverteDados();
  private final String URL = "https://gutendex.com/books/?search=";

  public Principal(LivroRepository repository) {
    this.repository = repository;
  }

  public void exibeMenu() {
    var option = -1;
    while (option != 0) {
      var menu = MessageFormat.format("""
          \nEscolha o número de sua opção:
          {1}1 -{0} Buscar livro pelo título
          {1}2 -{0} Listar livros registrados
          {1}3 -{0} Listar autores registrados
          {1}4 -{0} Listar autores vivos em um determinado ano
          {1}5 -{0} Listar livros em um determinado idioma

          {2}0 -{0} Sair
          """, Color.RESET, Color.CYAN, Color.RED);

      System.out.println(menu);
      try {
        option = leitura.nextInt();
      } catch (InputMismatchException e) {
        System.out.println(Color.RED + "Erro de input: " + Color.RESET + e.getMessage());
      }
      leitura.nextLine();

      switch (option) {
        case 1:
          buscarLivroPorTitulo();
          break;
        case 2:
          listarLivros();
          break;
        case 3:
          listarAutores();
          break;
        case 4:
          listarAutoresPorAno();
          break;
        case 5:
          listarLivrosPorIdioma();
          break;
        case 0:
          System.out.println("Saindo...");
          break;
        default:
          System.out.println(Color.RED + "Opção inválida" + Color.RESET);
          break;
      }
    }
  }

  private void buscarLivroPorTitulo() {
    System.out.println(Color.YELLOW + "\nInsira do título do livro que deseja buscar:" + Color.RESET);
    var titulo = leitura.nextLine();

    Optional<Livro> livroExiste = repository.findByTituloContainingIgnoreCase(titulo);

    if (livroExiste.isPresent()) {
      System.out.println(livroExiste.get());
    } else {
      String encodedQuery = encodeQuery(titulo);
      System.out.println(URL + encodedQuery);
      var json = api.obterDados(URL + encodedQuery);

      DadosLivro data = conversor.obterDados(json, DadosLivro.class);
      if (data == null) {
        System.out.println(Color.RED + "Livro não encontrado" + Color.RESET);
      } else {
        Livro livro = new Livro(data);
        Autor autor = repository.buscarAutoresPorNome(livro.getAutor().getNome());

        if (autor != null) {
          livro.setAutor(null);
          repository.save(livro);
          livro.setAutor(autor);
        }
        System.out.println(livro);
        repository.save(livro);
      }
    }

  }

  private void listarLivros() {
    List<Livro> livros = repository.findAll();
    livros.forEach(System.out::println);
  }

  private void listarAutores() {
    List<Autor> autores = repository.listarAutores();
    autores.forEach(System.out::println);
  }

  private void listarAutoresPorAno() {
    try {
      System.out.println("\nQual o ano que deseja buscar?");
      var ano = leitura.nextInt();
      leitura.nextLine();
      List<Autor> autores = repository.listarAutoresVivosPorAno(ano);
      System.out.println(Color.CYAN + "Autores vivos em " + ano + Color.RESET);
      autores.forEach(System.out::println);
    } catch (InputMismatchException e) {
      System.out.println(Color.RED + "Digite um ano válido. Erro: " + Color.RESET + e.getMessage());
      leitura.nextLine();
    }
  }

  private void listarLivrosPorIdioma() {
    var menuIdioma = MessageFormat.format("""
        \nEscolha o número de sua opção de idioma:
        {1}es -{0} Espanhol
        {1}en -{0} Inglês
        {1}fr -{0} Francês
        {1}pt -{0} Português
        """, Color.RESET, Color.CYAN, Color.RED);
    System.out.println(menuIdioma);
    var idioma = leitura.nextLine();

    List<Livro> livros = repository.findByIdiomaContainingIgnoreCase(idioma);

    if (livros.isEmpty()) {
      System.out.println(Color.RED + "Nenhum livro encontrado para essa opção de idioma" + Color.RESET);
    } else {
      livros.forEach(System.out::println);
    }

  }

  public String encodeQuery(String busca) {
    String query = "";
    try {
      query = URLEncoder.encode(busca, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      System.out.println("Parâmetro de busca inválido. Erro: " + e.getMessage());
    }
    return query;
  }
}
