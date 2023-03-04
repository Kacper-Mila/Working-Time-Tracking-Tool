package com.project.w3t.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.w3t.model.request.Request;
import com.project.w3t.security.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Builder
//@DynamicInsert
//@DynamicUpdate
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String userId;
    private String password;
    private String firstName;
    private String lastName;
    private Integer holidays;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Token> tokens;
    private String managerId;
    private String teamId;

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @ToString.Exclude
    private List<Request> requestList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id"))
    @ToString.Exclude
    private Collection<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userType.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}