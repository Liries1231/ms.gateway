# ms-gateway

## Описание

Microservice Gateway (Мс-гейтвей) предназначен для проксирования запросов в микросервисы и управления безопасностью. Гейтвей валидирует JWT-токены всех запросов, пропуская только аутентифицированные запросы.

## Основные функции

- **JWT-токен фильтр**: валидация JWT-токена для всех входящих запросов.
- **Проксирование запросов**: перенаправление запросов в микросервисы (например, ms-user и ms-post).
- **Централизованная безопасность**: единое место для проверки безопасности.

## Технологии

- **Java 17**
- **Spring Boot**
- **Spring Cloud Gateway**
- **JSON Web Tokens (JWT)**

## Установка и запуск

1. Склонируйте репозиторий:
   ```bash
   git clone <repository_url>
   cd ms-gateway
   ```
2. Настройте `application.yml` (если требуется). Обратите внимание на настройки URI для микросервисов.
3. Соберите и запустите приложение:
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

## Структура проекта

- **JWTFilter**: компонент для валидации JWT-токена.
- **PostServiceProxy** и **UserServiceProxy**: определяют маршруты к сервисам.
- **PostProxyController**: 
  - Реализует эндпоинты для работы с сервисом постов.
  - Методы: 
    - `GET /api/v1/post/{id}`: проксирует запрос на получение поста по ID в сервис постов.
    - `POST /api/v1/post`: создаёт пост, перенаправляя запрос в сервис постов, передавая также идентификатор пользователя через заголовок.

## Тестирование

1. Возможность запуска с использованием Postman или Curl.
2. Проверите JWT-токен и доступ к защищённым эндпоинтам.



