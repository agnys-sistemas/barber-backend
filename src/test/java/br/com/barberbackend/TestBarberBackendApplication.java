package br.com.barberbackend;

import org.springframework.boot.SpringApplication;

public class TestBarberBackendApplication {

    public static void main(String[] args) {
        SpringApplication.from(BarberBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
