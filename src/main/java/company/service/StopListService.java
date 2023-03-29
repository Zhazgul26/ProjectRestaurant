package company.service;

import company.dto.requests.StopListRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.stopList.StopListResponse;

import java.util.List;

public interface StopListService {
    SimpleResponse saveStopList(StopListRequest request);

    List<StopListResponse> getAllStopList();

   StopListResponse findByIdStopList(Long id);
    SimpleResponse updateStopList(Long id,StopListRequest request);
    SimpleResponse deleteStopList(Long id);

}
