package company.service;

import company.dto.requests.AuthUserRequest;
import company.dto.requests.UserRequest;
import company.dto.responses.PaginationResponse;
import company.dto.responses.SimpleResponse;
import company.dto.responses.user.AbstractApplicationClass;
import company.dto.responses.user.UserAllResponse;
import company.dto.responses.user.UserResponse;
import company.dto.responses.user.UserTokenResponse;

import java.util.List;

public interface UserService {


    UserTokenResponse authenticate(AuthUserRequest userRequest);

    List<UserAllResponse> getAllUsers(String role, String lastName);

    SimpleResponse saveUsers(UserRequest request);

    UserResponse findByIdUsers(Long id);

    SimpleResponse updateUsers(Long id, UserRequest request);

    SimpleResponse deleteUsers(Long id);

    SimpleResponse application(UserRequest request);

    AbstractApplicationClass applications(Long id, Boolean accepted);

    PaginationResponse getUserPagination(int page, int size);
}
