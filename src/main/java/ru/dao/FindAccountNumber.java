package ru.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.models.*;
import ru.repository.AccountPoolRepo;

import java.util.List;

@Service
public class FindAccountNumber {
    @Autowired
    ApplicationContext applicationContext;

    public Account get(String branchCode, String currencyCode, String mdmCode, String priorityCode, String registryTypeCode) {

        AccountPoolRepo accountPoolRepo = applicationContext.getBean(AccountPoolRepo.class);
        List<AccountPool> ap = accountPoolRepo
                .findAllByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(
                        branchCode, currencyCode, mdmCode, priorityCode, registryTypeCode);
        for (AccountPool oneAP : ap) {
            for (Account oneAcc : oneAP.getAccount()) {
                return oneAcc;
            }
        }
        return null;
    }
}