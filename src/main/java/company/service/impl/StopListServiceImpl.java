package company.service.impl;

import company.dto.requests.StopListRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.stopList.StopListResponse;

import company.entity.MenuItem;
import company.entity.StopList;
import company.exeption.NotFoundException;
import company.repository.MenuItemRepository;
import company.repository.StopListRepository;
import company.service.StopListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;
@Autowired
    public StopListServiceImpl(StopListRepository stopListRepository, MenuItemRepository menuItemRepository) {
        this.stopListRepository = stopListRepository;
        this.menuItemRepository = menuItemRepository;
    }


    @Override
    public SimpleResponse saveStopList(StopListRequest request) {

    if (stopListRepository.count(request.date(), request.menuItemId())> 0){
        throw new NoSuchElementException("This dish is already on the menu today.");
    }

    MenuItem menuItem = menuItemRepository.findById(request.menuItemId())
            .orElseThrow(() ->new NotFoundException(
                    String.format("MenuItem with ID: %s not found!", request.menuItemId())));

        StopList stopList = StopList.builder()
                .reason(request.reason())
                .date(request.date())
                .menuItem(menuItem)
                .build();
        
        stopListRepository.save(stopList);
        
        
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("StopList with name: %s successfully saved!",stopList.getReason()))
                .build();
    }

    @Override
    public List<StopListResponse> getAllStopList() {
        return stopListRepository.getAllStopList();
    }


    @Override
    public StopListResponse findByIdStopList(Long id) {
       return stopListRepository.findStopListById(id)
                .orElseThrow(()-> new NotFoundException(
                        String.format("StopList with ID: %s not found!", id)));


    }

    @Override
    public SimpleResponse updateStopList(Long id, StopListRequest request) {
        StopList stopList = stopListRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("StopList with  ID: %s not found!",id)));

        MenuItem menuItem = menuItemRepository.findById(request.menuItemId()).orElseThrow(()->
                new NotFoundException(String.format("MenuItem with  ID: %s not found!", request.menuItemId())));

        stopList.setDate(request.date());
        stopList.setMenuItem(menuItem);
        stopList.setReason(request.reason());
        menuItem.setStopList(stopList);

        stopListRepository.save(stopList);

        return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                                .message(String.format("StopList with  ID: %s  successfully updated ! ",id))
                                  .build();
    }

    @Override
    public SimpleResponse deleteStopList(Long id) {
    if (!stopListRepository.existsById(id)){
       throw new NotFoundException((String.format("StopList with ID: %s not found",id)));
    }
    stopListRepository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("StopList with  ID: %s  successfully deleted ! ",id))
                .build();
    }
}
