package br.com.jcaguiar.cinephiles.util;

import br.com.jcaguiar.cinephiles.movie.PosterRepository;
import br.com.jcaguiar.cinephiles.user.UserEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.persistence.EntityManager;

@Configuration
public class ApiBeans {

//    @Bean(name = "multipartResolver")
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(100000);
//        return multipartResolver;
//    }

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

//    @Bean
//    @Primary
//    public MasterService masterService() {
//        return new MasterService() {
//            @Override
//            public Page pageCheck(Page page) {
//                return super.pageCheck(page);
//            }
//        }
//    }

}
