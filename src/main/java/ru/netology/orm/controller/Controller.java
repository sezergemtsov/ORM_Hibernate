package ru.netology.orm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.orm.service.Service;

@RestController
@RequestMapping("/")
public class Controller {

    Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/persons/by-city")
    public String get(@RequestParam("city") String city) {
        return service.getPersonByCity(city);
    }

    @GetMapping()
    public String get() {
        service.get();
        return "Table was filled with 3 persons, you can find person by next cities: Moscow, Samara, Volgograd";
    }
}
