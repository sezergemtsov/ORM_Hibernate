package ru.netology.orm.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.netology.orm.model.Person;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Person, Long> {

    @Transactional
    Optional<Person> findByName(String name);

    @Transactional
    Optional<Person> findByNameAndSurnameAndAge(String name, String surname, int age);

    @Transactional
    Optional<Person> findByCityOfLivingContaining(String city);

    @Transactional
    List<Person> findByAgeLessThanOrderByAge(Integer age);

    @Transactional
    Optional<Person> findByNameAndSurname(String name, String surname);


    @Transactional
    @Modifying
    @Query("update Person set isRemoved=true where id = :id")
    void markForRemoving(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update Person set isRemoved=false where id = :id")
    void unmarkForRemoving(@Param("id") Long id);

}
