package repository;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import exceptions.NullValueException;
import model.Course;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class CourseFileRepository extends CourseRepository implements IFileRepository<Course> {

    private String fileName;

    public CourseFileRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void readDataFromFile() throws IOException {
        Reader reader = new BufferedReader(new FileReader(fileName));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode node : parser) {
            Course course = new Course();

            course.setId(node.path("id").asLong());
            course.setName(node.path("name").asText());
            course.setTeacherId(node.path("teacherId").asLong());
            course.setMaxEnrollment(node.path("maxEnrollment").asInt());

            JsonNode jsonArray = node.get("studentsEnrolled");
            if (jsonArray.size() > 0)
                course.setStudentsEnrolled(IFileRepository.convertJsonArray(jsonArray));
            else
                course.setStudentsEnrolled(new ArrayList<>());

            course.setCredits(node.path("credits").asInt());

            repoList.add(course);
        }

        reader.close();
    }

    @Override
    public void writeDataToFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(fileName), repoList);
    }

    @Override
    public Course save(Course entity) throws NullValueException, IOException {
        Course course = super.save(entity);
        writeDataToFile();
        return course;
    }

    @Override
    public Course delete(Long id) throws NullValueException, IOException {
        Course course = super.delete(id);
        writeDataToFile();
        return course;
    }

    @Override
    public Course update(Course entity) throws NullValueException, IOException {
        Course course = super.update(entity);
        writeDataToFile();
        return course;
    }
}
