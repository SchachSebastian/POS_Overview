# Overview

Thymeleaf is a template engine for Java that can be used to create HTML templates.

### Summary

- [Dependencies](#dependencies)
- [Templates](#templates)
	- [expressions](#expressions)
		- [variables/standard expressions](#variablesstandard-expressions)
		- [selection variable expressions](#selection-variable-expressions)
		- [link url expressions](#link-url-expressions)
		- [message expressions](#message-expressions)
		- [fragment expressions](#fragment-expressions)
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
- [Displaying errors from Validation](#displaying-errors-from-validation)
- [messages.properties](#messagesproperties)
- [Expression Objects](#expression-objects)
	- [Basic Objects](#basic-objects)
	- [Utility Objects](#utility-objects)

## Dependencies

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
</dependencies>
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

#### message expressions

message expressions are prefixed with `#` and can be used to access messages from the `messages.properties` file.

```html

<div th:text="#{message}"></div>
```

#### fragment expressions

fragment expressions are prefixed with `~` and can be used to include a fragment from another template.

```html
<!--template.html-->
<div th:fragment="fragment"></div>
```

```html
<!--other.html-->
<div th:replace="~{template :: fragment}"></div>
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

## Displaying errors from Validation

To display errors, you can use the `th:errors` attribute together with `#fields.hasErrors()` and test which field has
errors.

```html

<div th:object="${category}">
    <input th:field="*{name}"/>
    <p th:if="${#fields.hasErrors('name')}" th:errors="*{name}"></p>
</div>
```

## messages.properties

This file is used to store messages that can be used in the templates.

```properties
message = Hello World
```

```html

<div th:text="#{message}"></div>
```

## Expression Objects

Expression objects are objects that can be used in expressions.
They are prefixed with `#` and can be used to access utility methods.
For a complete list of expression objects, see
the [thymeleaf documentation](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#appendix-b-expression-utility-objects).

### Basic Objects

- `#ctx` - the context object
- `#vars` - the context variables
- `#locale` - the context locale
- `#fields` - the context fields, used for validation (hasErrors)

### Utility Objects

- `#dates` - utility methods for java.util.Date objects
- `#calendars` - utility methods for java.util.Calendar objects
- `#numbers` - utility methods for java.lang.Number objects
- `#strings` - utility methods for java.lang.String objects
- `#objects` - utility methods for java.lang.Object objects
- `#bools` - utility methods for boolean values
- `#arrays` - utility methods for arrays
- ...
