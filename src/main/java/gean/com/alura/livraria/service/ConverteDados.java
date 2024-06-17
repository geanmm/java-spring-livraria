package gean.com.alura.livraria.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados {
  private ObjectMapper mapper = new ObjectMapper();

  @Override
  public <T> T obterDados(String json, Class<T> classe) {
    try {
      var results = mapper.readTree(json).path("results");
      if (results.isEmpty()) {
        return null;
      }
      return mapper.readValue(results.get(0).toString(), classe);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

}
