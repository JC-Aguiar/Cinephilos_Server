package br.com.jcaguiar.cinephiles.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class PosterService {

    @Autowired private PicturesRepository dao;

    public PostersEntity saveAndFlush(@NotNull PostersEntity poster) {
        return dao.saveAndFlush(poster);
    }

}
