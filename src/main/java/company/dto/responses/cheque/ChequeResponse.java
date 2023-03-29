package company.dto.responses.cheque;

import company.dto.responses.menuItem.MenuItemResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
@Builder
public class ChequeResponse{

        String waiterFullName;
        List<MenuItemResponse> menuItems;
        BigDecimal priceAverage;
        Integer service;
        BigDecimal grandTotal;


}
