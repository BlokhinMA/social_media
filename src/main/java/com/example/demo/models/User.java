package com.example.demo.models;

import com.example.demo.models.enums.Role;
import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class User implements UserDetails {

    private int id;
    @Size(min = 1, max = 255, message = "Количество символов должно быть больше 0 и меньше 256")
    @NotBlank(message = "Поле не должно состоять только из пробелов")
    private String login;
    @Size(min = 1, max = 255, message = "Количество символов должно быть больше 0 и меньше 256")
    @NotBlank(message = "Поле не должно состоять только из пробелов")
    @Email
    private String email;
    @Size(min = 1, max = 255, message = "Количество символов должно быть больше 0 и меньше 256")
    @NotBlank(message = "Поле не должно состоять только из пробелов")
    private String firstName;
    @Size(min = 1, max = 255, message = "Количество символов должно быть больше 0 и меньше 256")
    @NotBlank(message = "Поле не должно состоять только из пробелов")
    private String lastName;
    @NotNull(message = "Заполните поле \"Дата рождения\"")
    @PastOrPresent(message = "Указанная дата должна быть либо в прошлом, либо в настоящем (сегодняшняя)")
    private LocalDate birthDate;
    @Size(min = 1, max = 255, message = "Количество символов должно быть больше 0 и меньше 256")
    private String password;
    private Set<Role> roles = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
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
