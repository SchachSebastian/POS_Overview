# Overview

Spring is a framework for building web applications.
Together with Thymeleaf, it provides a lot of functionality to build dynamic webpages.

### Summary

- [Main](#main)
- [Controller](#controller)
	- [@GetMapping({""})](#getmapping)
	- [@ResponseBody](#responsebody)
	- [@PathVariable()](#pathvariable)
	- [@RequestParam()](#requestparam)
	- [@ModelAttribute()](#modelattribute)
	- [@PostMapping({""})](#postmapping)
	- [@SessionAttributes({""})](#sessionattributes)
- [Templates](#templates)
	- [expressions](#expressions)
		- [variables/standard expressions](#variablesstandard-expressions)
		- [selection variable expressions](#selection-variable-expressions)
		- [link url expressions](#link-url-expressions)
	- [attributes](#attributes)
		- [th:attr](#thattr)
	- [special attributes](#special-attributes)
		- [th:if](#thif)
		- [th:unless](#thunless)
		- [th:switch](#thswitch)
			- [th:case](#thcase)
		- [th:each](#theach)
		- [th:object](#thobject)
		- [th:field](#thfield)
- [Logging](#logging)

## Main

The main class is the entry point of the application.

```java

@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
```

## Controller

A controller is a class that handles requests and returns a response.
It's usually annotated with `@Controller` and contains methods annotated with `@GetMapping` or `@PostMapping`.

```java

@Controller
public class MainController {
}
```

### @GetMapping({""})

This annotation is used to map HTTP GET requests onto specific handler methods.
It returns a view name or `ModelAndView`.

**View name**

```java

@Controller
public class MainController {
	@GetMapping({"/", "/index"})
	public String index() {
		return "index"; // this returns index.html from templates
	}
}
```

**ModelAndView**

```java

@Controller
public class MainController {
	@GetMapping({"/", "/index"})
	public String index() {
		ModelAndView mv = new ModelAndView("index"); // set the view to index.html
		mv.addObject("message", "Hello World"); // add objects required by the view to the model
		return mv;
	}
}
```

**Model as parameter**
Using a `Model` as a parameter in a method will make it available to the view.

```java
@Controller
public class MainController {
	@GetMapping({"/", "/index"})
	public String index(Model model) {
		model.addAttribute("message", "Hello World"); // add objects required by the view to the model
		return "index"; // this returns index.html from templates
	}
}
```

### @ResponseBody

Adding this annotation to a method will make it return a response body instead of a view name|ModelAndView.

```java

@Controller
public class MainController {
	@GetMapping({"/", "/index"})
	@ResponseBody
	public String index(Model model) {
		return "Index Page"; // this returns "Index Page" as a response body
	}
}
```

### @PathVariable()

This annotation is used to map a path variable to a method parameter.

```java

@Controller
public class MainController {
	@GetMapping({"/category/{id}"})
	public String index(@PathVariable("id") int id, Model model) {
		model.addAttribute("id", id); // adds the obtained id to the model
		return "category"; // this returns "category.html" as a response body
	}
}
```

### @RequestParam()

This annotation is used to map a request parameter to a method parameter.

```java

@Controller
public class MainController {
	@GetMapping({"/category"})
	public String index(@RequestParam("id") int id, Model model) {
		model.addAttribute("id", id); // adds the obtained id to the model
		return "category"; // this returns "category.html" as a response body
	}
}
```

### @ModelAttribute()

This annotation can be used to map a model attribute to a method parameter

```java

@Controller
public class MainController {
	@PostMapping({"/category"})
	public String index(@ModelAttribute("category") Category category) {
		return "category"; // this returns "category.html"
		// as a response body containing the category object as a model attribute
	}
}
```

or to make it available to all methods in the controller via `@ModelAttribute` on a getter method.
Note that this method will be called **before** each request.

```java

@Controller
public class MainController {
	@ModelAttribute("category")
	public Category category() {
		return new Category();
	}
}
```

adding `@ControllerAdvice` to a class will make it available to all controllers.

### @PostMapping({""})

This annotation is used to map HTTP POST requests onto specific handler methods.

```java

@Controller
public class MainController {
	@PostMapping({"/category"})
	public String index(@ModelAttribute("category") Category category, Model model) {
		model.addAttribute("category", category); // adds the obtained category to the model
		return "category"; // this returns "category.html" as a response body
	}
}
```

### @SessionAttributes({""})

This annotation is used to store model attributes in the session.

```java

@Controller
@SessionAttributes({"object"})
public class MainController {
	@ModelAttribute("category")
	public Category category() {
		// will only be called once per session
		return new Category();
	}
}
```

## Templates

To use templates, add the following dependency to your `pom.xml` file.

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

and add the following to every html template file to add thymeleaf support.

```html

<html lang="en" xmlns:th="http://www.thymeleaf.org">
</html>
```

### expressions

#### variables/standard expressions

variables/standard expressions are prefixed with `$` and can be used to access variables in the model.

```html

<div th:text="${message}"></div>
```

this will set the text of the div to the value of the variable `message` in the model.

#### selection variable expressions

selection variable expressions are prefixed with `*` and can be used to select a variable from an object.

```html

<div th:object="${object}">
    <p th:text="*{field}"></p> <!-- this will resolve to ${object.field} -->
</div>
```

#### link url expressions

link url expressions are prefixed with `@` and can be used to link to a controller method.

```html
<a th:href="@{/path}">link</a>
```

### attributes

thymeleaf attributes are prefixed with `th:`.

```html

<div th:text="${message}"></div>
```

this will set the attribute to the value specified by thyemleaf.
Via Thymeleaf you can set all attributes that are available in HTML.
For example:

- `th:text` will set the text of an element
- `th:src` will set the source of an element
- `th:href` will set the href of an element
- `th:style` will set the style of an element
- `th:value` will set the value of an element

There are many more attributes, see
the [thymeleaf documentation](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values).

#### th:attr

To set any native HTML attribute, you can use `th:attr` and specify the attribute name as the attribute name and the
value as the attribute value.

```html

<div th:attr="id=${x}"></div>
```

### special attributes

#### th:if

this attribute will only render the element if the condition is true.

```html

<div th:if="${condition}"></div>
```

#### th:unless

this attribute will only render the element if the condition is false.

```html

<div th:unless="${condition}"></div>
```

#### th:switch

this attribute will switch between the elements inside it based on the value of the specified variable.

```html

<div th:switch="${variable}">
    <p th:case="'1'">Value 1</p>
    <p th:case="'2'">Value 2</p>
    <p th:case="*">default</p> <!-- this will be the default case -->
</div>
```

##### th:case

this attribute will be used to specify a case in a `th:switch` element.

#### th:each

this attribute will iterate over a list and render the element for each item in the list.

```html

<div th:each="item : ${list}">
    <p th:text="${item}"></p>
</div>
<!-- list = ["item 1", "item 2", "item 3"] -->
<!-- this will render the following html -->
<div>
    <p>item 1</p>
    <p>item 2</p>
    <p>item 3</p>
</div>
```

#### th:object

this attribute will set the object that will be used to resolve fields in the template.

```html

<div th:object="${object}">
    <p th:text="${field}"></p> <!-- this will resolve to ${object.field} -->
</div>
```

#### th:field

this attribute will set the field that will be used to store the value of the element.

```html

<div th:object="${object}">
    <input th:field="*{field}"/> <!-- this will resolve to ${object.field} -->
</div>
```

## Logging

`@Slf4j` is a Lombok annotation that adds a logger to the class.

```java

@Slf4j
public class MainController {
	public void index() {
		log.info("Hello World");
	}
}
```
