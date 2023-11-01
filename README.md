# SimbirSoft LLC. Functional Program LICENSE Rent Service Exclusive ©️

## Перед работой
- Java 17
- Maven
- PostgreSQL (>10)

## Сборка проекта
> fast: [startup.bat](./startup.bat) **WINDOWS** or [startup.sh](./startup.sh) **LINUX**
- ```mvn clean package```
> Архив проекта после сборки будет находиться в папке ```target```

> Готовая сборка **возможно** будет в релизах репозитория

**Для запуска .jar файла (или сборки) используйте** ```java -jar <Name>.jar```
> Дополнительные аргументы при запуске архива не используются

## Документация к API
> Проект настроен и работает согласно предоставленной [документации](./Задание-на-полуфинал-по-дисциплине-Backend-разработка-WEB-API.pdf).
> Swagger документация представляет из себя совокупность enpoints, на которые маппится API из ТЗ, согласно
> [MappingController](./src/main/java/org/jeugenedev/simbir/controller/v0/MappingController.java)

> [http://localhost:8080/docs](http://localhost:8080/docs)

## Информация о ресурсах проекта
> Местоположение ресурсов проекта [здесь](./src/main/resources)

- Миграции баз данных [click](./src/main/resources/db/migration)
- Конфигурация проекта [click](./src/main/resources/application.yml)
- Конфигурация к бизнес-логике проекта [click](./src/main/resources/logic.properties)

### application.yml
- spring.datasource.url: информация для соединения с СУБД \
  prefix: jdbc:postgresql://localhost:5432/ \
  suffix: <название базы данных>, simbir по-умолчанию \
  jdbc:postgresql://localhost:5432/**simbir**
- spring.datasource.username: username для подключения к СУБД
- spring.datasource.password: password для подключения к СУБД
- **springdoc.swagger-ui.path**: путь до страницы с **swagger** документацией

### logic.properties
- auth.username.param: никнейм пользователя, использующийся в JWT
- auth.secret: секретный ключ, использующийся для подписи JWT
- jwt.tokens.expired.minutes: длительность жизни JWT в минутах
- password.secret: секретный ключ, использующийся для хеширования паролей пользователей

## Информацию о логике По-умолчанию проекта
> В проекте присутствует логика по-умолчанию, описанная [здесь](./src/main/resources/db/migration/V1__Init.sql)

### Система ролей пользователь
- Роль пользователь
- - Младшая роль
- - id: 1
- - name: ROLE_USER
- - Права доступа: USER
- Роль админ
- - Старшая роль
- - id: 2
- - name: ROLE_ADMIN
- - Права доступа: ADMIN

### Тип транспорта
- Автомобиль
- - id: 1
- - name: Car
- Велосипед
- - id: 2
- - name: Bike
- Скутер
- - id: 3
- - name: Scooter
- Все
- - Композиция остальных типов

### Тип аренды
- Поминутная
- - id: 1
- - name: Minutes
- Дневная
- - id: 2
- - name: Days

### Пользователи
- Пользователь YCb4Afsmgq0o3UpAei33eOs6lSutJJwb
- - id: 1
- - username: YCb4Afsmgq0o3UpAei33eOs6lSutJJwb
- - password: qtaZcNBxuVGDBJUv2awbgOOH035tFICZ
- - - Пароли пользователей хранятся в SHA-256 password(solt + original)
- - Роль: Администратор
- - Имеющийся транспорт
- - - Транспорт с id 1

### Транспорт
- Транспорт с id 1
- - Владелец: Пользователь YCb4Afsmgq0o3UpAei33eOs6lSutJJwb
- - Тип транспорта: Автомобиль
- - Модель, Цвет, Номерной знак, Описание
- - - Неизвестно
- - Координаты: 0.0; 0.0
- - Тарифы аренды / денежная единица
- - - Поминутный: 0 (д.е)
- - - Посуточный: 1 (д.е)

> В техническом задании не уполминались следующие сущности: banned_tokens (заблокированные токены), payments (платежи
> или выставленные счета)

### banned_tokens
> Токены, которые еще не успели выйти за пределы expired и по-прежнему могут использоваться, но не является валидными
> в системе и **не** должны быть пропущены системой безопасности

> Такие токены каждые 10 минут (по-умолчанию) автоматически удаляются из [TokenLifeService](./src/main/java/org/jeugenedev/simbir/service/TokenLifeService.java),
> при условии, что время жизни токена expired истекло

### payments
> Все платежи, проведенные в системе, в том числе неоплаченные (флаг done), при смене done на значение true с аккаунта
> payer списывается amount денежных единиц, при условии, что на аккаунте payer имеется достаточная сумма balance

## Почему нельзя удалять, изменять, частично изменять rents в системе?
> Таблица rents, как и сущность Rent, не являются логически свободными логическими единицами в бизнес-логике проекта,
> это значит что любое изменение из вне внутри rents может повлечь ошибки как в текущей реализации, так и в будущих реализациях,
> основанных на данной реализации проекта. Изменения внутри таблицы rents могут являться составными и требовать соблюдение
> зависимости между двумя различными столбцами, раздельное изменение которых может привести к инвалидации данных. Ранее
> была описана только архитектурная причина, есть также множество причин, касающихся ГК РФ, относительно клиентов сервиса.
> Rent отражает запись или историю поездки (аренды), история может быть изменена единожды, с помощью специального метода:
> ```/rents/close/{rent_id}``` [RentController](./src/main/java/org/jeugenedev/simbir/controller/RentController.java)

> Если необходмость в изменении Rent превышает более чем 100%, тогда в [RentRepository](./src/main/java/org/jeugenedev/simbir/repository/RentRepository.java)
> следует изменить код до следующего состояния (изменить значение exported с false на true для методов save(S entity) и delete(Rent entity))

```java
@RestResource(exported = true)
@Override
<S extends Rent> S save(S entity);
@RestResource(exported = true)
@Override
void delete(Rent entity);
```