package com.example.springsecurityjwt.Entities;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// import com.example.springsecurityjwt.Services.impl.override;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.*;
import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name="myuser")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer Id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    // @Enumerated(EnumType.STRING)
//   private Role role;

    @OneToMany(mappedBy = "user") //one user may have multiple tokens
    private List<Token> tokens;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
        var c= Collections.singletonList(new SimpleGrantedAuthority(role.name()));
        System.err.println(c);
        return c;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
        return email;
    }
    public void setfirstname(String firstname){
        this.firstname=firstname;

    }
    public void setlastname(String lastname){
        this.lastname=lastname;
    }
    public String getfirstname(){
        return firstname;
    }
    public String getlastname(){
        return lastname;
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

public static Object builder() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'builder'");
}
}