package br.com.jcaguiar.cinephiles.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.MappedSuperclass;

@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PROTECTED)
@MappedSuperclass
public class UserModel {

    String firstName;
    String lastName;
    String email;
    String password;
    String avatar;

}
