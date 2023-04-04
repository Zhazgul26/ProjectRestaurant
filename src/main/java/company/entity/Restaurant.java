package company.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "restaurant")
@AllArgsConstructor
@NoArgsConstructor

public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq")
    @SequenceGenerator(name = "restaurant_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String location;

    private String resType;

    private int numberOfEmployees = 0;
    private Integer service;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems;

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
        numberOfEmployees++;


    }

    public Restaurant(String name, String location, String resType, Integer numberOfEmployees, Integer service, List<User> users, List<MenuItem> menuItems) {
        this.name = name;
        this.location = location;
        this.resType = resType;
        this.numberOfEmployees = numberOfEmployees;
        this.service = service;
        this.users = users;
        this.menuItems = menuItems;
    }
}