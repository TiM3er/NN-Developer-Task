package pl.tim3erland.interview.currencyexchanger.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.tim3erland.interview.currencyexchanger.dto.integration.ExchangeIntegrationDto;

import java.math.BigDecimal;


public class NbpIntegrationService {
    private RestClient restClient;
    private String exchangeUrl;

    public NbpIntegrationService(String exchangeUrl, RestClient restClient) {
        this.exchangeUrl = exchangeUrl;
        this.restClient = restClient;
    }

    @Cacheable("usdExchange")
    public BigDecimal getPlnUsdExchangeRate() {
        ExchangeIntegrationDto body = restClient.get()
                .uri(exchangeUrl)
                .retrieve()
                .body(ExchangeIntegrationDto.class);
        return body.getRates().getFirst().getMid();
    }

    public BigDecimal getUsdPlnExchangeRate() {
        BigDecimal plnUsdExchangeRate = getPlnUsdExchangeRate();
        return BigDecimal.ONE.divide(plnUsdExchangeRate, 2, BigDecimal.ROUND_HALF_UP);
    }

}
