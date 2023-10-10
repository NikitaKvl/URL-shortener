# URL-shortener

Application for shortening long links.

The application has 2 APIs:

`POST: / ` - accepts long links and returns a shortened one

`GET: /{urlId}` - redirects to the original link

# Technologies used:
- Java 17
- Spring Boot, Spring Data
- MongoDB, Redis
- Mockito, JUnit

# How to run this project on your computer, you need:
1. Clone this project:
```bash
git clone https://github.com/NikitaKvl/URL-shortener
```
2. Add [Lombok](https://projectlombok.org/setup/overview) plugin to your IDE
3. Install [Docker](https://www.docker.com/products/docker-desktop/) on your local machine
4. Run docker-compose file
```bash
docker-compose up
```

After all these steps you will be able to run this project locally.
