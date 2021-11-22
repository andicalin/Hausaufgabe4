package controller;

import com.sun.org.glassfish.gmbal.Description;
import exceptions.InvalidStudentException;
import exceptions.InvalidTeacherException;
import exceptions.NullValueException;
import model.Course;
import model.Student;
import model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CourseFileRepository;
import repository.StudentFileRepository;
import repository.TeacherFileRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CourseControllerTest {
    StudentFileRepository studentFileRepo = new StudentFileRepository("./test/resources/testStudents.json");
    TeacherFileRepository teacherFileRepo = new TeacherFileRepository("./test/resources/testTeachers.json");
    CourseFileRepository courseFileRepo = new CourseFileRepository("./test/resources/testCourses.json");
    CourseController courseController = new CourseController(courseFileRepo, studentFileRepo, teacherFileRepo);

    @BeforeEach
    public void setUp() throws IOException, NullValueException {
        studentFileRepo.findAll().clear();
        teacherFileRepo.findAll().clear();
        courseFileRepo.findAll().clear();
        courseFileRepo.save(new Course(1141, "Algebra liniara", 1200, 45, new ArrayList<>(Arrays.asList(1216L)), 18));
        courseFileRepo.save(new Course(653, "Analiza matematica", 1200, 45, new ArrayList<>(Arrays.asList(1216L, 113L)), 11));
        courseFileRepo.save(new Course(807, "Programare distribuita", 120, 88, new ArrayList<>(), 8));
        teacherFileRepo.save(new Teacher(1200, "Mugur", "Acu", new ArrayList<>(Arrays.asList(1141L))));
        studentFileRepo.save(new Student(1216, "Matei", "Stroia", 11, new ArrayList<>(Arrays.asList(1141L))));
        studentFileRepo.save(new Student(113, "Luca", "Tompea", 12, new ArrayList<>(Arrays.asList(1141L, 653L))));
        studentFileRepo.findAll().clear();
        teacherFileRepo.findAll().clear();
        courseFileRepo.findAll().clear();
    }

    @Test
    @Description("Should sort the courses descending by the number of students enrolled")
    void sortCoursesByStudentsEnrolled() throws IOException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        List<Course> sortedCourses = courseController.sortCoursesByStudentsEnrolled();
        assertEquals(sortedCourses.get(0).getId(), 653);
        assertEquals(sortedCourses.get(1).getId(), 1141);
        assertEquals(sortedCourses.get(2).getId(), 807);
    }

    @Test
    @Description("Should return the course with id 807 because it's the only one that has 8 credits")
    void filterCoursesWithSpecifiedCredits() throws IOException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        List<Course> filteredCourses = courseController.filterCoursesWithSpecifiedCredits(8);
        assertEquals(filteredCourses.size(), 1);
        assertEquals(filteredCourses.get(0).getId(), 807);
    }

    @Test
    @Description("Should throw a NullValueException because the parameter course is null")
    void save_course_null() throws IOException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        assertThrows(NullValueException.class, () -> courseController.save(null));
    }

    @Test
    @Description("Should throw an InvalidTeacherException because there is no teacher in the repository with the specified id")
    void save_course_invalid_teacher() throws IOException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        Course course = new Course(2051, "Modelare si simulare", 1118, 53, new ArrayList<>(Arrays.asList(1052L, 1216L)), 11);
        assertThrows(InvalidTeacherException.class, () -> courseController.save(course));
    }

    @Test
    @Description("Should throw an InvalidStudentException because the list with enrolled students contains an id that doesn't exist in the repository")
    void save_course_invalid_student() throws IOException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        Course course = new Course(2051, "Modelare si simulare", 1200, 53, new ArrayList<>(Arrays.asList(1216L, 927L)), 11);
        assertThrows(InvalidStudentException.class, () -> courseController.save(course));
    }

    @Test
    @Description("Should return the course because the id already exists in the repository")
    void save_course_id_exists() throws IOException, InvalidTeacherException, InvalidStudentException, NullValueException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        Course course = new Course(807, "Modelare si simulare", 1200, 53, new ArrayList<>(Arrays.asList(1216L)), 11);
        assertEquals(courseController.save(course), course);
    }

    @Test
    @Description("Should return null because the course has been saved")
    void save_course_not_exists() throws IOException, InvalidTeacherException, InvalidStudentException, NullValueException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        Course newCourse = new Course(152, "Modelare si simulare", 1200, 53, new ArrayList<>(Arrays.asList(113L, 1216L)), 11);
        assertEquals(courseController.save(newCourse), null);

        Teacher teacher = teacherFileRepo.findOne(1200L);
        int found = 0;
        List<Long> courses = teacher.getCourses();
        for (Long courseId : courses) {
            if (courseId == 152)
                found++;
        }

        Student student1 = studentFileRepo.findOne(113L);
        Student student2 = studentFileRepo.findOne(1216L);
        List<Long> coursesStudent1 = student1.getEnrolledCourses();
        List<Long> coursesStudent2 = student2.getEnrolledCourses();
        for (Long courseId : coursesStudent1)
            if (courseId == 152)
                found++;
        for (Long courseId : coursesStudent2)
            if (courseId == 152)
                found++;

        assertEquals(found, 3);
    }

    @Test
    @Description("Should throw a NullValueException because the parameter course is null")
    void delete_course_null() throws IOException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        assertThrows(NullValueException.class, () -> courseController.delete(null));
    }

    @Test
    @Description("Should return null because there is no course in the repository with the specified id")
    void delete_course_not_exists() throws IOException, NullValueException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        assertEquals(courseController.delete(1217L), null);
    }

    @Test
    @Description("Should return the course because it has been removed from the repoList")
    void delete_course_exists() throws IOException, InvalidTeacherException, InvalidStudentException, NullValueException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        Course course = courseController.findOne(1141L);
        assertEquals(courseController.delete(1141L), course);
    }
}