package com.company;

import controller.CourseController;
import controller.StudentController;
import exceptions.*;
import model.Course;
import model.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View {

    private StudentController studentController;
    private CourseController courseController;

    public View(StudentController studentController, CourseController courseController) {
        this.studentController = studentController;
        this.courseController = courseController;
    }

    public void sortStudentsByTotalCredits() {
        List<Student> students = studentController.sortStudentsByTotalCredits();
        for (Student student : students)
            System.out.println(student);
    }

    public void filterStudentsAttendingCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course id for the filter: ");
        Long courseId = scanner.nextLong();

        List<Student> students = studentController.filterStudentsAttendingCourse(courseId);
        for (Student student : students)
            System.out.println(student);
    }

    public void findOne() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id: ");
        Long studentId = scanner.nextLong();

        try {
            studentController.findOne(studentId);
        } catch (NullValueException e) {
            System.out.println(e);
        }
    }

    public void findAll() {
        List<Student> students = studentController.findAll();
        for (Student student : students)
            System.out.println(student);
    }

    public void save() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter total credits: ");
        int totalCredits = scanner.nextInt();
        System.out.println("Enter the size of the list of enrolled courses: ");
        int size = scanner.nextInt();
        List<Long> courseList = new ArrayList<>();

        if (size != 0) {
            System.out.println("Enter courses: ");

            Long courseId;
            for (int i = 0; i < size; i++) {
                courseId = scanner.nextLong();
                courseList.add(courseId);
            }
        }

        Student newStudent = new Student(id, firstName, lastName, totalCredits, courseList);
        try {
            studentController.save(newStudent);
        } catch (NullValueException | IOException | InvalidCourseException e) {
            System.out.println(e);
        }
    }

    public void delete() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id: ");
        Long id = scanner.nextLong();

        try {
            studentController.delete(id);
        } catch (NullValueException | IOException e) {
            System.out.println(e);
        }
    }

    public void update() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter total credits: ");
        int totalCredits = scanner.nextInt();
        System.out.println("Enter the size of the list of enrolled courses: ");
        int size = scanner.nextInt();
        List<Long> courseList = new ArrayList<>();

        if (size != 0) {
            System.out.println("Enter courses: ");

            Long courseId;
            for (int i = 0; i < size; i++) {
                courseId = scanner.nextLong();
                courseList.add(courseId);
            }
        }

        Student newStudent = new Student(id, firstName, lastName, totalCredits, courseList);
        try {
            studentController.update(newStudent);
        } catch (NullValueException | IOException e) {
            System.out.println(e);
        }
    }

    public void size() {
        System.out.println(studentController.size());
    }

    public void printStudentMenu() {
        System.out.println("1. Sort students by number of total credits");
        System.out.println("2. Filter students attending course");
        System.out.println("3. Find student by id");
        System.out.println("4. Find all students");
        System.out.println("5. Save student");
        System.out.println("6. Delete student");
        System.out.println("7. Update student");
        System.out.println("8. Print the size of the student list");
        System.out.println("9. Exit");
    }

    public void runStudentMenu() {
        boolean done = false;
        while (!done)
            try {
                printStudentMenu();

                Scanner scanner = new Scanner(System.in);
                System.out.println("Choose option: ");
                int option = scanner.nextInt();

                if (option < 1 || option > 9)
                    throw new InvalidMenuOptionException("Invalid value");
                if (option == 9)
                    done = true;
                if (option == 1)
                    sortStudentsByTotalCredits();
                if (option == 2)
                    filterStudentsAttendingCourse();
                if (option == 3)
                    findOne();
                if (option == 4)
                    findAll();
                if (option == 5)
                    save();
                if (option == 6)
                    delete();
                if (option == 7)
                    update();
                if (option == 8)
                    size();
            } catch (InvalidMenuOptionException e) {
                System.out.println(e);
            }
    }

    public void sortCoursesByStudentsEnrolled() {
        List<Course> courses = courseController.sortCoursesByStudentsEnrolled();
        for (Course course : courses)
            System.out.println(course);
    }

    public void filterCoursesWithSpecifiedCredits() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of credits for the filter: ");
        int numberCredits = scanner.nextInt();

        List<Course> courses = courseController.filterCoursesWithSpecifiedCredits(numberCredits);
        for (Course course : courses)
            System.out.println(course);
    }

    public void courseFindOne() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course id: ");
        Long courseId = scanner.nextLong();

        try {
            studentController.findOne(courseId);
        } catch (NullValueException e) {
            System.out.println(e);
        }
    }

    public void courseFindAll() {
        List<Course> courses = courseController.findAll();
        for (Course course : courses)
            System.out.println(course);
    }

    public void courseSave() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course id: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter teacher id: ");
        Long teacherId = scanner.nextLong();
        System.out.println("Enter maximum enrollment: ");
        int maxEnrollment = scanner.nextInt();
        System.out.println("Enter the size of the list of enrolled students: ");
        int size = scanner.nextInt();
        List<Long> studentList = new ArrayList<>();

        if (size != 0) {
            System.out.println("Enter students: ");

            Long studentId;

            for (int i = 0; i < size; i++) {
                studentId = scanner.nextLong();
                studentList.add(studentId);
            }
        }

        System.out.println("Enter number of credits: ");
        int credits = scanner.nextInt();

        Course newCourse = new Course(id, name, teacherId, maxEnrollment, studentList, credits);
        try {
            courseController.save(newCourse);
        } catch (NullValueException | IOException | InvalidStudentException | InvalidTeacherException e) {
            System.out.println(e);
        }
    }

    public void courseDelete() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course id: ");
        Long id = scanner.nextLong();

        try {
            courseController.delete(id);
        } catch (NullValueException | IOException e) {
            System.out.println(e);
        }
    }

    public void courseUpdate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter teacher id: ");
        Long teacherId = scanner.nextLong();
        System.out.println("Enter maximum enrollment: ");
        int maxEnrollment = scanner.nextInt();
        System.out.println("Enter the size of the list of enrolled students: ");
        int size = scanner.nextInt();
        List<Long> studentList = new ArrayList<>();

        if (size != 0) {
            System.out.println("Enter students: ");

            Long studentId;

            for (int i = 0; i < size; i++) {
                studentId = scanner.nextLong();
                studentList.add(studentId);
            }
        }

        System.out.println("Enter number of credits: ");
        int credits = scanner.nextInt();

        Course newCourse = new Course(id, name, teacherId, maxEnrollment, studentList, credits);
        try {
            courseController.update(newCourse);
        } catch (NullValueException | IOException e) {
            System.out.println(e);
        }
    }

    public void courseSize() {
        System.out.println(courseController.size());
    }

    public void printCourseMenu() {
        System.out.println("1. Sort courses by number of students enrolled");
        System.out.println("2. Filter courses with specified number of credits");
        System.out.println("3. Find course by id");
        System.out.println("4. Find all courses");
        System.out.println("5. Save course");
        System.out.println("6. Delete course");
        System.out.println("7. Update course");
        System.out.println("8. Print the size of the course list");
        System.out.println("9. Exit");
    }

    public void runCourseMenu() {
        boolean done = false;
        while (!done)
            try {
                printCourseMenu();

                Scanner scanner = new Scanner(System.in);
                System.out.println("Choose option: ");
                int option = scanner.nextInt();

                if (option < 1 || option > 9)
                    throw new InvalidMenuOptionException("Invalid value");
                if (option == 9)
                    done = true;
                if (option == 1)
                    sortCoursesByStudentsEnrolled();
                if (option == 2)
                    filterCoursesWithSpecifiedCredits();
                if (option == 3)
                    courseFindOne();
                if (option == 4)
                    courseFindAll();
                if (option == 5)
                    courseSave();
                if (option == 6)
                    courseDelete();
                if (option == 7)
                    courseUpdate();
                if (option == 8)
                    courseSize();
            } catch (InvalidMenuOptionException e) {
                System.out.println(e);
            }
    }


    void runMenu() {
        System.out.println("Menu:");
        System.out.println('\t' + "1. Student Menu");
        System.out.println('\t' + "2. Course Menu");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose option 1 or 2: ");
        int option = scanner.nextInt();

        if (option == 1)
            runStudentMenu();
        else
            runCourseMenu();
    }
}
