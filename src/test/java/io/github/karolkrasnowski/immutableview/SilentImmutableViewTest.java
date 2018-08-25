package io.github.karolkrasnowski.immutableview;

import io.github.karolkrasnowski.immutableview.domain.Address;
import io.github.karolkrasnowski.immutableview.domain.Person;
import io.github.karolkrasnowski.immutableview.domain.Street;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class SilentImmutableViewTest {

    @Test
    void shouldNotThrowExceptionWhenSetterIsInvoked() {
        // given
        Person person = new Person("John", 60);

        // when
        Person immutablePerson = ImmutableView.silent.of(person);

        // then
        assertThatCode(() -> immutablePerson.setName("Steve")).doesNotThrowAnyException();
        assertThatCode(() -> immutablePerson.setAge(80)).doesNotThrowAnyException();
    }

    @Test
    void shouldPreservePreviousValueDespiteSetterInvocation() {
        // given
        Person person = new Person("John", 60);

        // when
        Person immutablePerson = ImmutableView.silent.of(person);
        immutablePerson.setName("Steve");

        // then
        assertThat(immutablePerson.getName()).isEqualTo("John");
    }

    @Test
    void shouldCopyAllDataFromGivenFlatObject() {
        // given
        Person person = new Person("John", 30);

        // when
        Person immutablePerson = ImmutableView.silent.of(person);

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
        Person immutablePerson = ImmutableView.silent.of(person);

        // then
        assertThat(immutablePerson.getName()).isEqualTo("John");
        assertThat(immutablePerson.getAge()).isEqualTo(60);
        assertThat(immutablePerson.getAddress().getCity()).isEqualTo("London");
        assertThat(immutablePerson.getAddress().getStreet().getName()).isEqualTo("Spencer Park");
        assertThat(immutablePerson.getAddress().getStreet().getNumber()).isEqualTo(7);
    }
}
