# Spring Blog App

Spring Blog App is a web blogging platform hosted on Railway.app [here](https://jovertkispringblog.up.railway.app).

With this project I aimed to write a blogging platform to improve my skills with Spring Framework and to introduce myself to MongoDB, Thymeleaf, Tailwind, HTML and CSS.

Spring Blog App allows users to post in a single feed.
## Installation

To run it locally clone local_development branch and run
```bash
docker-compose up
```

## Usage

The project initially fills database with a couple of posts, a user and an admin.

Access points:

* [localhost:8080](localhost:8080) - site 
* [localhost:8081](localhost:8081) - mongo-express

Any user can create a post with a title, an image and text.

User can edit their posts.

Only admin can delete posts.

Images are stored in database

## Tests

You can run unit tests with
```bash
mvn clean test
```
