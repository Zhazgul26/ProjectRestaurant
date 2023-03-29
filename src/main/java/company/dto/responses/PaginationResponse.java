package company.dto.responses;

import company.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse {
    private List<User> users;
   private int currentPage;
   private  int pageSize;

}
