## БД
- Установить psql
- Инициировать бд
  ```bash
  createdb -h localhost -p 5432 sd-web
  psql -d sd-web -a -f server/src/main/sql/init.sql
  ```

## Брокер сообщений

- Установить RabbitMQ

## Конфигурация

- `config.yml`

- Заполнить пользователя и пароль для доступа к базе данных

## Тестирующий процесс

- Собрать fatJar
  ```bash
  ./gradlew :runner:fatJar
  ```
- Запустить (в директории с `config.yml`)
  ```bash
  java -jar runner/build/libs/Runner-0.0.1.jar
  ``` 

## Сервер

- Собрать fatJar
  ```bash
  ./gradlew :server:fatJar
  ```
- Запустить (в директории с `config.yml`)
  ```bash
  java -jar server/build/libs/Server-0.0.1.jar
  ```
