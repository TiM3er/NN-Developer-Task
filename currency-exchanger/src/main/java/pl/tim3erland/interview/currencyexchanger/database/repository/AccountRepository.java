package pl.tim3erland.interview.currencyexchanger.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tim3erland.interview.currencyexchanger.database.dao.AccountDao;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountDao, UUID> {
}
