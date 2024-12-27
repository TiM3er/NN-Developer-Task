package pl.tim3erland.interview.currencyexchanger.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pl.tim3erland.interview.currencyexchanger.database.dao.AccountDao;
import pl.tim3erland.interview.currencyexchanger.database.repository.AccountRepository;
import pl.tim3erland.interview.currencyexchanger.dto.*;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public RestApiResponse createAccoundt(CreateAccountDatadto accountBasicDatadto) {
        List<RestApiError> errors = createAccoundtValidate(accountBasicDatadto);
        if (!CollectionUtils.isEmpty(errors)) {
            return RestApiResponse.builder()
                    .errors(errors)
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        AccountDao saved = accountRepository.save(mapToDao(accountBasicDatadto));

        return RestApiResponse.builder()
                .status(HttpStatus.OK)
                .body(AccountNumberdto.builder().accountNumber(saved.getAccountUid()).build())
                .build();
    }

    private static AccountDao mapToDao(CreateAccountDatadto accountBasicDatadto) {
        return AccountDao.builder()
                .accountUid(UUID.randomUUID())
                .name(accountBasicDatadto.getName())
                .surname(accountBasicDatadto.getSurname())
                .plnBalance(accountBasicDatadto.getInitialBalance())
                .usdBalance(BigDecimal.ZERO)
                .build();
    }

    private List<RestApiError> createAccoundtValidate(CreateAccountDatadto accountBasicDatadto) {
        List<RestApiError> errors = new ArrayList<>();
        if (!StringUtils.hasText(accountBasicDatadto.getName())) {
            errors.add(RestApiError.builder()
                    .errorCode("01")
                    .errorMessage("ce-createAccoundt-01")
                    .build());
        }
        if (!StringUtils.hasText(accountBasicDatadto.getSurname())) {
            errors.add(RestApiError.builder()
                    .errorCode("02")
                    .errorMessage("ce-createAccoundt-02")
                    .build());
        }
        if (accountBasicDatadto.getInitialBalance() == null) {
            errors.add(RestApiError.builder()
                    .errorCode("03")
                    .errorMessage("ce-createAccoundt-03")
                    .build());
        }else{
            if (accountBasicDatadto.getInitialBalance().compareTo(BigDecimal.ZERO) == 0) {
                errors.add(RestApiError.builder()
                        .errorCode("04")
                        .errorMessage("ce-createAccoundt-04")
                        .build());
            }
        }
        return errors;
    }

    public RestApiResponse getAccoundtInfo(String accountUid) {
        Optional<AccountDao> accountDao = accountRepository.findById(UUID.fromString(accountUid));
        if (accountDao.isEmpty()) {
            return RestApiResponse.builder()
                    .errors(Arrays.asList(RestApiError.builder()
                            .errorCode("01")
                            .errorMessage("ce-getAccoundtInfo-01")
                            .build()))
                    .status(HttpStatus.BAD_REQUEST)
                    .build();

        }
        return mapToDto(accountDao.get());
    }

    private RestApiResponse mapToDto(AccountDao accountDao) {
        return RestApiResponse.builder()
                .status(HttpStatus.OK)
                .body(AccountFulldatadto.builder()
                        .plnBalance(accountDao.getPlnBalance())
                        .usdBalance(accountDao.getUsdBalance())
                        .name(accountDao.getName())
                        .surname(accountDao.getSurname())
                        .build())
                .build();
    }
}
