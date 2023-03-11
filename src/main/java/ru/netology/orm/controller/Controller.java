package ru.netology.orm.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.orm.model.PersonDTO;
import ru.netology.orm.service.Service;

import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {

    Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/persons/by-city")
    public String get(@RequestParam("city") final String city) {
        return service.getPersonByCity(city);
    }

    @GetMapping("/persons/all")
    public List<PersonDTO> getAll() {
        return service.getAll();
    }

    @PostMapping("/persons/add")
    public String post(final PersonDTO person) {
        return service.post(person);
    }

    @DeleteMapping("/persons")
    public String delete(@RequestParam("name") final String name) {
        return service.delete(name);
    }

}
