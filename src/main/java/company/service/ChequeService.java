package company.service;

import company.dto.requests.ChequeOneDayWaiterTotalAmountRequest;
import company.dto.requests.ChequeRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.cheque.ChequeResponse;
import company.dto.responses.cheque.ChequeWaiterResponse;


import java.util.List;

public interface ChequeService {
    List<ChequeResponse> getAllCheque();

    SimpleResponse saveCheque(ChequeRequest request);

    ChequeResponse findByIdCheque(Long id);

    SimpleResponse updateCheque(Long id,ChequeRequest request);
    SimpleResponse deleteCheque(Long id);

    SimpleResponse totalSumCheque(ChequeOneDayWaiterTotalAmountRequest chequeOneDayWaiterTotalAmountRequest);

    ChequeWaiterResponse summa(ChequeOneDayWaiterTotalAmountRequest request);
}
