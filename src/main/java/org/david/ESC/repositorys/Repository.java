package org.david.ESC.repositorys;

import java.util.List;

public interface Repository<T> {

    List<T> enlistar();

    T byid(long id);

    void persistence(T t);

    void delete(long id);
}
