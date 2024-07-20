package ru.repository;

import org.springframework.data.repository.CrudRepository;
import ru.models.Agreement;
import ru.models.TppRefProductClass;

import java.util.List;

public interface TppRefProductClassRepo  extends CrudRepository<TppRefProductClass, Long> {
    List<TppRefProductClass> findAllByValue(String value);


}
