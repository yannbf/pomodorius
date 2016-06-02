package braga.com.br.pomodorius.dao;

import java.util.List;

/**
 * Created by yann.braga on 24/05/2016.
 */
public interface IDatabase<T> {
    void insert(T obj);

    void update(T obj);

    void delete(T obj);

    T find(Integer id);

    List<T> findAll();
}
