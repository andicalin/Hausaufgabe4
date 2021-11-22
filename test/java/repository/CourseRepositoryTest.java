package repository;

import com.sun.org.glassfish.gmbal.Description;
import exceptions.NullValueException;
import model.Course;
import model.Student;
import model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CourseRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseRepositoryTest {

    CourseRepository courseRepo = new CourseRepository();

    List<Long> listCourses1 = new ArrayList<>();
    List<Long> listStudents1 = new ArrayList<>();
    Teacher teacher1 = new Teacher(52, "Mugur", "Acu", listCourses1);
    Teacher teacher2 = new Teacher(11, "Mugur", "Acu", listCourses1);
    Course course1 = new Course(653, "Analiza matematica", teacher1.getId(), 48, listStudents1, 6);
    Course course2 = new Course(1226, "Algoritmi fundamentali", teacher2.getId(), 27, listStudents1, 3);

    @BeforeEach
    public void setUp() throws NullValueException, IOException {
        courseRepo.save(course1);
    }

    @Test
    @Description("Should return the parameter object because the course has been found")
    void findOne_course_found() throws NullValueException {
        assertEquals(courseRepo.findOne(course1.getId()), course1);
    }

    @Test
    @Description("Should return null because there is no course with the same id in the repository")
    void findOne_course_not_found() throws NullValueException {
        assertNull(courseRepo.findOne(course2.getId()));
    }

    @Test
    @Description("Should throw a NullValueException because the course is null")
    void findOne_course_null() {
        assertThrows(NullValueException.class, () -> courseRepo.findOne(null));
    }

    @Test
    @Description("Should return null because the parameter object has been saved")
    void save_course_not_found() throws NullValueException, IOException {
        assertNull(courseRepo.save(course2));
        assertEquals(courseRepo.size(), 2);
        assertEquals(courseRepo.findOne(course2.getId()), course2);
    }

    @Test
    @Description("Should return the parameter object because the course already exists in the repository")
    void save_course_found() throws NullValueException, IOException {
        assertEquals(courseRepo.save(course1), course1);
    }

    @Test
    @Description("Should throw a NullValueException because the course is null")
    void save_course_null() {
        assertThrows(NullValueException.class, () -> courseRepo.save(null));
    }

    @Test
    @Description("Should return the parameter object because the course has been deleted")
    void delete_course_found() throws NullValueException, IOException {
        assertEquals(courseRepo.delete(course1.getId()), course1);
        assertEquals(courseRepo.size(), 0);
    }

    @Test
    @Description("Should return null because there is no course with the same id in the repository")
    void delete_course_not_found() throws NullValueException, IOException {
        assertEquals(courseRepo.delete(course2.getId()), null);
    }

    @Test
    @Description("Should throw a NullValueException because the course is null")
    void delete_course_null() {
        assertThrows(NullValueException.class, () -> courseRepo.delete(null));
    }

    @Test
    @Description("Should return null because the course has been updated")
    void update_course_found() throws NullValueException, IOException {
        Course newCourse1 = new Course(653, "Analiza matematica", teacher1.getId(), 48, listStudents1, 6);
        assertNull(courseRepo.update(newCourse1));
        assertEquals(courseRepo.findOne(newCourse1.getId()), newCourse1);
    }

    @Test
    @Description("Should return the parameter object because there is no course with the same id in the repository")
    void update_course_not_found() throws NullValueException, IOException {
        assertEquals(courseRepo.update(course2), course2);
    }

    @Test
    @Description("Should throw a NullValueException because the course is null")
    void update_course_null() {
        assertThrows(NullValueException.class, () -> courseRepo.update(null));
    }
}