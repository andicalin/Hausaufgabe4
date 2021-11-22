package controller;

import exceptions.InvalidCourseException;
import exceptions.NullValueException;
import model.Course;
import model.Student;
import repository.CourseFileRepository;
import repository.StudentFileRepository;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentController {

    private StudentFileRepository studentFileRepo;
    private CourseFileRepository courseFileRepo;

    public StudentController(StudentFileRepository studentFileRepo, CourseFileRepository courseFileRepo) {
        this.studentFileRepo = studentFileRepo;
        this.courseFileRepo = courseFileRepo;
    }

    /**
     * sorts students descending by the number of total credits
     *
     * @return sorted list of students
     */
    public List<Student> sortStudentsByTotalCredits() {
        return studentFileRepo.findAll().stream()
                // .sorted((student, otherStudent) -> otherStudent.getTotalCredits() - student.getTotalCredits())
                .sorted(Comparator.comparingInt(Student::getTotalCredits).reversed())
                .collect(Collectors.toList());
    }

    /**
     * filters the students who attend the course with the given id
     *
     * @param courseId id of the course
     * @return list of students who attend the course
     */
    public List<Student> filterStudentsAttendingCourse(Long courseId) {
        return studentFileRepo.findAll().stream()
                .filter(student -> student.getEnrolledCourses().contains(courseId))
                // .filter(student -> {
                //    for (Long id : student.getEnrolledCourses()) if (id == courseId) return true;
                //    return false;
                // })
                .collect(Collectors.toList());
    }

    public Student findOne(Long id) throws NullValueException {
        return studentFileRepo.findOne(id);
    }

    public List<Student> findAll() {
        return studentFileRepo.findAll();
    }

    /**
     * saves the parameter object in repoList. Returns the result of method save in PersonRepository<T>
     *
     * @param student to be saved
     * @return result of method save in PersonRepository<T>
     * @throws NullValueException if the parameter object is null
     * @throws IOException if the file is invalid
     * @throws InvalidCourseException student has a course in courseList that doesn't exist in courseRepolist
     */
    public Student save(Student student) throws NullValueException, IOException, InvalidCourseException {
        if (student == null)
            throw new NullValueException("Invalid entity");

        List<Long> enrolledCourses = student.getEnrolledCourses();
        if (enrolledCourses.size() == 0) {
            Student result = studentFileRepo.save(student);
            if (result == null)
                studentFileRepo.writeDataToFile();
            return result;
        }

        List<Course> allCourses = courseFileRepo.findAll();

        for (Long courseId : enrolledCourses)
            if (courseFileRepo.findOne(courseId) == null)
                throw new InvalidCourseException("Invalid course");

        Student result = studentFileRepo.save(student);
        if (result == null) {
            for (Long courseId : enrolledCourses) {
                Course course = courseFileRepo.findOne(courseId);
                course.getStudentsEnrolled().add(student.getId());
            }

            studentFileRepo.writeDataToFile();
            courseFileRepo.writeDataToFile();
        }
        return result;
    }

    /**
     * deletes the object with the parameter id from repoList. Returns the result of method delete in PersonRepository<T>
     *
     * @param id of the object to be deleted
     * @return result of method delete in PersonRepository<T>
     * @throws IOException if the file is invalid
     * @throws NullValueException if the parameter object is null
     */
    public Student delete(Long id) throws IOException, NullValueException {
        if (id == null)
            throw new NullValueException("Invalid entity");
        Student result = studentFileRepo.delete(id);
        if (result == null)
            return result;

        List<Course> allCourses = courseFileRepo.findAll();
        for (Course course : allCourses) {
            List<Long> studentsEnrolled = course.getStudentsEnrolled();
            // if (studentsEnrolled.contains(id))
                studentsEnrolled.remove(id);
        }

        studentFileRepo.writeDataToFile();
        courseFileRepo.writeDataToFile();
        return result;
    }

    public Student update(Student student) throws IOException, NullValueException {
        return studentFileRepo.update(student);
    }

    public int size() {
        return studentFileRepo.size();
    }
}
