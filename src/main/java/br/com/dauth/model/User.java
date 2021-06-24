package br.com.dauth.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oauth_user", schema = "oauth")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "user_password", nullable = false, updatable = false)
    private String password;

    @Column(name = "user_full_name", nullable = false)
    private String userFullName;

    @Column(name = "super_user", nullable = false)
    private boolean superUser;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "oauth_user_authority", schema = "oauth", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "authority_id"))
    private Collection<Authority> authorities;
}
