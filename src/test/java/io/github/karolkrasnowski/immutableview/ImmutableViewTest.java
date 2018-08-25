package io.github.karolkrasnowski.immutableview;

import io.github.karolkrasnowski.immutableview.domain.Address;
import io.github.karolkrasnowski.immutableview.domain.Cat;
import io.github.karolkrasnowski.immutableview.domain.Person;
import io.github.karolkrasnowski.immutableview.domain.Street;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ImmutableViewTest {

    @Test
    public void shouldCreateImmutableCopyOfGivenFlatObject() {
        // given
        Person person = new Person("John", 60);

        // when
        Person immutablePerson = ImmutableView.of(person);

        // then
        assertThatThrownBy(() -> immutablePerson.setName("Steve"))
                .isInstanceOf(ImmutableObjectModificationException.class);
        assertThatThrownBy(() -> immutablePerson.setAge(45))
                .isInstanceOf(ImmutableObjectModificationException.class);
    }

    @Test
    public void shouldCreateImmutableCopyOfGivenNestedObject() {
        // given
        Person person = new Person("John", 60, new Address(new Street("Spencer Park", 7), "London"));

        // when
        Person immutablePerson = ImmutableView.of(person);

        // then
        assertThatThrownBy(() -> immutablePerson.getAddress().setCity("New York"))
                .isInstanceOf(ImmutableObjectModificationException.class);
        assertThatThrownBy(() -> immutablePerson.getAddress().getStreet().setName("Trefoli Rd"))
                .isInstanceOf(ImmutableObjectModificationException.class);
    }

    @Test
    void shouldCopyAllDataFromGivenFlatObject() {
        // given
        Person person = new Person("John", 30);

        // when
        Person immutablePerson = ImmutableView.of(person);

        // then
        assertThat(immutablePerson.getName()).isEqualTo("John");
        assertThat(immutablePerson.getAge()).isEqualTo(30);
        assertThat(immutablePerson.getAddress()).isEqualTo(null);
    }

    @Test
    void shouldCopyAllDataFromGivenNestedObject() {
        // given
        Person person = new Person("John", 60, new Address(new Street("Spencer Park", 7), "London"));

        // when
        Person immutablePerson = ImmutableView.of(person);

        // then
        assertThat(immutablePerson.getName()).isEqualTo("John");
        assertThat(immutablePerson.getAge()).isEqualTo(60);
        assertThat(immutablePerson.getAddress().getCity()).isEqualTo("London");
        assertThat(immutablePerson.getAddress().getStreet().getName()).isEqualTo("Spencer Park");
        assertThat(immutablePerson.getAddress().getStreet().getNumber()).isEqualTo(7);
    }

    @Test
    void shouldCreateImmutableCopyOfClassWithPrivateDefaultConstructor() {
        // given
        Cat cat = new Cat("Garfield");

        // when
        Cat immutableCat = ImmutableView.of(cat);

        // then
        assertThatThrownBy(() -> immutableCat.setName("Leo")).isInstanceOf(ImmutableObjectModificationException.class);
    }
}