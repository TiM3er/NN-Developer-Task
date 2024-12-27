package pl.tim3erland.interview.currencyexchanger.dto.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeIntegrationDto {
    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;
}
