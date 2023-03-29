package company.repository;


import company.dto.responses.user.UserAllResponse;
import company.dto.responses.user.UserResponse;
import company.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);


    @Query("SELECT NEW company.dto.responses.user.UserAllResponse(u.id,concat(u.lastName,' ',u.firstName),u.email,u.role) FROM User u")
    List<UserAllResponse> getAllUserResponses();

    @Query("SELECT NEW company.dto.responses.user.UserAllResponse(u.id,concat(u.lastName,' ',u.firstName),u.email,u.role) FROM User u WHERE LOWER(u.role) LIKE  LOWER(:role) ")
    List<UserAllResponse> getAllUserResponsesByRole(String role);

    @Query("SELECT NEW company.dto.responses.user.UserAllResponse(u.id,concat(u.lastName,' ',u.firstName),u.email,u.role) " +
            "FROM User u WHERE u.lastName LIKE (:lastName)")
    List<UserAllResponse> getAllUserResponsesByLastName(String lastName);
    @Query("SELECT NEW company.dto.responses.user.UserResponse(u.id,concat(u.lastName,' ',u.firstName),u.dateOfBirth,u.email,u.phoneNumber,u.role,u.experience) " +
            "FROM User u WHERE u.id=:id")
    Optional<UserResponse> getUserResponseById(Long id);
    List<User> findAllByAcceptedFalse();
    @Query("SELECT NEW company.dto.responses.user.UserAllResponse(u.id,concat(u.lastName,' ',u.firstName),u.email,u.role)" +
            " FROM User u WHERE u.accepted=false")
    List<UserAllResponse> getAllUserResponsesByAcceptedFalse();

    @Override
    Page<User> findAll(Pageable pageable);
}