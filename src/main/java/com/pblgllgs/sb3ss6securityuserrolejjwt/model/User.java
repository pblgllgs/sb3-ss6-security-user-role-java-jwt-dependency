package com.pblgllgs.sb3ss6securityuserrolejjwt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/*
 *
 * @author pblgl
 * Created on 26-03-2024
 *
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date(System.currentTimeMillis());
    }

    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String mobileNumber;
    private Date createdAt;
    private Date updatedAt;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "user_id",
                    foreignKey = @ForeignKey(
                            name = "user_roles_user_id")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(
                            name = "user_roles_role_id"))
    )
    private Set<Role> roles = new HashSet<>();

    public User( String userName, String email, String password, String mobileNumber, Set<Role> roles) {
        this.userId = email;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
