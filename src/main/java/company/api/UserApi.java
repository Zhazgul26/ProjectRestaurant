package company.api;

import company.dto.requests.AuthUserRequest;
import company.dto.requests.UserRequest;
import company.dto.responses.PaginationResponse;
import company.dto.responses.SimpleResponse;
import company.dto.responses.user.AbstractApplicationClass;
import company.dto.responses.user.UserAllResponse;
import company.dto.responses.user.UserResponse;
import company.dto.responses.user.UserTokenResponse;
import company.service.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserApi {
    private final UserService userService;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }




    @PostMapping("/login")
    public UserTokenResponse login(@RequestBody @Valid AuthUserRequest userRequest) {
        return userService.authenticate(userRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public List<UserAllResponse> finaAllUsers(@RequestParam(required = false) String role,
                                              @RequestParam(required = false) String lastName) {
        return userService.getAllUsers(role, lastName);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveUser(@RequestBody @Valid UserRequest request) {

        return userService.saveUsers(request);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findByIdUsers(id);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        return userService.updateUsers(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return userService.deleteUsers(id);
    }


    @PostMapping("/application")
    public SimpleResponse application(@RequestBody @Valid UserRequest request) {
        return userService.application(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/applications")
    public AbstractApplicationClass applications(@RequestParam(required = false) Long id,
                                                 @RequestParam(required = false) Boolean accepted) {
        return userService.applications(id, accepted);
    }

@GetMapping("/pagination")
    public PaginationResponse getUserPagination(@RequestParam int page,
                                                @RequestParam int size){

        return userService.getUserPagination(page,size);


}
}
