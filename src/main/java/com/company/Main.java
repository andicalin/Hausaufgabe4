package com.company;

import controller.CourseController;
import controller.StudentController;
import repository.CourseFileRepository;
import repository.StudentFileRepository;
import repository.TeacherFileRepository;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here

        StudentFileRepository studentFileRepo = new StudentFileRepository("students.json");
        CourseFileRepository courseFileRepo = new CourseFileRepository("courses.json");
        TeacherFileRepository teacherFileRepo = new TeacherFileRepository("teachers.json");

        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();

        StudentController studentController = new StudentController(studentFileRepo, courseFileRepo);
        CourseController courseController = new CourseController(courseFileRepo, studentFileRepo, teacherFileRepo);

        View view = new View(studentController, courseController);
        view.runMenu();
    }
}