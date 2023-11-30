package com.example.backend.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "client", schema = "public", catalog = "hrBackDb")
@Getter
@Setter
@ToString(exclude = {"roles"})
@EqualsAndHashCode(exclude = {"roles"})
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class UserDao implements UserDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "username", nullable = true, length = -1)
    private String username;
    @Basic
    @Column(name = "phone", nullable = true)
    private Integer phone;
    @Basic
    @Column(name = "name", nullable = true, length = -1)
    private String name;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)/*(mappedBy = "client", fetch = FetchType.EAGER)*/
    private List<Roles> roles;
    @Basic
    @Column(name = "password", nullable = true, length = -1)
    private String password;
    @Basic
    @Column(name = "login", nullable = true, length = -1)
    private String login;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
