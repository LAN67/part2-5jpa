package ru.dao;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.models.*;
import ru.repository.TppProductRegisterRepo;
import ru.repository.TppRefProductRegisterTypeRepo;
import ru.request.RequestAccount;

import java.util.List;

@Service
public class AccountDAO {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    Environment environment;
    @Autowired
    FindAccountNumber findAccountNumber;

    public ResponseEntity<String> check(RequestAccount acc) {

        TppProductRegisterRepo tppProductRegisterRepo = applicationContext.getBean(TppProductRegisterRepo.class);
        long count = tppProductRegisterRepo.countByProductIDAndType(acc.instanceId, acc.registryTypeCode);
        if (count != 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header("Accept-Language:\"ru-ru,ru\"")
                    .body("Параметр registryTypeCode тип регистра " + acc.registryTypeCode + " уже существует для ЭП с ИД  " + acc.instanceId + ".");
        }
        return null;
    }

    @Transactional
    public ResponseEntity<String> create(RequestAccount acc) {
        List<TppRefProductRegisterType> productRegisterType;
        TppProductRegister tppProductRegister;
        Long accountId = null;

        TppRefProductRegisterTypeRepo tppRefProductRegisterTypeRepo = applicationContext.getBean(TppRefProductRegisterTypeRepo.class);
        //Шаг 3. Взять значение из Request.Body.registryTypeCode и найти соответствующие ему
        // записи в tpp_ref_product_register_type.value.
        productRegisterType = tppRefProductRegisterTypeRepo.findAllByValue(acc.registryTypeCode);
        if (productRegisterType.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header("Accept-Language:\"ru-ru,ru\"")
                    .body("Код Продукта '" + acc.registryTypeCode + "' не найдено в Каталоге продуктов " + environment.getProperty("connection_username") + ".tpp_ref_product_register_type для данного типа Регистра.");
        }
        //Шаг 4. Найти значение номера счета по параметрам branchCode, currencyCode, mdmCode, priorityCode, registryTypeCode
        // из Request.Body в таблице Пулов счетов (account_pool). Номер счета берется первый из пула.
        Account newAcc = findAccountNumber.get(acc.branchCode, acc.currencyCode, acc.mdmCode, acc.priorityCode, acc.registryTypeCode);

        tppProductRegister = new TppProductRegister();
        tppProductRegister.setId(null);
        tppProductRegister.setProductID(acc.instanceId);
        String strTemp = null;

        for (TppRefProductRegisterType x : productRegisterType) {
            strTemp = x.getValue();
            break;
        }
        tppProductRegister.setType(strTemp);

        tppProductRegister.setAccount(newAcc.getId());
        accountId = newAcc.getId();

        tppProductRegister.setCurrencyCode(acc.getCurrencyCode());
        tppProductRegister.setState("1");
        tppProductRegister.setAccountNumber(newAcc.getAccountNumber());

        TppProductRegisterRepo tppProductRegisterRepo = applicationContext.getBean(TppProductRegisterRepo.class);
        tppProductRegisterRepo.insert(
                tppProductRegister.getProductID(),
                tppProductRegister.getType(),
                tppProductRegister.getAccount(),
                tppProductRegister.getCurrencyCode(),
                tppProductRegister.getState(),
                tppProductRegister.getAccountNumber()
        );

        if (accountId == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header("Accept-Language:\"ru-ru,ru\"")
                    .body("Счет не найден. branchCode='" + acc.branchCode + "' and currencyCode='" + acc.currencyCode + "' and mdmCode='" + acc.mdmCode + "' and priorityCode='" + acc.priorityCode + "' and registryTypeCode='" + acc.registryTypeCode + "'");
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\n" +
                            "\"data\": {\n" +
                            "\"accountId\": \"" + accountId + "\"\n" +
                            "}\n" +
                            "}\n");
        }
    }
}
