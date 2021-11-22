package controller;

import com.sun.org.glassfish.gmbal.Description;
import exceptions.InvalidCourseException;
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

class StudentControllerTest {
    StudentFileRepository studentFileRepo = new StudentFileRepository("./test/resources/testStudents.json");
    TeacherFileRepository teacherFileRepo = new TeacherFileRepository("./test/resources/testTeachers.json");
    CourseFileRepository courseFileRepo = new CourseFileRepository("./test/resources/testCourses.json");
    StudentController studentController = new StudentController(studentFileRepo, courseFileRepo);

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
    @Description("Should sort the students descending by the number of total credits")
    void sortStudentsByTotalCredits() throws IOException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        List<Student> sortedStudents = studentController.sortStudentsByTotalCredits();
        assertEquals(sortedStudents.get(0).getId(), 113);
        assertEquals(sortedStudents.get(1).getId(), 1216);
    }

    @Test
    @Description("Should return the students with id 1216 and 113 because they both attend the course with id 1141")
    void filterStudentsAttendingCourse() throws IOException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        List<Student> filteredStudents = studentController.filterStudentsAttendingCourse(1141L);
        assertEquals(filteredStudents.size(), 2);
    }

    @Test
    @Description("Should throw a NullValueException because the parameter course is null")
    void save_student_null() throws IOException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        assertThrows(NullValueException.class, () -> studentController.save(null));
    }

    @Test
    @Description("Should throw an InvalidCourseException because the list with enrolled courses contains an id that doesn't exist in the repository")
    void save_student_invalid_course() throws IOException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        Student student = new Student(918, "Moise", "Gabriel", 53, new ArrayList<>(Arrays.asList(1216L)));
        assertThrows(InvalidCourseException.class, () -> studentController.save(student));
    }

    @Test
    @Description("Should return the student because the id already exists in the repository")
    void save_student_id_exists() throws IOException, InvalidTeacherException, InvalidStudentException, NullValueException, InvalidCourseException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        Student student = new Student(113, "Moise", "Gabriel", 53, new ArrayList<>(Arrays.asList()));
        assertEquals(studentController.save(student), student);
    }

    @Test
    @Description("Should return null because the student has been saved")
    void save_course_not_exists() throws IOException, InvalidTeacherException, InvalidStudentException, NullValueException, InvalidCourseException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        Student student = new Student(930, "Moise", "Gabriel", 53, new ArrayList<>(Arrays.asList(1141L, 653L)));
        assertEquals(studentController.save(student), null);

        int found = 0;
        Course course1 = courseFileRepo.findOne(1141L);
        Course course2 = courseFileRepo.findOne(653L);
        List<Long> studentsCourse1 = course1.getStudentsEnrolled();
        List<Long> studentsCourse2 = course2.getStudentsEnrolled();
        for (Long studentId : studentsCourse1)
            if (studentId == 930)
                found++;
        for (Long studentId : studentsCourse2)
            if (studentId == 930)
                found++;

        assertEquals(found, 2);
    }

    @Test
    @Description("Should throw a NullValueException because the parameter course is null")
    void delete_student_null() throws IOException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        assertThrows(NullValueException.class, () -> studentController.delete(null));
    }

    @Test
    @Description("Should return null because there is no student in the repository with the specified id")
    void delete_student_not_exists() throws IOException, NullValueException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        assertEquals(studentController.delete(1217L), null);
    }

    @Test
    @Description("Should return the student because it has been removed from the repoList")
    void delete_student_exists() throws IOException, InvalidTeacherException, InvalidStudentException, NullValueException {
        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();
        Student student = studentController.findOne(1216L);
        assertEquals(studentController.delete(1216L), student);
    }
}