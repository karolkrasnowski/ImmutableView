package io.github.karolkrasnowski.immutableview.domain;

public class Cat {

    private String name;

    private Cat() {}

    public Cat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
