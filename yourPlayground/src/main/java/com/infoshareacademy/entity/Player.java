package com.infoshareacademy.entity;

import com.google.gson.GsonBuilder;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String mail;

    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "player_role_jointable",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn( name = "role_id")
    )
    private Set<Role> roles;
}
