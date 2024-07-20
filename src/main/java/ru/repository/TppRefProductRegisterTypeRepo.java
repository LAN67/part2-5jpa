package ru.repository;

import org.springframework.data.repository.CrudRepository;
import ru.models.TppProductRegister;
import ru.models.TppRefProductRegisterType;

import java.util.List;

public interface TppRefProductRegisterTypeRepo extends CrudRepository<TppRefProductRegisterType, Long> {
    List<TppRefProductRegisterType> findAllByValue(String value);
}
