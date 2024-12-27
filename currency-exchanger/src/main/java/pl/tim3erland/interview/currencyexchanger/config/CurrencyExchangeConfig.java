package pl.tim3erland.interview.currencyexchanger.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;
import pl.tim3erland.interview.currencyexchanger.service.NbpIntegrationService;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CurrencyExchangeConfig {

    @Bean
    NbpIntegrationService nbpIntegrationService(@Value("${nbp.base.url}") String url,
                                                @Value("${nbp.exchange.url}") String exchangeUrl) {
        return new NbpIntegrationService(exchangeUrl, RestClient.create(url));
    }

    @Bean
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("usdExchange");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                        .expireAfterAccess(1, TimeUnit.MINUTES)
                .weakKeys()
                .recordStats());
        return cacheManager;
    }
}
