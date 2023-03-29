package company.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "menu_items")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_item_seq")
    @SequenceGenerator(name = "menu_item_seq",allocationSize = 1)
    private Long id;

    private String name;
    private String image;
    private Integer price;
    private String description;
    private Boolean isVegetarian;

    @ManyToOne(cascade = {CascadeType.PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

    @OneToOne(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private StopList stopList;

    @ManyToMany(cascade = {CascadeType.PERSIST, MERGE, REFRESH, DETACH})
    @JsonIgnore
    private List<Cheque> cheques;


    @ManyToOne(cascade = {CascadeType.PERSIST,MERGE,REFRESH, DETACH})
    @JsonIgnore
    private SubCategory subCategory;

    public void addCheque(Cheque cheque){
        if (cheques == null){
            cheques = new ArrayList<>();
        }
        cheques.add(cheque);
    }

}