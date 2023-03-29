package company.service.impl;

import company.config.jwt.JwtUtil;
import company.dto.requests.AuthUserRequest;
import company.dto.requests.UserRequest;
import company.dto.responses.PaginationResponse;
import company.dto.responses.SimpleResponse;
import company.dto.responses.user.*;
import company.entity.Restaurant;
import company.entity.User;
import company.entity.enums.Role;
import company.exeption.*;
import company.repository.RestaurantRepository;
import company.repository.UserRepository;
import company.service.UserService;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.restaurantRepository = restaurantRepository;
    }


    @PostConstruct
    public void saveAdmin() {
        User user = User.builder()
                .firstName("Zhazgul")
                .lastName("Zhoroeva")
                .email("admin@gmail.com")
                .password(encoder.encode("admin123"))
                .role(Role.ADMIN)
                .build();

        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }

    public UserTokenResponse authenticate(AuthUserRequest userRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.email(),
                        userRequest.password()));
        User user = userRepository.findByEmail(userRequest.email())
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with email: %s not found!", userRequest.email())));
        String token = jwtUtil.generateToken(user);
        return UserTokenResponse.builder()
                .email(user.getEmail())
                .token(token)
                .build();
    }



    @Override
    public List<UserAllResponse> getAllUsers(String role, String lastName) {
        if (role == null && lastName == null) {
            return userRepository.getAllUserResponses();
        } else if (role != null) {
            return userRepository.getAllUserResponsesByRole(role.toUpperCase() + "%");
        } else {
            return userRepository.getAllUserResponsesByLastName(lastName + "%");
        }

    }

    @Override
    public SimpleResponse saveUsers(UserRequest request) {
        Restaurant restaurant = restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Restaurant with ID: %s not found!", request.restaurantId())));

        if (restaurant.getUsers().size() > 15) {
            throw new BadCredentialException("The number of employees cannot be more than 15");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException(String.format("User with email: %s already exists!", request.email()));
        }

        int age = LocalDate.now().getYear() - request.dateOfBirth().getYear();

        if (request.role().equals(Role.CHEF)) {
            if (age < 25 || age > 45) {
                throw new BadRequestException("For the vacancy of a cook, the age range is from 25 to 45 years!");
            }

            if (request.experience() <= 1) {
             throw new BadRequestException("Cooking experience must be at least 2 years!");
            }
        } else if (request.role().equals(Role.WAITER)) {
            if (age < 18 || 30 < age) {
                throw new BadRequestException("For the vacancy of a waiter, the age range is from 18 to 30 years!");
            }
            if (request.experience() <= 1) {
               throw new BadRequestException("Experience as a waiter must be at least 1 year!");
            }
        }


        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .dateOfBirth(request.dateOfBirth())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .role(request.role())
                .phoneNumber(request.phoneNumber())
                .experience(request.experience())
                .restaurant(restaurant)
                .build();

        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("User with fullName: %s %s successfully saved!", user.getFirstName(), user.getLastName()))
                .build();
    }


    @Override
    public UserResponse findByIdUsers(Long id) {
        return userRepository.getUserResponseById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with ID: %s not found!", id)));
    }

    @Override
    public SimpleResponse updateUsers(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with ID: %s not found!", id)));

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setDateOfBirth(request.dateOfBirth());
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        user.setPhoneNumber(request.phoneNumber());
        user.setRole(request.role());
        user.setExperience(request.experience());
        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("User with ID: %s successfully updated", id))
                .build();
    }

    @Override
    public SimpleResponse deleteUsers(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("User with ID: %s not found!", id)));

        user.getCheques().forEach(cheque -> cheque.getMenuItems()
                .forEach(menuItem -> menuItem.setCheques(null)));
        userRepository.delete(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("User with ID: %s successfully deleted", id))
                .build();
    }

    @Override
    public SimpleResponse application(UserRequest request) {
        Restaurant restaurant = restaurantRepository.findById(1L).orElseThrow(() -> new NotFoundException(
                String.format("Restaurant with ID: %s not found!", 1L)));
        if (restaurant.getUsers().size() > 15) {
         throw new BadRequestException("Now vacancy !");
        }
        if (request.role().equals(Role.ADMIN)) {
           throw new ForbiddenException("There is no vacancy for the administrator!");
        }

        if (userRepository.existsByEmail(request.email())) {
           throw  new ConflictException(String.format("User with email: %s already exists!", request.email()));
        }

        if (request.role().equals(Role.CHEF)) {
            Period period = Period.between(request.dateOfBirth(), LocalDate.now());
            if (period.getYears() < 25 || period.getYears() > 45) {
                throw new BadRequestException("For the vacancy of a cook, the age range is from 25 to 45 years!");
            }
            if (request.experience() <= 1) {
               throw  new BadRequestException("Cooking experience must be at least 2 years!");
            }
        } else if (request.role().equals(Role.WAITER)) {
            Period period = Period.between(request.dateOfBirth(), LocalDate.now());
            if (period.getYears() < 18 || 30 < period.getYears()) {
               throw new BadRequestException("For the vacancy of a waiter, the age range is from 18 to 30 years!");
            }
            if (request.experience() <= 1) {
               throw  new BadRequestException("Experience as a waiter must be at least 1 year!");
            }
        }

        User user = User.builder()
                .firstName(request.firstName())
                  .lastName(request.lastName())
                    .dateOfBirth(request.dateOfBirth())
                      .email(request.email())
                        .password(encoder.encode(request.password()))
                          .role(request.role())
                            .phoneNumber(request.phoneNumber())
                               .experience(request.experience())
                                    .build();

        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Your application has been successfully sent")
                .build();    }

    @Override
    public AbstractApplicationClass applications(Long id, Boolean accepted) {
        if (accepted == null && id != null) {
            if (userRepository.findAllByAcceptedFalse().stream().noneMatch(u -> u.getId().equals(id))) {
                return new ApplicationResponse(
                        HttpStatus.NOT_FOUND,
                        String.format("User with ID: %s not in requests", id),
                        null);
            }

            return new ApplicationResponse(
                    HttpStatus.OK,
                    "User with ID: " + id,
                    userRepository.getUserResponseById(id)
                            .orElseThrow(() -> new NotFoundException(
                                    String.format("User with ID: %s not found!", id))));
        }

        if (id != null && Boolean.TRUE.equals(accepted)) {
            List<User> users = userRepository.findAllByAcceptedFalse();
            if (users != null) {

                boolean isExists = userRepository.findAllByAcceptedFalse().stream().anyMatch(u -> u.getId().equals(id));
                if (!isExists) {
                    return new ApplicationResponse(
                            HttpStatus.NOT_FOUND,
                            String.format("User with ID: %s not in requests", id),
                            null);
                }

                Restaurant restaurant = restaurantRepository.findById(1L)
                        .orElseThrow(() -> new NotFoundException(
                                String.format("Restaurant with ID: %s not found!", 1L)));

                User user = users.stream().filter(u -> u.getId().equals(id)).findFirst().
                        orElseThrow(() -> new NotFoundException(
                                String.format("User with ID: %s not found!", id)));

                user.setRestaurant(restaurant);


                userRepository.save(user);

                return new ApplicationResponse(
                        HttpStatus.OK,
                        String.format("User with fullName: %s %s successfully accepted",
                                user.getFirstName(), user.getLastName()),
                        userRepository.getUserResponseById(id)
                                .orElseThrow(() -> new NotFoundException(
                                        String.format("User with ID: %s not found", id))));

            }

        } else if (id != null) {
            List<User> users = userRepository.findAllByAcceptedFalse();
            if (users != null) {

                boolean isExists = userRepository.findAllByAcceptedFalse().stream().anyMatch(u -> u.getId().equals(id));
                if (!isExists) {
                    return new ApplicationResponse(
                            HttpStatus.NOT_FOUND,
                            String.format("User with ID: %s not in requests", id)
                    );
                }

                userRepository.deleteById(id);
                return new ApplicationResponse(
                        HttpStatus.OK,
                        String.format("User with id: %s successfully not accepted!", id),
                        null);
            }
        } else if (accepted == null) {
            return new ApplicationsResponse(
                    HttpStatus.OK,
                    "Not accepted",
                    userRepository.getAllUserResponsesByAcceptedFalse());
        }
        return null;
    }


    @Override
    public PaginationResponse getUserPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> pageUsers = userRepository.findAll(pageable);
        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setUsers(pageUsers.getContent());
        paginationResponse.setCurrentPage(pageable.getPageNumber());
        paginationResponse.setPageSize(pageUsers.getTotalPages());


        return paginationResponse;
    }


}
