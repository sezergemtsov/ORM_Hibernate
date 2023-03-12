package ru.netology.orm.service;

import jakarta.transaction.Transactional;
import ru.netology.orm.exceptions.NotFoundExceptions;
import ru.netology.orm.model.Person;
import ru.netology.orm.model.PersonDTO;
import ru.netology.orm.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@org.springframework.stereotype.Service
public class Service {

    Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public String getPersonByCity(String city) {
        Optional<Person> person = repository.findByCityOfLivingContaining(city);
        AtomicReference<String> respond = new AtomicReference<>();
        person.ifPresentOrElse(v -> {
            if (!v.isRemoved()) {
                respond.set(v.getName());
            } else {
                respond.set("Персона была удалена");
            }
        }, () -> respond.set("Персона не найдена"));
        return respond.get();
    }

    public List<PersonDTO> getAll() {
        List<Person> personList = repository.findAll();
        List<PersonDTO> respondPersons = new ArrayList<>();
        personList.forEach(x -> {
            if (!x.isRemoved()) {
                respondPersons.add(convertToUserFormat(x));
            }
        });
        return respondPersons;
    }

    public String delete(String name) {
        Optional<Person> person = repository.findByName(name);
        var respond = new AtomicReference<>("Персона удалена");
        person.ifPresentOrElse(v -> {
            if (!v.isRemoved()) {
                repository.markForRemoving(v.getId());
            } else {
                respond.set("Персона уже удалена из базы");
            }
        }, () -> respond.set("Персона не найдена"));
        return respond.get();
    }

    public String post(PersonDTO person) {
        Optional<Person> op = findMatch(person);
        AtomicReference<String> respond = new AtomicReference<>("Персона " + person.name() + " успешно добавлена");
        op.ifPresentOrElse(v -> {
            if (!v.isRemoved()) {
                respond.set("Персона " + person.name() + " уже существует");
            } else {
                repository.unmarkForRemoving(v.getId());
            }
        }, () -> repository.save(convertToDataBaseFormat(person)));
        return respond.get();
    }

    public List<PersonDTO> findByAgeLess(Integer age) {
        List<Person> personsLessThen = repository.findByAgeLessThanOrderByAge(age);
        List<PersonDTO> sendingList = new ArrayList<>();
        personsLessThen.forEach(x -> {
            if (!x.isRemoved()) {
                sendingList.add(convertToUserFormat(x));
            }
        });
        return sendingList;
    }

    public PersonDTO findByNameAndSurname(String name, String surname) {
        Optional<Person> searchedPerson = repository.findByNameAndSurname(name, surname);
        final PersonDTO[] respond = new PersonDTO[1];
        searchedPerson.ifPresent(p -> {
            if (!p.isRemoved()) {
                respond[0] = convertToUserFormat(p);
            }
        });
        if (respond[0] != null) {
            return respond[0];
        } else {
            throw new NotFoundExceptions("Персона не найдена");
        }
    }

    private Person convertToDataBaseFormat(PersonDTO person) {
        return new Person(person.id(),
                person.name(), person.surname(),
                person.age(), person.phoneNumber(),
                person.cityOfLiving(),
                false, false);
    }

    private PersonDTO convertToUserFormat(Person person) {
        return new PersonDTO(person.getId(),
                person.getName(), person.getSurname(),
                person.getAge(), person.getPhoneNumber(),
                person.getCityOfLiving());
    }

    @Transactional
    private Optional<Person> findMatch(PersonDTO person) {
        return repository.findByNameAndSurnameAndAge(person.name().toLowerCase(), person.surname().toLowerCase(), person.age());
    }
}
