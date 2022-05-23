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

## Тестирующие процессы

## Сервер
- Собрать fatJar
  ```bash
  ./gradlew :server:fatJar
  ```
- Запустить (в репозитории с `config.yml`)
  ```bash
  java -jar server/build/libs/Server-0.0.1.jar
  ```
