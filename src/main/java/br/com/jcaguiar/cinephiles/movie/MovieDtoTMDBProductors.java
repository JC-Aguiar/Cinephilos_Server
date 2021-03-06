package br.com.jcaguiar.cinephiles.movie;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieDtoTMDBProductors {

    Integer id;
    String logo_path;
    String name;
    String origin_country;
}
