package pl.tim3erland.interview.currencyexchanger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccountFulldatadto extends RestApiBody {
    private String name;
    private String surname;
    private BigDecimal usdBalance;
    private BigDecimal plnBalance;

}
