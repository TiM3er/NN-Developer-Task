package pl.tim3erland.interview.currencyexchanger.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tim3erland.interview.currencyexchanger.dto.AccountFulldatadto;
import pl.tim3erland.interview.currencyexchanger.dto.AccountNumberdto;
import pl.tim3erland.interview.currencyexchanger.dto.RestApiResponse;
import pl.tim3erland.interview.currencyexchanger.service.ExchangeService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/exchange")
public class ExchangeContoller {
    private ExchangeService exchangeService;

    public ExchangeContoller(ExchangeService exchangeService) {

        this.exchangeService = exchangeService;
    }

    @PostMapping("/usd")
    public ResponseEntity<RestApiResponse> exchangeToUsd(@RequestParam(name = "accountUid", required = true) UUID accountUid, @RequestParam(name = "ammount", required = true) BigDecimal ammount) {
        RestApiResponse response = exchangeService.exchangeToUsd(accountUid, ammount);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/pln")
    public ResponseEntity<RestApiResponse> exchangeToPln(@RequestParam(name = "accountUid", required = true) UUID accountUid, @RequestParam(name = "ammount", required = true) BigDecimal ammount) {
        RestApiResponse response = exchangeService.exchangeToPln(accountUid, ammount);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
