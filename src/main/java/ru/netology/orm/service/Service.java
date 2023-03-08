package ru.netology.orm.service;

import ru.netology.orm.model.Person;
import ru.netology.orm.repository.Repository;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public void get() {
        List<Person> persons = List.of(
                Person.builder()
                        .name("Ivan")
                        .surname("Petrov")
                        .age(31)
                        .phoneNumber("9 801")
                        .cityOfLiving("Moscow")
                        .build(),
                Person.builder()
                        .name("Oleg")
                        .surname("Ivanov")
                        .age(36)
                        .phoneNumber("9 801")
                        .cityOfLiving("Samara")
                        .build(),
                Person.builder()
                        .name("Petr")
                        .surname("Romanov")
                        .age(27)
                        .phoneNumber("9 801")
                        .cityOfLiving("Volgograd")
                        .build()
        );

        persons.forEach(x -> repository.run(x));

    }

    public String getPersonByCity(String city) {
        return repository.getPersonByCity(city);
    }
}
