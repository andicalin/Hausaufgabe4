package model;

import java.util.List;
import java.util.Objects;

public class Teacher extends Person {

    private List<Long> courses;

    public Teacher(long id, String firstName, String lastName, List<Long> courses) {
        super(id, firstName, lastName);
        this.courses = courses;
    }

    public Teacher() {
    }

    public List<Long> getCourses() {
        return courses;
    }

    public void setCourses(List<Long> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                super.toString() +
                ", courses=" + courses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(courses, teacher.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), courses);
    }
}
