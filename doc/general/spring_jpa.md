# Overview

Spring JPA is a library that allows you to easily create a data access layer for your application.

### Summary

- [Dependencies](#dependencies)
- [Configuration](#configuration)
	- [spring.jpa.hibernate.ddl-auto](#springjpahibernateddl-auto)
	- [logging.level.org.hibernate.SQL](#logginglevelorghibernatesql)
	- [spring.jpa.properties.hibernate.format_sql](#springjpapropertieshibernateformat_sql)
- [@Entity](#entity)
	- [@Id](#id)
	- [@GeneratedValue](#generatedvalue)
	- [@Column](#column)
	- [@Table](#table)
		- [@UniqueConstraint](#uniqueconstraint)
	- [@Transient](#transient)
	- [@OneToMany](#onetomany)
	- [@ManyToOne](#manytoone)
	- [@OneToOne](#onetoone)
	- [@ManyToMany](#manytomany)
	- [@JoinColumn](#joincolumn)
	- [@JoinTable](#jointable)
- [Repository](#repository)
	- [@Query](#query)
		- [@Param](#param)

## Dependencies

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
</dependencies>
```

include the used `database driver` as well, for **postgres** it would be:

```xml

<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

## Configuration

Add the following to your `application.properties` file to configure the database connection.

```properties
spring.datasource.url               = jdbc:database_driver://database_url:database_port/db_name
spring.datasource.username          = username
spring.datasource.password          = password
spring.datasource.driver-class-name = driver_class_name (for postgres it's org.postgresql.Driver)
spring.jpa.hibernate.ddl-auto       = update
```

### spring.jpa.hibernate.ddl-auto

This property is used to configure the database schema generation.

| Value       | Description                                                                          |
|-------------|--------------------------------------------------------------------------------------|
| create      | Creates the database schema on startup.                                              |
| create-drop | Creates the database schema on startup and drops it when the application shuts down. |
| update      | Updates the database schema on startup.                                              |
| validate    | Validates the database schema on startup.                                            |
| none        | Does nothing.                                                                        |

### logging.level.org.hibernate.SQL

This property is used to configure the logging level of the SQL queries.

| Value | Description                              |
|-------|------------------------------------------|
| DEBUG | Logs the SQL queries.                    |
| TRACE | Logs the SQL queries and the parameters. |

### spring.jpa.properties.hibernate.format_sql

This property is used to format the SQL queries and make them more readable.

## @Entity

This annotation is used to mark a class as an entity.

```java

@Entity
public class User {
}
```

### @Id

This annotation is used to mark a field as the primary key.

```java

@Entity
public class User {
	@Id
	private int id;
}
```

### @GeneratedValue

This annotation is used to configure the generation of the primary key.

```java

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
}
```

it can be used with the following strategies:

| Value    | Description                              |
|----------|------------------------------------------|
| IDENTITY | Uses an identity column in the database. |
| SEQUENCE | Uses a sequence in the database.         |
| TABLE    | Uses a table in the database.            |
| AUTO     | Uses the default strategy.               |

### @Column

This annotation is used to configure the column of a field.

```java

@Entity
public class User {
	@Column(name = "user_name", nullable = false, unique = true)
	private String name;
}
```

### @Table

This annotation is used to configure the table of an entity.

```java

@Entity
@Table(name = "users", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "birthday"}))
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private LocalDate birthday;
}
```

#### @UniqueConstraint

This annotation is used to configure a unique constraint on the table.

### @Transient

This annotation is used to mark a field as transient, which means that it will not be persisted.
Any field that is not annotated with `@Column` or `@Id` is considered transient.

Data in transient fields will not be saved to the database.

```java

@Entity
public class User {
	@Transient
	private String name;
}
```

### @OneToMany

This annotation is used to configure a one-to-many relationship.

```java

@Entity
public class User {
	@OneToMany(mappedBy = "user")
	private List<Book> books;
}
```

### @ManyToOne

This annotation is used to configure a many-to-one relationship.

```java

@Entity
public class Book {
	@ManyToOne
	private User user;
}
```

### @OneToOne

This annotation is used to configure a one-to-one relationship.

```java

@Entity
public class User {
	@OneToOne
	private Book book;
}
```

### @ManyToMany

This annotation is used to configure a many-to-many relationship.

```java

@Entity
public class User {
	@ManyToMany
	private List<Book> books;
}
```

```java

@Entity
public class Book {
	@ManyToMany
	private List<User> users;
}
```

### @JoinColumn

This annotation is used to configure the join column of a relationship.

JPA also works **without** `@JoinColumn`, but it will use its own naming convention for the join column.

```java

@Entity
public class Book {
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
}
```

### @JoinTable

This annotation is used to configure the join table of a many-to-many relationship.

JPA also works **without** `@JoinTable`, but it will use its own naming convention for the join table.

```java

@Entity
public class User {
	@ManyToMany
	@JoinTable(name = "user_book", joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "book_id"))
	private List<Book> books;
}
```

```java

@Entity
public class Book {
	@ManyToMany
	@JoinTable(name = "user_book", joinColumns = @JoinColumn(name = "book_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;
}
```

## Repository

A repository is an interface that extends `JpaRepository` or one of its subinterfaces.

It should also be annotated with [`@Repository`](spring.md#repository).

```java
public interface UserRepository extends JpaRepository<User, Integer> {
}
```

The repository provides methods to access the database,
many of them are already implemented by `JpaRepository`,
but you can also define your own methods using the spring data query methods or custom queries.

### @Query

This annotation is used to define a custom query.

```java
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.name = :name")
	List<User> findByName(@Param("name") String name);
}
```

#### @Param

This annotation is used to define a parameter of a custom query.
