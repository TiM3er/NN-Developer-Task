package pl.tim3erland.interview.currencyexchanger.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tim3erland.interview.currencyexchanger.dto.*;
import pl.tim3erland.interview.currencyexchanger.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountContoller {
    private AccountService accountService;

    public AccountContoller(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping
    public ResponseEntity<RestApiResponse<AccountNumberdto>> createAccoundt(@RequestBody CreateAccountDatadto accountBasicDatadto){
        RestApiResponse<AccountNumberdto> response = accountService.createAccoundt(accountBasicDatadto);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping
    public ResponseEntity<RestApiResponse<AccountFulldatadto>> getAccoundtInfo(@RequestParam(name = "accountUid", required = true) String accountUid){
        RestApiResponse<AccountFulldatadto> response = accountService.getAccoundtInfo(accountUid);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
