package app.DAOS;

import java.util.Set;

public interface IDAO<T> {
    Set<T> getAll();

    T create(T t);

    T update(T t);

    void delete(T t);
}
