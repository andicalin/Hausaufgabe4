package controller;

import exceptions.InvalidCourseException;
import exceptions.InvalidStudentException;
import exceptions.InvalidTeacherException;
import exceptions.NullValueException;
import model.Course;
import model.Student;
import model.Teacher;
import repository.CourseFileRepository;
import repository.StudentFileRepository;
import repository.TeacherFileRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CourseController {

    private CourseFileRepository courseFileRepo;
    private StudentFileRepository studentFileRepo;
    private TeacherFileRepository teacherFileRepo;

    public CourseController(CourseFileRepository courseFileRepo, StudentFileRepository studentFileRepo, TeacherFileRepository teacherFileRepo) {
        this.courseFileRepo = courseFileRepo;
        this.studentFileRepo = studentFileRepo;
        this.teacherFileRepo = teacherFileRepo;
    }

    /**
     * sorts courses descending by the number of enrolled students
     *
     * @return sorted list of students
     */
    public List<Course> sortCoursesByStudentsEnrolled() {
        return courseFileRepo.findAll().stream()
                .sorted((course, otherCourse) -> otherCourse.getStudentsEnrolled().size() - course.getStudentsEnrolled().size())
                .collect(Collectors.toList());
    }

    /**
     * filters the courses with the specified number of credits
     *
     * @param credits number of credits
     * @return list of courses
     */
    public List<Course> filterCoursesWithSpecifiedCredits(int credits) {
        return courseFileRepo.findAll().stream()
                .filter(course -> course.getCredits() == credits)
                .collect(Collectors.toList());
    }

    public Course findOne(Long id) throws NullValueException {
        return courseFileRepo.findOne(id);
    }

    public List<Course> findAll() {
        return courseFileRepo.findAll();
    }

    /**
     * saves the parameter object in repoList. Returns the result of method save in CourseRepository
     *
     * @param course to be saved
     * @return result of method save in CourseRepository
     * @throws NullValueException if the parameter object is null
     * @throws IOException if the file is invalid
     * @throws InvalidTeacherException course has a teacher who doesn't exist in teacherRepolist
     * @throws InvalidStudentException course has a student in studentList who doesn't exist in studentRepoList
     */
    public Course save(Course course) throws NullValueException, InvalidTeacherException, IOException, InvalidStudentException {
        if (course == null)
            throw new NullValueException("Invalid entity");

        Long teacherId = course.getTeacherId();
        if (teacherId != null) {
            Teacher teacher = teacherFileRepo.findOne(teacherId);
            if (teacher == null)
                throw new InvalidTeacherException("Invalid teacher");
            else {
                teacher.getCourses().add(course.getId());
            }
        }

        List<Long> studentsEnrolled = course.getStudentsEnrolled();
        if (studentsEnrolled.size() == 0) {
            Course result = courseFileRepo.save(course);
            if (result == null) {
                courseFileRepo.writeDataToFile();
                teacherFileRepo.writeDataToFile();
            }
            return result;
        }

        List<Student> allstudents = studentFileRepo.findAll();

        for (Long studentId : studentsEnrolled)
            if (studentFileRepo.findOne(studentId) == null)
                throw new InvalidStudentException("Invalid student");

        Course result = courseFileRepo.save(course);
        if (result == null) {
            for (Long studentId : studentsEnrolled) {
                Student student = studentFileRepo.findOne(studentId);
                student.getEnrolledCourses().add(course.getId());
            }

            courseFileRepo.writeDataToFile();
            studentFileRepo.writeDataToFile();
            teacherFileRepo.writeDataToFile();
        }
        return result;
    }

    /**
     * deletes the object with the parameter id from repoList. Returns the result of method delete in CourseRepository
     *
     * @param id of the object to be deleted
     * @return result of method delete in CourseRepository
     * @throws IOException if the file is invalid
     * @throws NullValueException if the parameter object is null
     */
    public Course delete(Long id) throws NullValueException, IOException {
        if (id == null)
            throw new NullValueException("Invalid entity");
        Course result = courseFileRepo.delete(id);
        if (result == null)
            return result;

        List<Teacher> allTeachers = teacherFileRepo.findAll();
        for (Teacher teacher : allTeachers) {
            List<Long> courses = teacher.getCourses();
            courses.remove(id);
        }

        List<Student> allStudents = studentFileRepo.findAll();
        for (Student student : allStudents) {
            List<Long> enrolledCourses = student.getEnrolledCourses();
            enrolledCourses.remove(id);
        }

        studentFileRepo.writeDataToFile();
        courseFileRepo.writeDataToFile();
        teacherFileRepo.writeDataToFile();
        return result;
    }

    public Course update(Course course) throws IOException, NullValueException {
        return courseFileRepo.update(course);
    }

    public int size() {
        return courseFileRepo.size();
    }
}
