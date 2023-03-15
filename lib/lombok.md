# Overview

Lombok is a Java API that allows you to generate boilerplate code such as getters, setters, constructors, etc. at
compile time.
It adds annotations to your code that will be processed by the Lombok library.

### Summary
- [Annotations](#annotations)
  - [@Getter](#getter)
  - [@Setter](#setter)
  - [@ToString](#tostring)
  - [@EqualsAndHashCode](#equalsandhashcode)
  - [@NoArgsConstructor](#noargsconstructor)
  - [@AllArgsConstructor](#allargsconstructor)
  - [@RequiredArgsConstructor](#requiredargsconstructor)
  - [@Data](#data)
  - [@Value](#value)
  - [@FieldDefaults](#fielddefaults)
  - [@Builder](#builder)
  - [How does Lombok work](#how-does-lombok-work)
## Annotations

### @Getter

Generates a getter for the annotated field.

```java
private class Person {
    @Getter
    private String name;
//	public String getName() {
//        return this.name;
//    }
}
```
or getters for all non-static fields of the class
```java
@Getter
private class Person {
    private String name;
    private int age;
//	public String getName() {
//        return this.name;
//    }
//	public int getAge() {
//        return this.age;
//    }
}
```
### @Setter
Generates a setter for the annotated field.

```java
private class Person {
    @Setter
    private String name;
//	public void setName(String name) {
//        this.name = name;
//    }
}
```
or setters for all non-static fields of the class
```java
@Setter
private class Person {
    private String name;
    private int age;
//  public void setName(String name) {
//        this.name = name;
//  }
//	public void setAge(int age) {
//          this.age = age;
//  }
}
```
### @ToString
Generates a toString method for the annotated class.

```java
@ToString
private class Person {
    private String name;
    private int age;
//	public String toString() {
//        return "Person(name=" + this.name + ", age=" + this.age + ")";
//    }
}
```
if you want to exclude some fields from the toString method, you can use the `@ToString.Exclude` annotation
```java
@ToString
private class Person {
    private String name;
    @ToString.Exclude
    private int age;
//	public String toString() {
//        return "Person(name=" + this.name + ")";
//    }
}
```
### @EqualsAndHashCode
Generates a equals and hashCode method for the annotated class.

```java
@EqualsAndHashCode
private class Person {
    private String name;
    private int age;
//	public boolean equals(Object o) {
//        return o == this || o instanceof Person && 
//                            ((Person)o).name.equals(this.name) && 
//                            ((Person)o).age == this.age;
//    }
//    public int hashCode() {
//        return 1;
//    }
}
```
if you want to exclude some fields from the equals and hashCode method, you can use the `@EqualsAndHashCode.Exclude` annotation
```java
@EqualsAndHashCode
private class Person {
    private String name;
    @EqualsAndHashCode.Exclude
    private int age;
//	public boolean equals(Object o) {
//        return o == this || o instanceof Person && 
//                            ((Person)o).name.equals(this.name);
//    }
//    public int hashCode() {
//        return 1;
//    }
}
```

### @NoArgsConstructor
Generates a no-args constructor for the annotated class.

```java
@NoArgsConstructor
private class Person {
    private String name;
    private int age;
//	public Person() {
//    }
}
```
### @AllArgsConstructor
Generates a constructor for the annotated class that takes all fields as arguments.

```java
@AllArgsConstructor
private class Person {
    private String name;
    private int age;
//	public Person(String name, int age) {
//        this.name = name;
//        this.age = age;
//    }
}
```
### @RequiredArgsConstructor
Generates a constructor for the annotated class that takes all final fields as arguments.

```java
@RequiredArgsConstructor
private class Person {
    private final String name;
    private int age;
//	public Person(String name) {
//        this.name = name;
//    }
}
```
### @Data
Generates getters, setters, equals, hashCode, required args constructor, and toString methods for the annotated class.

```java
@Data
private class Person {
    private String name;
    private int age;
}
```
instead of
```java
@Getter
@Setter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
private class Person {
    private String name;
    private int age;
}
```
### @Value
Generates getters, equals, hashCode, all args constructor, and toString methods for the annotated class.

```java
@Value
private class Person {
    private String name;
    private int age;
}
```
instead of
```java
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
private class Person {
    private String name;
    private int age;
}
```
### FieldDefaults
sets the default access level for fields and static fields.
can also be used to set variables as final.
```java
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
private class Person {
    /*private final*/ String name;
    /*private final*/ int age;
}
```
There also exists `makeStatic`, which can be used to set fields as static.
```java
@FieldDefaults(level = AccessLevel.PRIVATE, makeStatic = true)
private class Person {
    /*private static*/ String name;
    /*private static*/ int age;
}
```
### @Builder
Generates a builder for the annotated class.

```java
@Builder
private class Person {
    private String name;
    private int age;
//	public static PersonBuilder builder() {
//		return new PersonBuilder();
//	}
}
```

## How does lombok work
Lombok uses a Java annotation processor to generate the boilerplate code at compile time.
The annotation processor is executed by the Java compiler and generates the code that is then compiled with the rest of the code.
Other than other annotation processors, Lombok does not generate a new class file, but modifies the existing class file using the Java ASM library.