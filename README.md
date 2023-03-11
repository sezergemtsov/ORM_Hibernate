## Hibernate with JPA repository

Приложение по умолчанию запускается на порту 8081.

В приложении реализованы методы Get для всего списка: "/persons/all" и для поиска имени по названию города:
"/persons/by-city". Пример запроса: "http://localhost:8081/persons/by-city?city=Moscow"

Также реализован метод post который принимает объект для сохранения и метод delete который удалит из выдачи обьект по имени.

В модели реализован DTO и обьекты в базе имеют больше информаии чем в выдаче.
Запросы на удаление производятся через @Query а поиск через стандартные методы JPA repository.

Создание и заполнение таблиц настроено через миграции Liquibase.

Изначально приложение настроено для работы с PostgreSQL