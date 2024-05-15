[JAVA_BADGE]:https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[SPRING_SECURITY_BADGE]: https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white
[MYSQL_BADGE]:https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white
[DOCKER_BADGE]:https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white
[SWAGGER_BADGE]:https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white

<h1 align="center" style="font-weight: bold;">Courses Platform API<?xml version="1.0" ?></h1>

![java][JAVA_BADGE]
![spring][SPRING_BADGE]
![spring-security][SPRING_SECURITY_BADGE]
![mysql][MYSQL_BADGE]
![docker][DOCKER_BADGE]
![swagger][SWAGGER_BADGE]

<p align="center">
 <a href="#started">Getting Started</a> • 
  <a href="#routes">API Endpoints</a> •
 <a href="#colab">Collaborators</a> •
 <a href="#contribute">Contribute</a>
</p>

<p align="center">
  <b>The Courses Platform API is designed to manage and interact with an online courses system. The API allows for user authentication, enabling secure access to its features. Users can register new accounts, create and manage courses, enroll in available courses, and provide reviews and ratings for the courses they have completed. Additionally, the API supports generating Net Promoter Score (NPS) reports for each course to assess user satisfaction.  Java 22, Spring Boot, Spring Security, Oauth2, JWT, MySQL, FlyWay, Swagger and Docker were used for development.</b>
</p>


<h2 id="started">🚀 Getting started</h2>

<h3>Prerequisites</h3>

- [Docker](https://docs.docker.com/install/)
- [Git](https://git-scm.com/downloads)

<h3>Cloning</h3>

- After installing docker and git, clone the project with the following command:
    ```
    git clone https://github.com/tarikwesley/courses-platform-api.git
    ```

<h3>Starting</h3>

- Access the cloned project directory.
- Run the command ```docker-compose up -d``` to have docker start the project services in the background.
  - When you obtain the following status below, you will be able to test the application.

  ```bash
  ✔ Network courses-platform-api_network  Created
  ✔ Container courses-platform-db         Started
  ✔ Container courses-platform-api        Started
  ```

Once everything is ok, continue the process.

- After this process, the API can be tested. 
- To test API requests, you can use [Postman](https://www.getpostman.com/downloads/).
- To close the application use the command ``` docker-compose down```.

<h2 id="routes">📍 API Endpoints</h2>

<h3> 📍 Doc Swagger</h3>

```http://localhost:8081/swagger-ui/index.html```

<h3>📍 Internal API</h2>

| Route                                                     | Description                                                |
|-----------------------------------------------------------|------------------------------------------------------------|
| <kbd>POST /api/login</kbd>                                | Authentication from user.                                  |
| <kbd>POST /api/users</kbd>                                | Create new user.                                           |
| <kbd>GET /api/users/{username}</kbd>                      | Returns the user belonging to the given username info.     |             
| <kbd>POST /api/courses</kbd>                              | Create new course.                                         |
| <kbd>GET /api/courses?page=0&size=2</kbd>                 | Returns all courses paginate.                              |
| <kbd>GET /api/courses?status={status}&page=0&size=2</kbd> | Returns all courses paginate filter by status.             |
| <kbd>PATCH /api/courses/{code}</kbd>                      | Disable course by code.                                    |
| <kbd>POST /api/registrations</kbd>                        | Create new registration.                                   |
| <kbd>POST /api/reviews</kbd>                              | Create new review.                                         |
| <kbd>GET /api/reviews/user/{userId}</kbd>                 | Returns all reviews belonging to the given user id info.   |
| <kbd>GET /api/reviews/courses/{courseId}</kbd>            | Returns all courses belonging to the given course id info. |
| <kbd>GET /api/reviews/nps</kbd>                           | Returns report nps by course.                              |


<h2 id="colab">🤝 Collaborators</h2>

Special thank you for all people that contributed for this project.

<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="https://avatars.githubusercontent.com/u/47906316?v=4" width="100px;" alt="Tarik Wesley Profile Picture"/><br>
        <sub>
          <b>Tarik Wesley</b>
        </sub>
      </a>
    </td>

  </tr>
</table>

<h2 id="contribute">📫 Contribute</h2>

1. `git clone https://github.com/tarikwesley/courses-platform-api.git`
2. `git checkout -b feature/NAME`
3. Follow commit patterns
4. Open a Pull Request explaining the problem solved or feature made, if exists, append screenshot of visual modifications and wait for the review!

<h3>Documentations that might help</h3>

[📝 How to create a Pull Request](https://www.atlassian.com/br/git/tutorials/making-a-pull-request)

[💾 Commit pattern](https://github.com/iuricode/padroes-de-commits)