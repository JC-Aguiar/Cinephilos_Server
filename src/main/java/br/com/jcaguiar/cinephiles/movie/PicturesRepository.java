package br.com.jcaguiar.cinephiles.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PicturesRepository extends JpaRepository<PostersEntity, Integer> {
}
