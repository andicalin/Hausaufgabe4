package repository;

import java.util.ArrayList;
import java.util.List;

public abstract class InMemoryRepository<T> implements ICrudRepository<T> {

    protected List<T> repoList;

    public InMemoryRepository() {
        repoList = new ArrayList<>();
    }

    @Override
    public int size() {
        return repoList.size();
    }

    @Override
    public List<T> findAll() {
        return repoList;
    }
}
