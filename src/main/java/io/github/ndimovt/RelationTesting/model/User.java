package io.github.ndimovt.RelationTesting.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * The class User
 */
@Entity
@Table(name = "users")
@Data
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, length = 100, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(updatable = false, name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "role")
    private String role;

    /**
     * Instantiates User
     * @param id Long object
     * @param name String object
     * @param username String object
     * @param password String object
     * @param createdAt Date object
     * @param role String object
     */
    public User(Long id, String name, String username, String password, Date createdAt, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.role = role;
    }

    /**
     * Returns list of authorities
     * @return List object
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    /**
     * Checks if account is not expired
     * @return Boolean primitive
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Checks if account is not locked
     * @return Boolean primitive
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Checks if account is not expired
     * @return Boolean primitive
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Checks if account is enabled
     * @return Boolean primitive
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
