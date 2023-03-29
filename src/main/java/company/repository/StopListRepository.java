package company.repository;

import company.dto.responses.stopList.StopListResponse;
import company.entity.StopList;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface StopListRepository extends JpaRepository<StopList, Long> {
    @Query("select  new company.dto.responses.stopList.StopListResponse(s.menuItem.name,s.reason,s.date) from StopList s")
List<StopListResponse> getAllStopList();

    @Query("select new company.dto.responses.stopList.StopListResponse(s.menuItem.name,s.reason,s.date) from StopList s")
    Optional<StopListResponse> findStopListById(Long id);

    @Query("select count (s)  from StopList s where s.date = ?1 and s.menuItem.id = ?2")
    Integer count (LocalDate date, Long menuItemId);
    List<StopList> findByDate(LocalDate date);
}