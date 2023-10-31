# SimbirSoft LLC. Functional Program LICENSE Rent Service Exclusive ©️
## Перед работой
- Java 17
- Maven
- PostgreSQL (>10)
## Сборка проекта
- ```mvn clean package```
> Архив проекта после сборки будет находиться в папке ```target```

> Готовая сборка **возможно** будет в релизах репозитория

**Для запуска .jar файла (или сборки) используйте** ```java -jar <Name>.jar```
> Дополнительные аргументы при запуске архива не используются
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
- springdoc.swagger-ui.path: путь до страницы с **swagger** документацией

### logic.properties
- auth.username.param: никнейм пользователя, использующийся в JWT
- auth.secret: секретный ключ, использующийся для подписи JWT
- jwt.tokens.expired.minutes: длительность жизни JWT в минутах
- password.secret: секретный ключ, использующийся для хеширования паролей пользователей
