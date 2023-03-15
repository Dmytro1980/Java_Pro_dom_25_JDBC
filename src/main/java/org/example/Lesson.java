package org.example;

public class Lesson {
    private Integer id;
    private String name;

    private Integer homework_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHomework_id() {
        return homework_id;
    }

    public void setHomework_id(Integer homework_id) {
        this.homework_id = homework_id;
    }

    @Override
    public String toString() {
        if (this.id != null) {
            return id + " " + name + " " + homework_id;
        }
        return "object \"Lesson\" is empty";
    }
}
