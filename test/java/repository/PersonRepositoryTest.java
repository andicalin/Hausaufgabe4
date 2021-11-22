package repository;

import com.sun.org.glassfish.gmbal.Description;
import exceptions.NullValueException;
import exceptions.NullValueException;
import model.Course;
import model.Student;
import model.Course;
import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.PersonRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryTest {

    PersonRepository<Student> studentRepo = new PersonRepository<Student>();

    List<Long> enrolledCourses1 = new ArrayList<>();
    List<Long> enrolledCourses2 = new ArrayList<>();
    Student student1 = new Student(410, "Stefan", "Gorea", 17, enrolledCourses1);
    Student student2 = new Student(113, "Luca", "Tompea", 28, enrolledCourses1);

    @BeforeEach
    public void setUp() throws NullValueException, IOException {
        studentRepo.save(student1);
    }

    @Test
    @Description("Should return the parameter object because the student has been found")
    void findOne_student_found() throws NullValueException {
        assertEquals(studentRepo.findOne(student1.getId()), student1);
    }

    @Test
    @Description("Should return null because there is no student with the same id in the repository")
    void findOne_student_not_found() throws NullValueException {
        assertNull(studentRepo.findOne(student2.getId()));
    }

    @Test
    @Description("Should throw a NullValueException because the student is null")
    void findOne_student_null() {
        assertThrows(NullValueException.class, () -> studentRepo.findOne(null));
    }

    @Test
    @Description("Should return null because the parameter object has been saved")
    void save_student_not_found() throws NullValueException, IOException {
        assertNull(studentRepo.save(student2));
        assertEquals(studentRepo.size(), 2);
        assertEquals(studentRepo.findOne(student2.getId()), student2);
    }

    @Test
    @Description("Should return the parameter object because the student already exists in the repository")
    void save_student_found() throws NullValueException, IOException {
        assertEquals(studentRepo.save(student1), student1);
    }

    @Test
    @Description("Should throw a NullValueException because the student is null")
    void save_course_null() {
        assertThrows(NullValueException.class, () -> studentRepo.save(null));
    }

    @Test
    @Description("Should return the parameter object because the student has been deleted")
    void delete_student_found() throws NullValueException, IOException {
        assertEquals(studentRepo.delete(student1.getId()), student1);
        assertEquals(studentRepo.size(), 0);
    }

    @Test
    @Description("Should return null because there is no student with the same id in the repository")
    void delete_student_not_found() throws NullValueException, IOException {
        assertEquals(studentRepo.delete(student2.getId()), null);
    }

    @Test
    @Description("Should throw a NullValueException because the student is null")
    void delete_student_null() {
        assertThrows(NullValueException.class, () -> studentRepo.delete(null));
    }

    @Test
    @Description("Should return null because the student has been updated")
    void update_student_found() throws NullValueException, IOException {
        Student newStudent1 = new Student(410, "Stefan", "Blaga", 17, enrolledCourses1);
        assertNull(studentRepo.update(newStudent1));
        assertEquals(studentRepo.findOne(newStudent1.getId()), newStudent1);
    }

    @Test
    @Description("Should return the parameter object because there is no student with the same id in the repository")
    void update_student_not_found() throws NullValueException, IOException {
        assertEquals(studentRepo.update(student2), student2);
    }

    @Test
    @Description("Should throw a NullValueException because the student is null")
    void update_course_null() {
        assertThrows(NullValueException.class, () -> studentRepo.update(null));
    }
}