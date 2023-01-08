package cz.cvut.ear.sem.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.List;

@Entity
@DiscriminatorValue("admin")
@Getter
@Setter
public class Admin extends User{
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(Authority.ADMIN.toString())
        );
    }
}
