package repository;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import exceptions.NullValueException;
import model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class StudentFileRepository extends PersonRepository<Student> implements IFileRepository<Student> {

    private String fileName;

    public StudentFileRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void readDataFromFile() throws IOException {
        Reader reader = new BufferedReader(new FileReader(fileName));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode node : parser) {
            Student student = new Student();

            student.setId(node.path("id").asLong());
            student.setFirstName(node.path("firstName").asText());
            student.setLastName(node.path("lastName").asText());
            student.setTotalCredits(node.path("totalCredits").asInt());

            JsonNode jsonArray = node.get("enrolledCourses");
            if (jsonArray.size() > 0)
                student.setEnrolledCourses(IFileRepository.convertJsonArray(jsonArray));
            else
                student.setEnrolledCourses(new ArrayList<>());

            repoList.add(student);
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
    public Student save(Student entity) throws NullValueException, IOException {
        Student student = super.save(entity);
        writeDataToFile();
        return student;
    }

    @Override
    public Student delete(Long id) throws NullValueException, IOException {
        Student student = super.delete(id);
        writeDataToFile();
        return student;
    }

    @Override
    public Student update(Student entity) throws NullValueException, IOException {
        Student student = super.update(entity);
        writeDataToFile();
        return student;
    }
}
