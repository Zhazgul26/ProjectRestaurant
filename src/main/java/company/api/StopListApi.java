package company.api;

import company.dto.requests.StopListRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.stopList.StopListResponse;
import company.service.StopListService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stopList")
public class StopListApi {
    private final StopListService stopListService;

    public StopListApi(StopListService stopListService) {
        this.stopListService = stopListService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping
public List<StopListResponse> getAllStopList(){
        return stopListService.getAllStopList();
}

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
@PostMapping
    public SimpleResponse saveStopList(@RequestBody  StopListRequest request){
       return stopListService.saveStopList(request);

   }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    StopListResponse findByIdStopList(@PathVariable Long id){
        return stopListService.findByIdStopList(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
   @PutMapping("/{id}")
    public SimpleResponse updateStopList(@PathVariable Long id,@RequestBody StopListRequest request){
        return stopListService.updateStopList(id, request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{id}")
    public  SimpleResponse deleteStopList(@PathVariable Long id){
        return stopListService.deleteStopList(id);
}
}
