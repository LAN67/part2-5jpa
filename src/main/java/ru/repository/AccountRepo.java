package ru.repository;

import org.springframework.data.repository.CrudRepository;
import ru.models.Account;
import ru.models.AccountPool;

public interface AccountRepo extends CrudRepository<Account, Long> {
}
