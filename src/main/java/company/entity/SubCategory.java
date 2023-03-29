package company.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "sub_categories")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_category_seq")
    @SequenceGenerator(name = "sub_category_seq",allocationSize = 1)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems;

    @ManyToOne(cascade = {CascadeType.PERSIST,MERGE,REFRESH,DETACH})
    @JoinColumn(name = "category_id")
    private Category category;

}