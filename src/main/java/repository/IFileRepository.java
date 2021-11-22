package repository;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * File operations repository interface
 *
 * @param <T> generic class, represents the class of objects that will be stored in the repository
 */
public interface IFileRepository<T> extends ICrudRepository<T> {

    /**
     * reads data from Json file and saves it in repoList
     *
     * @throws IOException if the file is invalid
     */
    void readDataFromFile() throws IOException;

    /**
     * writes repoList in the file
     *
     * @throws IOException if the file is invalid
     */
    void writeDataToFile() throws IOException;

    /**
     * puts the ids from a Json array into an ArrayList
     *
     * @param jsonArray that has to become an ArrayList
     * @return ArrayList with the elements contained in the Json array
     */
    public static List<Long> convertJsonArray(JsonNode jsonArray) {
        List<Long> convertedArray = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++)
            convertedArray.add(jsonArray.get(i).asLong());
        return convertedArray;
    }
}
