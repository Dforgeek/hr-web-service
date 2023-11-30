package com.example.backend.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;


// DAO класс отражающий таблицу в бд roles,
// каждое имя роли в таблице должно соответствовать шаблону ROLE_ИМЯ
// например ROLE_USER


@Entity
@Table(name = "roles", schema = "public", catalog = "hrBackDb")
@Getter
@Setter
@ToString(exclude = {"client"})
@EqualsAndHashCode(exclude = {"client"})
@AllArgsConstructor
@RequiredArgsConstructor
@Transactional
public class Roles implements GrantedAuthority {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = -1)
    private String name;

    public Roles(int id, String name) {
        this.id = id;
        this.name = name;
    }
    @JsonIgnore
    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)/*(cascade = CascadeType.ALL, fetch = FetchType.EAGER)*/
    private Set<UserDao> client;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Roles roles)) return false;
//        return getId() == roles.getId() && getName().equals(roles.getName()) && getClient().equals(roles.getClient());
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(getId(), getName());
//    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
