package ru.repository;

import org.springframework.data.repository.CrudRepository;
import ru.models.AccountPool;

import java.util.List;

public interface AccountPoolRepo extends CrudRepository<AccountPool, Long> {
    List<AccountPool> findAllByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(String branchCode, String currencyCode, String mdmCode, String priorityCode, String registryTypeCode);
}
