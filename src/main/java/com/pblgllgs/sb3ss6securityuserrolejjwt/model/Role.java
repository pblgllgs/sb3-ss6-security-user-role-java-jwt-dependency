package com.pblgllgs.sb3ss6securityuserrolejjwt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/*
 *
 * @author pblgl
 * Created on 26-03-2024
 *
 */
@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date(System.currentTimeMillis());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "role_sequence", sequenceName = "role_sequence", allocationSize = 1)
    private long id;
    private String name;
    private String description;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToMany(mappedBy = "roles")
    @Fetch(value = FetchMode.SELECT)
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }
}
