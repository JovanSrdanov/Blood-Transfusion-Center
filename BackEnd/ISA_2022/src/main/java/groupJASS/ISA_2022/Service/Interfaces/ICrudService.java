package groupJASS.ISA_2022.Service.Interfaces;

import java.util.UUID;

public interface ICrudService<T> {

    Iterable<T> findAll();

    T findById(UUID id);

    T save(T entity);
    
    void deleteById(UUID id);

}
