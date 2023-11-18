package com.example.demo.models;

import com.example.demo.models.enums.Role;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class User implements UserDetails {

    private int id;
    @Size(min = 1, max = 255, message = "Количество символов должно быть больше 0 и меньше 256")
    @Pattern(regexp="\\S+", message="Поле не должно содержать пробелы")
    private String login;
    @Size(min = 1, max = 255, message = "Количество символов должно быть больше 0 и меньше 256")
    @Pattern(regexp="\\S+", message="Поле не должно содержать пробелы")
    @Email
    private String email;
    @Size(min = 1, max = 255, message = "Количество символов должно быть больше 0 и меньше 256")
    @Pattern(regexp="\\S+", message="Поле не должно содержать пробелы")
    private String firstName;
    @Size(min = 1, max = 255, message = "Количество символов должно быть больше 0 и меньше 256")
    @Pattern(regexp="\\S+", message="Поле не должно содержать пробелы")
    private String lastName;
    @NotNull(message = "Заполните поле \"Дата рождения\"")
    @PastOrPresent(message = "Указанная дата должна быть либо в прошлом, либо в настоящем (сегодняшняя)")
    private LocalDate birthDate;
    @Size(min = 1, max = 255, message = "Количество символов должно быть больше 0 и меньше 256")
    private String password;
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
