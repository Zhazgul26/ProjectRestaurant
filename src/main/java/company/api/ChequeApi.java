package company.api;

import company.dto.requests.ChequeOneDayWaiterTotalAmountRequest;
import company.dto.requests.ChequeRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.cheque.ChequeResponse;
import company.dto.responses.cheque.ChequeWaiterResponse;
import company.service.ChequeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cheque")
public class ChequeApi {
    private final ChequeService chequeService;

    public ChequeApi(ChequeService chequeService) {
        this.chequeService = chequeService;
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @GetMapping
   public List<ChequeResponse> getAllCheque(){
        return chequeService.getAllCheque();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @PostMapping
   public SimpleResponse saveCheque(@RequestBody ChequeRequest request){
        return chequeService.saveCheque(request);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @GetMapping("/{id}")
    public ChequeResponse findByIdCheque(@PathVariable Long id){
        return chequeService.findByIdCheque(id);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}")
    public SimpleResponse updateCheque(@PathVariable Long id,@RequestBody ChequeRequest request){
        return chequeService.updateCheque(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
   public SimpleResponse deleteCheque(@PathVariable Long id){
        return chequeService.deleteCheque(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
   public SimpleResponse totalSumCheque(@RequestBody ChequeOneDayWaiterTotalAmountRequest chequeOneDayWaiterTotalAmountRequest){
        return chequeService.totalSumCheque(chequeOneDayWaiterTotalAmountRequest);

}

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("kq")
    public ChequeWaiterResponse summa( @RequestBody ChequeOneDayWaiterTotalAmountRequest request){
        return chequeService.summa(request);
}

}
