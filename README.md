# ImmutableView lets you create an immutable version of your data structures at runtime

## Why should I  bother?

Immutable objects have a lot of benefits, some of them are listed below:
* they are easier to test
* they are thread-safe
* their usage is side-effect free (no defensive copies)
* there is no identity mutability problem
* they are easier to cache

You can read more about it in [this article](https://www.yegor256.com/2014/06/09/objects-should-be-immutable.html).

## Use case
Let's assume you are working with external code you cannot modify. Data structures in this code are mutable.
In order to reduce the risk related to mutability you want to rewrite these data structures into your own immutable versions.
It looks like a really tedious task, so perhaps there is another option? Indeed,
you can make use of **ImmutableView** and enjoy all the benefits of immutability.
It internally uses bytecode manipulation to intercept method invocations and add desired behaviour.

## Usage
Let's assume you have an object `person` of class `Person` which looks like this:

```java
class Person {

    private String name;
    private int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    int getAge() {
        return age;
    }

    void setAge(int age) {
        this.age = age;
    }
}
```
Let's initialize our mutable object:
```java
Person person = new Person("John", 30);
```

In order to make an immutable version of this object just type:
```java
Person immutablePerson = ImmutableView.of(person);
```
Now any mutator invocation will throw `ImmutableObjectModificationException`.
```java
immutablePerson.setAge(43); // throws exception
```
> **ImmutableView** assumes all `void` methods mutate the object state.

If instead of throwing an exception you just want to *silently* ignore the fact of object modification you can use:
```java
Person immutablePerson = ImmutableView.silent.of(person);
```
Now any mutator invocation will be *silently* ignored.
```java
immutablePerson.setAge(43);
immutablePerson.getAge(); // returns 30
```
**ImmutableView** works also for nested structures.
To illustrate this let's add to the `Person` class field of type `Address`. `Address` class has in turn field of type `Street`.
```java
class Address {

    private Street street;
    private String city;

    Address(Street street, String city) {
        this.street = street;
        this.city = city;
    }

    Street getStreet() {
        return street;
    }

    void setStreet(Street street) {
        this.street = street;
    }

    String getCity() {
        return city;
    }

    void setCity(String city) {
        this.city = city;
    }
}

class Street {

    private String name;
    private int number;

    Street(String name, int number) {
        this.name = name;
        this.number = number;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    int getNumber() {
        return number;
    }

    void setNumber(int number) {
        this.number = number;
    }
}
```
Well, let's create our `person` object:
```java
Person person = new Person("John", 30, new Address(new Street("Spencer Park", 7), "London"));
```
and make it immutable:
```java
Person immutablePerson = ImmutableView.of(person);
```
All nested properties became recursively immutable:
```java
immutablePerson.getAddress().getStreet().setName("Trefoli Rd") // throws exception
```