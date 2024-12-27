package pl.tim3erland.interview.currencyexchanger.dto.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
    private String no;
    private LocalDate effectiveDate;
    private BigDecimal mid;
}
