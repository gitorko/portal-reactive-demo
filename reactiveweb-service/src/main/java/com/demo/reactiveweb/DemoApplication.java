package com.demo.reactiveweb;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.http.MediaType;
// import org.springframework.security.config.web.server.ServerHttpSecurity;
// import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
// import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class DemoApplication {

  // @Bean
  // ReactiveUserDetailsService authentication(){
  // return new MapReactiveUserDetailsService(
  // User.withDefaultPasswordEncoder().username("admin").password("password").roles("Customer").build()
  // );
  // }

  // @Bean
  // SecurityWebFilterChain config(ServerHttpSecurity security){
  // return security.csrf().disable()
  // .httpBasic()
  // .and()
  // .authorizeExchange()
  // .pathMatchers("api/customers")
  // .authenticated()
  // .anyExchange()
  // .permitAll()
  // .and()
  // .build();

  // }

  @Bean
  public RouterFunction<ServerResponse> indexRouter(@Value("classpath:/static/index.html") final Resource indexHtml) {
    return route(GET("/"), request -> ok().contentType(MediaType.TEXT_HTML).syncBody(indexHtml));
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
class CustomerRestController {
  private final CustomerRepository repo;

  @GetMapping("/customers")
  public Flux<Customer> getCustomers() {
    log.info("get customers request!");
    return repo.findAll();
  }

  @PostMapping("/customers")
  public void save(@RequestBody Customer customer) {
    log.info("save request: {}", customer);
    repo.save(customer).subscribe();
  }

  @GetMapping("/hello")
  public Mono<String> hello() {
    log.info("hello world request!");
    return Mono.just("Hello World!");
  }
}

@Component
@AllArgsConstructor
@Slf4j
class Initializer implements ApplicationRunner {

  private final CustomerRepository repo;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("Initializing repo!");
    Flux<String> names = Flux.just("arjun","vijay","shantanu").delayElements(Duration.ofSeconds(2));
    Flux<String> colors = Flux.just("red","blue","green").delayElements(Duration.ofSeconds(3));
    Flux<Customer> customers = Flux.zip(names,colors).map(tupple -> {
      return new Customer(null,tupple.getT1(),new Date(),tupple.getT2());
    });

    repo.deleteAll().thenMany(customers.flatMap(repo::save).thenMany(repo.findAll())).subscribe(System.out::println);
    log.info("Main thread done!");
  }
}

interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
}


@Data
@Document
@AllArgsConstructor()
@NoArgsConstructor
class Customer {
  @Id
  private String id;
  private String name;
  private Date creation;
  private String color;
}
