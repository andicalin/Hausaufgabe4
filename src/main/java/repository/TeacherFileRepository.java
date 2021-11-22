package repository;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import exceptions.NullValueException;
import model.Teacher;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class TeacherFileRepository extends PersonRepository<Teacher> implements IFileRepository<Teacher> {

    private String fileName;

    public TeacherFileRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void readDataFromFile() throws IOException {
        Reader reader = new BufferedReader(new FileReader(fileName));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode node : parser) {
            Teacher teacher = new Teacher();

            teacher.setId(node.path("id").asLong());
            teacher.setFirstName(node.path("firstName").asText());
            teacher.setLastName(node.path("lastName").asText());

            JsonNode jsonArray = node.get("courses");
            if (jsonArray.size() > 0)
                teacher.setCourses(IFileRepository.convertJsonArray(jsonArray));
            else
                teacher.setCourses(new ArrayList<>());

            repoList.add(teacher);
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
    public Teacher save(Teacher entity) throws NullValueException, IOException {
        Teacher teacher = super.save(entity);
        writeDataToFile();
        return teacher;
    }

    @Override
    public Teacher delete(Long id) throws NullValueException, IOException {
        Teacher teacher = super.delete(id);
        writeDataToFile();
        return teacher;
    }

    @Override
    public Teacher update(Teacher entity) throws NullValueException, IOException {
        Teacher teacher = super.update(entity);
        writeDataToFile();
        return teacher;
    }
}
