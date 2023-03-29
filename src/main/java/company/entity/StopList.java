package company.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "stop_lists")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stop_list_seq")
    @SequenceGenerator(name = "stop_list_seq",allocationSize = 1)
    private Long id;

    private String reason;

    private LocalDate date;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, MERGE, REFRESH,DETACH}, orphanRemoval = true)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

}