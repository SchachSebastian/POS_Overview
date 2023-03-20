# Overview

A stream is a sequence of elements supporting sequential and parallel aggregate operations.
Java 8 introduced the concept of streams to make it easy to process collections of data.
Stream methods can be chained to form complex queries on the data.

### Summary

- [Creating a Stream](#creating-a-stream)
- [Terminal Operations](#terminal-operations)
  - [collect](#collect)
  - [forEach](#foreach)
  - [count](#count)
  - [min/max](#minmax)
  - [findFirst/findAny](#findfirstfindany)
- [Intermediate Operations](#intermediate-operations)
  - [map](#map)
  - [filter](#filter)
  - [sorted](#sorted)
  - [distinct](#distinct)
  - [limit](#limit)
  - [flatMap](#flatmap)
- [Stream parameters](#stream-parameters)
  - [Function](#function)
  - [Consumer](#consumer)
  - [Predicate](#predicate)
  - [Comparator](#comparator)
  - [Collector](#collector)

## Stream

### Creating a Stream

a stream can be created from a collection, an array or by using a builder
therefore, different methods can be used to create a stream
the most common used method is `list.stream()`

A stream is always a `seperate data structure`, it is not the data structure that is used to create the stream.
For example, if you create a stream from a list, the stream is not the list itself and therefore, the list is not
modified when the stream is.

[</> StreamCreationExample.java](../../src/main/java/streams/StreamCreationExample.java)

To create a stream you can use

```
list.stream();
```

```
Arrays.stream({"a", "b", "c"});
```

```
Stream.of("a", "b", "c")
```

```
Stream.<String>builder().add("a").add("b").build();
```

### Terminal Operations

terminal operations return a result of a certain type instead of a stream
the most common used terminal operations are:

#### collect

`collect` is used to collect the elements of the stream into a collection
[</> StreamCollectExample.java](../../src/main/java/streams/StreamCollectExample.java)

```
stream.collect(Collector);
```

an easy way to collect a list is by just using:

```
stream.toList();
```

#### forEach

`forEach` is used to iterate over each element of the stream

```
stream.forEach(Function);
```

[</> StreamForEachExample.java](../../src/main/java/streams/StreamForEachExample.java)

#### count

`count` is used to count the elements of the stream
[</> StreamCountExample.java](../../src/main/java/streams/StreamCountExample.java)

```
stream.count();
```

#### min/max

`min` and `max` are used to find the minimum or maximum element of the stream
[</> StreamMinExample.java](../../src/main/java/streams/StreamMinMaxExample.java)

```
stream.min(Comparator);
stream.max(Comparator);
```

#### findFirst/findAny

`findFirst` and `findAny` are used to find the first or any element of the stream
[</> StreamFindExample.java](../../src/main/java/streams/StreamFindExample.java)

```
stream.findFirst();
stream.findAny();
```

### Intermediate Operations

intermediate operations return a stream, so they can be chained
the most common used intermediate operations are:

#### map

`map` is used to transform each element of the stream into another object
[</> StreamMapExample.java](../../src/main/java/streams/StreamMapExample.java)

```
stream.map(Function);
```

#### filter

`filter` is used to select elements as per the Predicate passed as argument
[</> StreamFilterExample.java](../../src/main/java/streams/StreamFilterExample.java)

```
stream.filter(Predicate);
```

#### sorted

`sorted` is used to sort the stream
[</> StreamSortedExample.java](../../src/main/java/streams/StreamSortedExample.java)

```
stream.sorted(); // only for classes that implement Comparable
stream.sorted(Comparator);
```

#### distinct

`distinct` is used to remove duplicate elements based on the equals method from the stream
[</> StreamDistinctExample.java](../../src/main/java/streams/StreamDistinctExample.java)

```
stream.distinct();
```

#### limit

`limit` is used to reduce the size of the stream
the argument passed to limit is the maximum size of the stream
every other element will be removed
[</> StreamLimitExample.java](../../src/main/java/streams/StreamLimitExample.java)

```
stream.limit(int);
```

#### flatMap

`flatMap` is used to transform each element of the stream into another stream and then flatten the stream

it's primarily used to transform a stream of lists into a stream of objects
[</> StreamFlatMapExample.java](../../src/main/java/streams/StreamFlatMapExample.java)

```
stream.flatMap(Function);
```

### Stream parameters

#### Function

a function is a method that takes one argument and produces a result
in streams, it's common to use `lambdas` to pass a function as a parameter

```
(arg) -> { return result; }
```

in streams, you can also use so called `method references`

```
ClassName::methodName
```

#### Consumer

a consumer is a method that takes one argument and returns nothing
it's used in the `forEach` method to iterate over each element of a stream

```
(arg) -> { return; }
```

#### Predicate

a predicate is a method that takes one argument and returns a boolean.
it's used to `filter` elements from a stream

```
(arg) -> { return true; }
(arg) -> { return false; }
```

same as with functions, you can also use `method references` to a predicate method

```
ClassName::methodName
```

#### Comparator

a comparator is a method that takes two arguments and returns an integer.
it's used to `sort` elements from a stream
the integer returned by the comparator is used to determine the order of the elements

- x < 0 -> arg1 < arg2
- x == 0 -> arg1 == arg2
- x > 0 -> arg1 > arg2

```
(arg1, arg2) -> { return 0; }
(arg1, arg2) -> { return x; } // x > 0
(arg1, arg2) -> { return y; } // y < 0
```

same as with functions, you can also use `method references` to a compare method

```
ClassName::methodName
```

#### Collector

a collector is used to collect the elements of a stream into a collection
there are a lot of predefined collectors in the `Collectors` class,
but you can also create your own collectors.

```
Collectors.toList();
Collectors.toSet();
Collectors.toMap();
```
