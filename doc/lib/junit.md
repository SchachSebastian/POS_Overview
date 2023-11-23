# Overview

JUnit is a unit testing framework for Java. It is used to test the behavior of classes and methods.
[</> JunitExample.java](../../src/test/java/junit/JunitExample.java)

### Summary

- [Dependencies](#dependencies)
- [Annotations](#annotations)
	- [@BeforeEach](#beforeeach)
	- [@BeforeAll](#beforeall)
	- [@Test](#test)
	- [assert](#assert)

## Dependencies
```xml

<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.8.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Annotations

### @BeforeEach

annotation that defines a method that is executed before each test

```java
class TestClass {
    @BeforeEach
    static void setup() {
       // do something
    }
}
```

### @BeforeAll

annotation that defines a method that is executed before all tests

```java
class TestClass {
	@BeforeAll
	static void setup() {
		// do something
	}
}
```

### @Test

annotation for test methods, must be public and void
method will be executed when the test runs

```java
class TestClass {
	@Test
	public void testMethod() {
		// do something
	}
}
```

### assert

used to test if the result is as expected
in `@Test` methods, there can be multiple assert statements
there are multiple assert methods, some of them are:

- `assertEquals`

```java
class TestClass {
	@Test
	public void testMethod() {
		// do something
		assertEquals(1, 1);
	}
}
```

- `assertTrue`

```java
class TestClass {
	@Test
	public void testMethod() {
		// do something
		assertTrue(1 == 1);
	}
}
```

- `assertNull`

```java
class TestClass {
	@Test
	public void testMethod() {
		// do something
		assertNull(null);
	}
}
```
