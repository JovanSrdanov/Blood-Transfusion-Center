package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Exceptions.BadRequestException;

import java.util.UUID;

public interface ICrudService<T> {

    Iterable<T> findAll();

    T findById(UUID id);

    T save(T entity) throws BadRequestException;
    
    void deleteById(UUID id);

}
