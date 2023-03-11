package ru.netology.orm.model;

public record PersonDTO(long id, String name, String surname, int age, String phoneNumber, String cityOfLiving) {
}
