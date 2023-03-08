package ru.netology.orm.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import ru.netology.orm.model.Person;

@org.springframework.stereotype.Repository
public class Repository {

    @PersistenceContext
    public EntityManager entityManager;

    Person person = new Person();

    @Transactional
    public void run(Person person) {
        entityManager.persist(person);
    }

    @Transactional
    public String getPersonByCity(String city) {
        Query query = entityManager.createQuery("select a from Person a where a.cityOfLiving = :city");
        query.setParameter("city", city);
        return query.getSingleResult().toString();
    }
}
