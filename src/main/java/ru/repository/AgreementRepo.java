package ru.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.models.Agreement;

import java.util.List;
import java.util.Date;

public interface AgreementRepo extends CrudRepository<Agreement, Long> {
    List<Agreement> findAllByNumber(String number);

    List<Agreement> findAllByStatusNot(String status);


    @Modifying
    @Query("INSERT INTO Agreement t (t.productID, t.generalAgreementID, t.supplementaryAgreementID, t.arrangementType, " +
            "t.shedulerJobID, t.number, t.openingDate, t.closingDate, t.cancelDate, t.validityDuration, " +
            "t.cancellationReason, t.status, t.interestCalculationDate, t.interestRate, t.coefficient, " +
            "t.coefficientAction, t.minimumInterestRate, t.minimumInterestRateCoefficient, " +
            "t.minimumInterestRateCoefficientAction, t.maximalInterestRate, t.maximalInterestRateCoefficient, " +
            "t.maximalInterestRateCoefficientAction) " +
            "VALUES(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20, ?21, ?22)")
    void insert(
            Long productID,
            String generalAgreementID,
            String supplementaryAgreementID,
            String arrangementType,
            Long shedulerJobID,
            String number,
            Date openingDate,
            Date closingDate,
            Date cancelDate,
            Long validityDuration,
            String cancellationReason,
            String status,
            Date interestCalculationDate,
            Float interestRate,
            Float coefficient,
            String coefficientAction,
            Float minimumInterestRate,
            Float minimumInterestRateCoefficient,
            String minimumInterestRateCoefficientAction,
            Float maximalInterestRate,
            Float maximalInterestRateCoefficient,
            String maximalInterestRateCoefficientAction
    );
}
