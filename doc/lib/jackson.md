# Overview
Jackson is a Java API that allows you to convert objects to JSON and vice versa.

### Summary

- [Dependencies](#dependencies)
- [Jackson Annotations](#jackson-annotations)
  - [class](#class)
    - [@JsonPropertyOrder](#jsonpropertyorder)
    - [@JsonIgnoreProperties](#jsonignoreproperties)
  - [variable](#variable)
    - [@JsonProperty](#jsonproperty)
    - [@JsonAnyGetter](#jsonanygetter)
    - [@JsonAnySetter](#jsonanysetter)
    - [@JsonSerialize](#jsonserialize)
    - [@JsonDeserialize](#jsondeserialize)
    - [@JsonAlias](#jsonalias)
    - [@JsonIgnore](#jsonignore)
    - [@JsonUnwrapped](#jsonunwrapped)
    - [@JsonManagedReference / @JsonBackReference](#jsonmanagedreference--jsonbackreference)
    - [@JsonRootName](#jsonrootname)
- [Jackson API](#jackson-api)
  - [ObjectMapper](#objectmapper)
    - [serialize](#serialize)
    - [deserialize](#deserialize)

## Dependencies

```xml
<dependencies>
  <dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.14.1</version>
  </dependency>
</dependencies>
```

## Jackson Annotations

### class

#### @JsonPropertyOrder
defines the order of the fields in the JSON
```java
@JsonPropertyOrder({"id", "name", "age"})
public class Person {
    private int id;
    private String name;
    private int age;
}
```
#### @JsonIgnoreProperties
defines which fields will be ignored when serializing/deserializing
```java

@JsonIgnoreProperties({"id", "name", "randomValue"})
public class Person {
    private int id; // will be ignored
    private String name; // will be ignored
    private int age;
}
```

```json
{
  "age": 20,
  "randomValue": "random"
  // will be ignored as well
}
```

### variable

#### @JsonProperty
defines the name of the field in the JSON

```java
public class Person {
    @JsonProperty("person_id")
    private int id;
}
```
```json
{
  "person_id": 1
}
```

#### @JsonAnyGetter
defines a method that will be used to get the map of additional properties, that are not defined in the class

```java
public class Person {
    private Map<String, String> additionalProperties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, String> getAdditionalProperties() {
        return additionalProperties;
    }
}
```
#### @JsonAnySetter
defines a method that will be used to set the additional properties, that are not defined in the class

```java
public class Person {
    private Map<String, String> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalProperties(String key, String value) {
        additionalProperties.put(key, value);
    }
}
```

#### @JsonSerialize
defines a custom serializer for the field
```java
public class Person {
    @JsonSerialize(using = CustomSerializer.class)
    private String name;
}
```
**CustomSerializer.java**
```java
public class CustomSerializer<T> extends StdSerializer<T> {
    public CustomSerializer() {
        this(T.class);
    }
	@Override
	public void serialize(
			T value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		jgen.writeStartObject();
		jgen.writeNumberField("someValueInsideTObject", t.getSomeValueInsideTObject());
		jgen.writeEndObject();
	}
}
```

#### @JsonDeserialize
defines a custom deserializer for the field
```java
public class Person {
    @JsonDeserialize(using = CustomDeserializer.class)
    private String name;
}
```
**CustomDeserializer.java**
```java
public class CustomDeserializer extends StdDeserializer<String> {
    public CustomDeserializer() {
        this(String.class);
    }
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String text = jsonParser.getText(); // text is the value of the field
        return text.toUpperCase();
    }
}
```

#### @JsonFormat

defines the format of the date field

```java
public class Person {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate birthDate;
}
```

```json
{
  "birthDate": "2022-01-01"
}
```

##### JavaTimeModule

to use the `@JsonFormat` annotation, you need to register the `JavaTimeModule` module

```java
static {
  ObjectMapper mapper = new ObjectMapper();
  mapper.registerModule(new JavaTimeModule());
}
```

#### @JsonAlias
defines an alias for the field
```java
public class Person {
    @JsonAlias({"person_id", "id"})
    private int id;
}
```
```json
{
  "person_id": 1
}
```

or

```json
{
  "id": 1
}
```
#### @JsonIgnore
defines that the field will be ignored when serializing/deserializing

```java
public class Person {
    @JsonIgnore
    private int id;
  private String name;
}
```

```json
{
  "name": "John"
}
```
#### @JsonUnwrapped
defines that the field will be unwrapped when serializing/deserializing
```java
public class Person {
    @JsonUnwrapped
    private Address address;
}
```
```json
{
  "street": "Main Street",
  "city": "New York"
}
```
#### @JsonManagedReference / @JsonBackReference
defines that the fields will be serialized/deserialized as a reference

- `managed` reference is serialized as a value
- `back` reference is ignored
```java
public class Person {
    @JsonManagedReference
    private Address address;
    private String name;
}
public class Address {
    private String street;
    private String city;
    @JsonBackReference
    private Person person;
}
```
**Person.json**
```json
{
  "name": "John",
  "address": {
    "street": "Main Street",
    "city": "New York"
  }
}
```
**Address.json**
```json
{
  "street": "Main Street",
  "city": "New York"
}
```
#### @JsonRootName
defines the root name of the JSON
```java
@JsonRootName("person")
public class Person {
    private int id;
    private String name;
}
```
```json
{
  "person": {
    "id": 1,
    "name": "John"
  }
}
```
## Jackson API

### ObjectMapper
used to convert objects to JSON and vice versa
#### serialize
use the `writeValueAsString` method to serialize an object to JSON

```java
public class Serializer {
    public <T> String serialize(T object) throws JsonProcessingException {
       ObjectMapper mapper = new ObjectMapper();
       return mapper.writeValueAsString(T);
    }
}
```

#### deserialize
use the `readValue` method to deserialize JSON from a file to an object

```java
public class Deserializer {
    public <T> T deserialize(String path) throws IOException {
       ObjectMapper mapper = new ObjectMapper();
       T object = mapper.readValue(new File(path), T.class);
	   return object;
    }
}
```
use the `readerFor().readValue` method to deserialize JSON from a string to an object

```java
public class Deserializer {
    public <T> T deserialize(String json) throws IOException {
       ObjectMapper mapper = new ObjectMapper();
       T object = mapper.readerFor(T.class).readValue(json);
       return object;
    }
}
```
