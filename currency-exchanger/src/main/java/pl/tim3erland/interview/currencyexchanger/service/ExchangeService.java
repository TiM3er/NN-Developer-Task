package pl.tim3erland.interview.currencyexchanger.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.tim3erland.interview.currencyexchanger.database.dao.AccountDao;
import pl.tim3erland.interview.currencyexchanger.database.repository.AccountRepository;
import pl.tim3erland.interview.currencyexchanger.dto.RestApiError;
import pl.tim3erland.interview.currencyexchanger.dto.RestApiResponse;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExchangeService {

    private AccountRepository accountRepository;
    private NbpIntegrationService nbpIntegrationService;

    public ExchangeService(AccountRepository accountRepository, NbpIntegrationService nbpIntegrationService) {
        this.accountRepository = accountRepository;
        this.nbpIntegrationService = nbpIntegrationService;
    }

    public RestApiResponse exchangeToPln(UUID accountUid, BigDecimal ammount) {
        Optional<AccountDao> accountDao = accountRepository.findById(accountUid);
        if (accountDao.isEmpty()) {
            return RestApiResponse.builder()
                    .errors(Arrays.asList(RestApiError.builder()
                            .errorCode("01")
                            .errorMessage("ce-exchangeToPln-01")
                            .build()))
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        if (accountDao.get().getUsdBalance().compareTo(ammount) < 0) {
            return RestApiResponse.builder()
                    .errors(Arrays.asList(RestApiError.builder()
                            .errorCode("02")
                            .errorMessage("ce-exchangeToPln-02")
                            .build()))
                    .status(HttpStatus.BAD_REQUEST)
                    .build();

        }

        BigDecimal usdBalanceTmp = accountDao.get().getUsdBalance();
        BigDecimal plnBalanceTmp = accountDao.get().getPlnBalance();
        BigDecimal exchangeRate = nbpIntegrationService.getPlnUsdExchangeRate();
        accountDao.get().setUsdBalance(usdBalanceTmp.subtract(ammount));
        accountDao.get().setPlnBalance(plnBalanceTmp.add(exchangeRate.multiply(ammount)));

        accountRepository.save(accountDao.get());

        return RestApiResponse.builder()
                .status(HttpStatus.OK)
                .body(null)
                .build();
    }

    public RestApiResponse exchangeToUsd(UUID accountUid, BigDecimal ammount) {
        Optional<AccountDao> accountDao = accountRepository.findById(accountUid);
        if (accountDao.isEmpty()) {
            return RestApiResponse.builder()
                    .errors(Arrays.asList(RestApiError.builder()
                            .errorCode("01")
                            .errorMessage("ce-exchangeToUsd-01")
                            .build()))
                    .status(HttpStatus.BAD_REQUEST)
                    .build();

        }
        if (accountDao.get().getPlnBalance().compareTo(ammount) < 0) {
            return RestApiResponse.builder()
                    .errors(Arrays.asList(RestApiError.builder()
                            .errorCode("02")
                            .errorMessage("ce-exchangeToUsd-02")
                            .build()))
                    .status(HttpStatus.BAD_REQUEST)
                    .build();

        }

        BigDecimal usdBalanceTmp = accountDao.get().getUsdBalance();
        BigDecimal plnBalanceTmp = accountDao.get().getPlnBalance();
        BigDecimal exchangeRate = nbpIntegrationService.getUsdPlnExchangeRate();
        accountDao.get().setPlnBalance(plnBalanceTmp.subtract(ammount));
        accountDao.get().setUsdBalance(usdBalanceTmp.add(exchangeRate.multiply(ammount)));

        accountRepository.save(accountDao.get());


        return RestApiResponse.builder()
                .status(HttpStatus.OK)
                .body(null)
                .build();
    }
}
