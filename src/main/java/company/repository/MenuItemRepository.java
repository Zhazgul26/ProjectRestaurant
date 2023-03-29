package company.repository;
import company.dto.responses.menuItem.MenuItemResponse;
import company.dto.responses.menuItem.MenuItemResponseSearch;
import company.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    boolean existsByName(String name);

    @Query("select new company.dto.responses.menuItem.MenuItemResponse(" +
            "m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m where m.id=:id")
    Optional<MenuItemResponse> findMenuItemById(Long id);

   @Query("select new company.dto.responses.menuItem.MenuItemResponseSearch(c.name,s.name,m.name,m.image,m.price)from" +
           " MenuItem m join m.subCategory s join s.category c where " +
           "m.name like %:keyWord% or c.name like %:keyWord% or s.name like %:keyWord% ")
    List<MenuItemResponseSearch> search(String keyWord);
    List<MenuItem> getAllByOrderByPriceAsc();
    List<MenuItem> getAllByOrderByPriceDesc();
    List<MenuItem> findMenuItemByIsVegetarian(Boolean isFalse);
    Optional<MenuItem> findByName(String name);


}

