package ru.repository;

import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.models.TppProduct;

import java.util.Date;
import java.util.List;

public interface TppProductRepo extends CrudRepository<TppProduct, Long> {
    List<TppProduct> findAllByNumber(String number);

    @Modifying
    @Query("INSERT INTO TppProduct t " +
            "(t.productCodeID, t.clientID, t.type, t.number, t.priority, t.dateOfConclusion, t.startDateTime, t.endDateTime, t.days, t.penaltyRate, t.nso, t.thresholdAmount, t.requisiteType, t.interestRateType, t.taxRate, t.reasoneClose, t.state) " +
            "VALUES(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17)")
    void insert(
            Long productCodeID,
            Long clientID,
            String type,
            String number,
            Long priority,
            Date dateOfConclusion,
            Date startDateTime,
            Date endDateTime,
            Long days,
            Float penaltyRate,
            Float nso,
            Float thresholdAmount,
            String requisiteType,
            String interestRateType,
            Float taxRate,
            String reasoneClose,
            String state
    );

}
