# Запись на практикумы
### Авторы проекта
[Дарья Кашина](https://gitlab.akhcheck.ru/darya.kashina)

### Описание проекта
Проект направлен на обеспечение удобства студентов при записи на дополнительные семинары по высшей математике. 
Внедрила удобный функционал в Telegram-бота, позволяющий студентам сохранить свои данные и записаться на 
интересующий практикум без необходимости вводить их заново каждый раз.

[Бот](https://t.me/MIPTRegistrationForWorkshopsBot)

[Таблица](https://docs.google.com/spreadsheets/d/1kw91f5v-LfkUGEJNlEZ8UP9u0Mk1rV6V5FF91qmdgoE/edit#gid=984893817)

___

## Сборка и запуск
**Версия JDK - 17**

**Версия Maven - 3.9.5**


### Сборка

##### На UNIX
```
./mvnw clean package
```

##### На Windows
```
mvnw.cmd clean package
```


### Запуск
##### На UNIX
```
./mvnw spring-boot:run
```

##### На Windows
```
mvnw.cmd spring-boot:run
```
