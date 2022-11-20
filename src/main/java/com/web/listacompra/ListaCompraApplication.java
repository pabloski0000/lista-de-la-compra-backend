package com.web.listacompra;


import com.web.listacompra.domain.userDomain.User;
import com.web.listacompra.domain.userDomain.UserRepository;
import com.web.listacompra.infrastracture.userRepository.UserConnectionMongoDBRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ListaCompraApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ListaCompraApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println("EMPIEZA!!!!!!!!!");
		/*User user = new User();
		user.setId("78092");
		user.setNickName("Pablo2");
		user.setPassword("123456789");
		user.setRole(1);
		user.setToken("token");
		Mono<User> storedUser = userConnectionMongoDBRepositoryRepository.save(user);
		storedUser.then().doOnNext(it -> System.out.println("HE THEN!!!!"));
		storedUser.doFinally(it -> System.out.println("HE TERMINADO!!!!"));
		storedUser.doOnError(it -> System.out.println("HE FALLADO!!!!"));
		storedUser.map(it ->  it.getNickName()).doOnNext(it -> System.out.println("Esto: " + it));
		Flux<User> users = userConnectionMongoDBRepositoryRepository.findAll();
		users.then().doOnNext(it -> System.out.println("Esto otra vez: " + it));*/
		//User storedUser = userConnectionMongoDBRepositoryRepository.findUserByNickName("Pablo");
		/*System.out.println(storedUser);
		System.out.println(storedUser.getNickName());
		System.out.println(storedUser.getPassword());*/

		System.out.println("ACABA!!!!!!!!!!");
	}

}
