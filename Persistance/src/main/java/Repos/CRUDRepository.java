package Repos;

public interface CRUDRepository<ID,T> {
    int size()throws RepoException;
    void save(T entity)throws RepoException;
    void delete(ID id)throws RepoException;
    void update(ID id, T entity)throws RepoException;
    T findOne(ID id)throws RepoException;
    Iterable<T> findAll()throws RepoException;
}
