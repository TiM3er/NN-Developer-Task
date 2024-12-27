package pl.tim3erland.interview.currencyexchanger.database.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDao {

    @Id
    private UUID accountUid;

    private String name;

    private String surname;

    private BigDecimal usdBalance;

    private BigDecimal plnBalance ;
}
