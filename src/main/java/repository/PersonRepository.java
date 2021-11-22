package repository;

import exceptions.NullValueException;
import model.Course;
import model.Person;

import java.io.IOException;

/**
 * Repository Class that manages all CRUD operations for a Person Entity
 *
 * @param <T> generic PersonRepository
 */
public class PersonRepository<T extends Person> extends InMemoryRepository<T> {

    public PersonRepository() {
        super();
    }

    @Override
    public T findOne(Long id) throws NullValueException {
        if (id == null)
            throw new NullValueException("Invalid ID");
        for (Person person : repoList) {
            if (person.getId() == id)
                return (T) person;
        }
        return null;
    }

    @Override
    public T save(T entity) throws NullValueException, IOException {
        if (entity == null)
            throw new NullValueException("Invalid entity");
        for (Person person : repoList)
            if (person.getId() == entity.getId())
                return entity;
        repoList.add(entity);
        return null;
    }

    @Override
    public T delete(Long id) throws NullValueException, IOException {
        if (id == null)
            throw new NullValueException("Invalid entity");
        for (Person person : repoList)
            if (person.getId() == id) {
                repoList.remove(person);
                return (T) person;
            }
        return null;
    }

    @Override
    public T update(T entity) throws NullValueException, IOException {
        if (entity == null)
            throw new NullValueException("Invalid entity");
        for (Person person : repoList)
            if (person.getId() == entity.getId()) {
                repoList.remove(person);
                repoList.add(entity);
                return null;
            }
        return entity;
    }
}
