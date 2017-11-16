package br.com.money.auth.usuario;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "regras")
public class Regra implements GrantedAuthority {

	@Id
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
