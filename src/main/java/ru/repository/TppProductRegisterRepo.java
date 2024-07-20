package ru.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.models.TppProductRegister;

import java.util.List;

public interface TppProductRegisterRepo extends CrudRepository<TppProductRegister, Long> {
    List<TppProductRegister> findAllByProductIDAndType(Long productID, String type);
    List<TppProductRegister> findAllByState(String state);

    long countByProductIDAndType(Long productID, String type);

    @Modifying
    @Query("INSERT INTO TppProductRegister t " +
            "(t.productID, t.type, t.account, t.currencyCode, t.state, t.accountNumber) " +
            "VALUES(?1, ?2, ?3, ?4, ?5, ?6)")
    void insert(
            Long productID,
            String type,
            Long account,
            String currencyCode,
            String state,
            String accountNumber
    );
}
