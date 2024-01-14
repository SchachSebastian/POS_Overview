# Overview

Spring is a framework for building web applications.

### Summary

- [Dependencies](#dependencies)
- [Main](#main)
- [@Component](#component)
- [Controller](#controller)
	- [@Controller](#controller)
	- [@RestController](#restcontroller)
	- [@RequestMapping()](#requestmapping)
	- [@GetMapping({""})](#getmapping)
	- [@ResponseBody](#responsebody)
	- [@PathVariable()](#pathvariable)
	- [@RequestParam()](#requestparam)
	- [@RequestBody](#requestbody)
	- [@ModelAttribute()](#modelattribute)
	- [@PostMapping({""})](#postmapping)
	- [@SessionAttributes({""})](#sessionattributes)
- [@Service](#service)
- [@Repository](#repository)
- [Validation](#validation)
	- [@Valid](#valid)
	- [Validation annotations](#validation-annotations)
	- [ValidationMessages.properties](#validationmessagesproperties)
- [@PostStruct](#poststruct)

## Dependencies

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

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

## @Component

This annotation is used to mark a class as a component.
It's used to create Spring beans of the annotated classes.

```java
@Component
public class SomeComponent {
}
```

Spring beans can be injected into other classes using the `@Autowired` annotation or by using constructor injection.

```java
@Controller
public class MainController {
	private final SomeComponent someComponent;
	public MainController(SomeComponent someComponent) {
		this.someComponent = someComponent;
	}
}
```

## Controller

A controller is a class that handles requests and returns a response.

### @Controller

This annotation is used to mark a class as a controller.
It's usually used for MVC controllers.
`@Controller` is a specialization of `@Component`.

```java
@Controller
public class MainController {
}
```

### @RestController

This annotation is used to mark a class as a controller that returns a response body instead of a view name.
It's usually used for REST APIs.
`@RestController` is a specialization of `@Controller`.

```java

@RestController
public class MainController {
}
```

### @RequestMapping()

This annotation is used to map a request to a method.

You can specify the **path** of the request in the annotation and the **method type**.

```java

@Controller
public class MainController {
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "index"; // this returns index.html from templates
	}
}
```

It can also be used to map a request to a class.

```java

@Controller
@RequestMapping("/category")
public class CategoryController {
	@RequestMapping({"/", "/index"})
	public String index() {
		return "category"; // this returns category.html from templates
	}
}
```

### @GetMapping({""})

Is a shortcut for `@RequestMapping(method = RequestMethod.GET)`.
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

### @RequestBody

This annotation is used to map the request body to a method parameter.

```java

@Controller
public class MainController {
	@PostMapping({"/category"})
	public String add(@RequestBody Category category) {
		// code
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

Is a shortcut for `@RequestMapping(method = RequestMethod.POST)`.
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

## @Service

This annotation is used to mark a class as a service.
It's usually used to annotate classes that contain business logic.
`@Service` is a specialization of `@Component`.

```java
@Service
public class MainService {
}
```

## @Repository

This annotation is used to mark a class as a repository.
It's usually used to annotate classes that contain database logic and connect to the database.
`@Repository` is a specialization of `@Component`.

```java
@Repository
public class CategoryRepository extends JpaRepository<Category, Integer> {
}
```

## Validation

To use validation, add the following dependency to your `pom.xml` file.

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

### @Valid

This annotation can be used to validate a model attribute.

```java
@Controller
public class MainController {
	@PostMapping({"/category"})
	public String index(@Valid @ModelAttribute("category") Category category, BindingResult result) {
		if (bindingResult.hasErrors()) {
			return "category";
		}
		return "category-success";
	}
}
```

The `BindingResult` parameter is used to check if there are any errors.
> Note that the `BindingResult` parameter must be directly after the model attribute parameter.

### Validation annotations

There are many validation annotations, a few of them are:

- `@NotNull` - validates that the annotated property value is not null
- `@AssertTrue` - validates that the annotated property value is true
- `@Size` - validates that the annotated property value has a size between the attributes min and max; can be applied to
  String, Collection, Map, and array properties
- `@Min` - validates that the annotated property has a value no smaller than the value attribute
- `@Max` - validates that the annotated property has a value no larger than the value attribute
- `@Email` - validates that the annotated property is a valid email address
  You can also have multiple of the same validation annotations.

Every validation annotation has a `message` attribute that can be used to specify a custom error message.

```java
public class Category {
	@NotNull(message = "Name is required")
	private String name;
}
```

### ValidationMessages.properties

This file is used to store validation messages.

```properties
category.name.notnull = Name is required
```

```java
public class Category {
	@NotNull(message = "{category.name.notnull}")
	private String name;
}
```

## @PostStruct

This annotation is used to mark a method that will be executed after the bean is initialized.

It can be used to load initial data into the database.

```java

@Component
public class SomeComponent {
	@PostConstruct
	public void init() {
		// will be executed after the bean is initialized
	}
}
```
