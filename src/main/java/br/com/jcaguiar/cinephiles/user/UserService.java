package br.com.jcaguiar.cinephiles.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Optional;

@Service
public class UserService {

    //TODO: poderia ser static?
    @Autowired
    private UserRepository dao;

    public UserEntity getUserById(@Positive @NotNull int id) {
        return Optional.ofNullable(dao.getById(id)).orElseThrow();
    }

    public UserEntity getUserByEmail(@NotBlank String email) {
        return dao.findByEmail(email).orElseThrow();
    }

}
