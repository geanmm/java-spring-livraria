package gean.com.alura.livraria.service;

public interface IConverteDados {
  <T> T obterDados(String json, Class<T> classe);
}
