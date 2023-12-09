# Overview

JAXB is a Java API for XML Binding, it allows to convert Java objects into XML documents and vice versa

### Summary

- [Dependencies](#dependencies)
- [JAXB Annotations](#jaxb-annotations)
  - [class](#class)
    - [@XmlAccessorType](#xmlaccessortype)
    - [@XmlRootElement](#xmlrootelement)
    - [@XmlType](#xmltype)
  - [variable](#variable)
    - [@XmlAttribute](#xmlattribute)
    - [@XmlElement](#xmlelement)
    - [@XmlTransient](#xmltransient)
    - [@XmlElementWrapper](#xmlelementwrapper)
    - [@XmlJavaTypeAdapter](#xmljavatypeadapter)
    - [@XmlInverseReference](#xmlinversereference)
      - [dependency](#dependency)
- [JAXB API](#jaxb-api)
  - [Marshalling](#marshalling)
  - [Unmarshalling](#unmarshalling)

## Dependencies

```xml
<dependencies>
  <dependency>
    <groupId>jakarta.xml.bind</groupId>
    <artifactId>jakarta.xml.bind-api</artifactId>
    <version>3.0.1</version>
  </dependency>
  
  <dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>3.0.1</version>
  </dependency>
  
  <dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-core</artifactId>
    <version>3.0.1</version>
  </dependency>
</dependencies>
```

## JAXB Annotations

## class

### @XmlAccessorType
defines how JAXB will access the fields of a class
- PROPERTY (default) : access fields via getters and setters
- FIELD : access fields directly
- PUBLIC_MEMBER : access public fields and getters/setters
- NONE : access nothing

this just defines what and how JAXB will access, you can still use the variables annotations to define how the fields will be serialized
### @XmlRootElement

defines the root element of an XML document

```java
@XmlRootElement(name = "book")
class Book {
    //...
}
```
```xml
<book>...</book>
```
### @XmlType

#### arguments

##### propOrder

defines the order of the fields in the XML document
```java
@XmlType(propOrder = {"id", "title", "author"})
class Book {
    //...
}
```
```xml
<book>
    <id>1</id>
    <title>...</title>
    <author>...</author>
</book>
```

## variable

### @XmlAttribute

marks a field or property as an XML attribute
#### arguments

- **name** - optional, defines the name of the attribute in xml

```java
class Book {
	@XmlAttribute
	private Long id;
}
```
```xml
<book id="1">...</book>
```

### @XmlElement
marks a field or property as an XML element
#### arguments

- **name** - optional, defines the name of the attribute in xml
```java
class Book {
    @XmlElement
    private String title;
}
```
```xml
<book>
    <title>...</title>
</book>
```

### @XmlTransient
marks a field or property as transient, i.e. it will not be serialized

```java
class Book {
    @XmlTransient
    private String title;
}
```
```xml
<book>...</book>
```

### @XmlElementWrapper
marks a field or property as an XML element, but wraps it in a wrapper element
often used to wrap collections

```java
class Book {
    @XmlElementWrapper(name = "authors")
    @XmlElement(name = "author")
    private List<String> authors;
}
```
```xml
<book>
    <authors>
        <author>...</author>
        <author>...</author>
    </authors>
</book>
```

### @XmlJavaTypeAdapter
allows to use a custom adapter to convert a field or property to/from XML

```java
class Book {
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate publicationDate;
}
```
for this, a custom adapter must be defined
this adapter must extend `XmlAdapter` and implement the `unmarshal` and `marshal` methods
```java
class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}
```

### @XmlInverseReference

#### dependency
```xml
<dependency>
  <groupId>org.eclipse.persistence</groupId>
  <artifactId>org.eclipse.persistence.moxy</artifactId>
  <version>4.0.1</version>
</dependency>
```
allows to define a bi-directional relationship between two objects

```java
class Book {
    private List<Chapter> chapters;
}
```
```java
class Chapter {
    @XmlInverseReference(mappedBy = "book")
    @Transient
    private Book book;
	private String content;
}
```
Now each chapter will have a reference to its book, and each book will have a list of its chapters
## JAXB API

### Marshalling

marshalling is the process of converting a Java object into an XML document

```java
class Marshal {
	public static void main(String[] args) throws JAXBException {
		Book book = new Book();
		JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // pretty print
		marshaller.marshal(book, System.out /* or any other OutputStream, like a file*/);
	}
}
// output (pretty printed) :
// <book>
```

### Unmarshalling
unmarshalling is the process of converting an XML document into a Java object

```java
class Unmarshal {
    public static void main(String[] args) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Book book = (Book) unmarshaller.unmarshal(new File("book.xml"));
    }
}
```
