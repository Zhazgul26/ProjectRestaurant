package company.repository;

import company.entity.Cheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {

}