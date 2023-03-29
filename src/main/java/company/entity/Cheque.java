package company.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "cheques")
@AllArgsConstructor
@NoArgsConstructor

public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cheque_seq")
    @SequenceGenerator(name = "cheque_seq",allocationSize = 1)
    private Long id;

    private Integer priceAverage;

    private LocalDate createdAt;

    @ManyToOne(cascade = {CascadeType.PERSIST,MERGE, REFRESH,DETACH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "cheques",cascade = {CascadeType.PERSIST, MERGE, REFRESH, DETACH})
    private List<MenuItem> menuItems = new ArrayList<>();


}