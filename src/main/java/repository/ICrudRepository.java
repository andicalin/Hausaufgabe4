package repository;

import exceptions.NullValueException;

import java.io.IOException;
import java.util.List;

/**
 * CRUD operations repository interface
 *
 * @param <T> generic class
 */
public interface ICrudRepository<T> {

    /**
     * Searches for the entity with the specified id.
     * Returns the entity with the specified id or null - if there is no entity with the given id. id must not be null
     *
     * @param id -the id of the entity to be searched for
     * @return the entity with the specified id or null
     */
    T findOne(Long id) throws NullValueException;

    /**
     * @return all entities
     */
    List<T> findAll();

    /**
     * Adds an entity to the repository.
     * Returns null- if the given entity is saved otherwise returns the entity (id already exists). entity must be not null
     *
     * @param entity to be saved
     * @return null or the already existing entity
     */
    T save(T entity) throws NullValueException, IOException;

    /**
     * removes the entity with the specified id.
     * Returns the removed entity or null if there is no entity with the given id. id must be not null
     *
     * @param id -the id of the entity to be removed
     * @return the removed entity or null
     */
    T delete(Long id) throws NullValueException, IOException;

    /**
     * Updates the entity in the repository with the entity got as parameter.
     * Returns null - if the entity is updated, otherwise returns the entity - (e.g id does not exist). entity must not be null
     *
     * @param entity to be updated with
     * @return null or the not existing entity
     */
    T update(T entity) throws NullValueException, IOException;

    /**
     * counts the elements from the repoList
     *
     * @return the number of elements
     */
    int size();
}
