package pl.tim3erland.interview.currencyexchanger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDatadto extends AccountBasicDatadto {
    private BigDecimal initialBalance;
}
