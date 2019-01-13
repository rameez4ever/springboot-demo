Prerequisite: Java8, Maven3+, eclipse with JDK 8 compatibility.

Here are some basic details about technologies used.

1) Spring Boot 2.0.4.RELEASE
2) org.projectlombok -- To dynamically create getter setters and constructors. If you don't put lombak.jar at eclipse home it will give warnings in eclipse. 
3) com.fasterxml.jackson and org.json to process JSONs.
4) springfox-swagger2 -- For API documentation.
5) spring-boot-starter-actuator -- Helpful to monitor service.
6) com.h2database -- To use JPA beauty for filtering and sorting data using Spring Specification framework used in-memory h2 database.