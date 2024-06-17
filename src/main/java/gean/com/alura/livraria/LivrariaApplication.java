package gean.com.alura.livraria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import gean.com.alura.livraria.principal.Principal;
import gean.com.alura.livraria.repository.LivroRepository;

@SpringBootApplication
public class LivrariaApplication implements CommandLineRunner {

	@Autowired
	private LivroRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(LivrariaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		principal.exibeMenu();
	}

}
