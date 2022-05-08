package br.com.jcaguiar.cinephiles.movie;

import br.com.jcaguiar.cinephiles.enums.GenreEnum;
import br.com.jcaguiar.cinephiles.master.MasterController;
import br.com.jcaguiar.cinephiles.master.MasterProcess;
import br.com.jcaguiar.cinephiles.master.ProcessLine;
import br.com.jcaguiar.cinephiles.util.ConsoleLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("movie")
public class MovieController extends MasterController
    <Integer, MovieEntity, MovieDtoRequest, MovieDtoResponse, MovieController> {

    @Autowired
    private final MovieService service;

    public MovieController(MovieService service) {
        super(service);
        this.service = service;
        try {
            endpointsGet.put("genre", getClass().getMethod("byGenre", String.class, int.class, int.class));
            endpointsGet.put("title", getClass().getMethod("byTitle", String.class, int.class, int.class));
            endpointsGet.put("synopsis", getClass().getMethod("bySynopsis", String.class, int.class, int.class));
            endpointsGet.put("director", getClass().getMethod("byDirector", String.class, int.class, int.class));
            endpointsGet.put("actor", getClass().getMethod("byActor", String.class, int.class, int.class));
            endpointsGet.put("producer", getClass().getMethod("byProducer", String.class, int.class, int.class));
            endpointsGet.put("text", getClass().getMethod("byText", String.class, int.class, int.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        printInfo();
    }

    //GET: by GENRE
    // A shortcut to create a ResponseEntity with the status CREATED.
    @ConsoleLog
    public ResponseEntity<?> byGenre(@NotBlank String genre, int page, int itens) {
        GenreEnum genreEnum = Arrays.stream(GenreEnum.values())
                                    .filter(en -> genre.equalsIgnoreCase(en.toString()))
                                    .findFirst()
                                    .orElseThrow();
        final Pageable pageConfig = PageRequest.of(page, itens, Sort.by("title").ascending());
        final Page<MovieEntity> moviesEntities = service.getMoviesByGenre(genreEnum, pageConfig);
        return proxy().craftResponsePage(moviesEntities);
    }

    //GET: by TITLE
    // A shortcut to create a ResponseEntity with the status CREATED.
    @ConsoleLog
    public ResponseEntity<?> byTitle(@NotBlank String title, int page, int itens) {
        final Pageable pageConfig = PageRequest.of(page, itens, Sort.by("title").ascending());
        final Page<MovieEntity> moviesEntities = service.getMoviesByTitle(title, pageConfig);
        return proxy().craftResponsePage(moviesEntities);
    }

    //GET: by SYNOPSIS
    // A shortcut to create a ResponseEntity with the status CREATED.
    @ConsoleLog
    public ResponseEntity<?> bySynopsis(@NotBlank String synopsis, int page, int itens) {
        final Pageable pageConfig = PageRequest.of(page, itens, Sort.by("title").ascending());
        final Page<MovieEntity> moviesEntities = service.getMoviesBySynopsis(synopsis, pageConfig);
        return proxy().craftResponsePage(moviesEntities);
    }

    //GET: by DIRECTOR
    // A shortcut to create a ResponseEntity with the status CREATED.
    @ConsoleLog
    public ResponseEntity<?> byDirector(@NotBlank String director, int page, int itens) {
        final Pageable pageConfig = PageRequest.of(page, itens, Sort.by("title").ascending());
        final Page<MovieEntity> moviesEntities = service.getMoviesByDirector(director, pageConfig);
        return proxy().craftResponsePage(moviesEntities);
    }

    //GET: by ACTOR
    // A shortcut to create a ResponseEntity with the status CREATED.
    @ConsoleLog
    public ResponseEntity<?> byActor(@NotBlank String actor, int page, int itens) {
        final Pageable pageConfig = PageRequest.of(page, itens, Sort.by("title").ascending());
        final Page<MovieEntity> moviesEntities = service.getMoviesByActor(actor, pageConfig);
        return proxy().craftResponsePage(moviesEntities);
    }

    //GET: by PRODUCER
    // A shortcut to create a ResponseEntity with the status CREATED.
    @ConsoleLog
    public ResponseEntity<?> byProducer(@NotBlank String producer, int page, int itens) {
        final Pageable pageConfig = PageRequest.of(page, itens, Sort.by("title").ascending());
        final Page<MovieEntity> moviesEntities = service.getMoviesByProducer(producer, pageConfig);
        return proxy().craftResponsePage(moviesEntities);
    }

    //GET: by KEY-WORD
    // A shortcut to create a ResponseEntity with the status CREATED.
    @ConsoleLog
    public ResponseEntity<?> byText(@NotBlank String text, int page, int itens) {
        final PageRequest pageConfig = PageRequest.of(page, itens, Sort.by("title").ascending());
        //        final MovieEntity movieTemplate = MovieEntity.builder()
        //                .title(text).synopsis(text).build();
        //        movieTemplate.addDirector(text).addActor(text).addProctor(text);
        //        final Example<MovieEntity> movieEx = Example.of(movieTemplate, MATCHER_ANY);
        final Page<MovieEntity> moviesEntities = service.getMoviesByTextLike(text, pageConfig);
        return proxy().craftResponsePage(moviesEntities);
    }

    //POST: ADVANCED SEARCH
    // A shortcut to create a ResponseEntity with the status CREATED.
    @ConsoleLog
    @PostMapping(name = "search", params = {"page", "itens"})
    public ResponseEntity<?> byExampleOf(@Valid MovieDtoRequest movie, int page, int itens) {
        final Pageable pageConfig = PageRequest.of(page, itens, Sort.by("title").ascending());
        final MovieEntity movieEntity = parseToEntity(movie);
        final Example<MovieEntity> movieEx = Example.of(movieEntity, MATCHER_ALL);
        final Page<MovieEntity> moviesEntities = service.getMoviesByExample(movieEx, pageConfig);
        return proxy().craftResponsePage(moviesEntities);
    }

    //TODO: CREATE HANDLE RESPONSE AGAINST DUPLICATED MOVIES
    // org.postgresql.util.PSQLException
    //POST: INSERT ONE FILE
    @ConsoleLog
    @PostMapping(value = "add/one/tmdb", params = {"page", "itens"}, consumes = {"application/json", "text/plain"})
    public ResponseEntity<?> addOne(final @RequestBody Map<String, Object> file,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "itens", defaultValue = "12") int itens) {
        final ProcessLine<MovieDtoTMDB> dtoTMDB = service.parseMapToDto(file);
        final ProcessLine<MovieEntity> movie = service.persistDtoTMDB(dtoTMDB);
        final MasterProcess<MovieEntity> process = new MasterProcess<>(movie);
        return new ResponseEntity<>(process, HttpStatus.OK);
    }

    //POST: INSERT MANY FILES
    @ConsoleLog
    @PostMapping(value = "add/many/tmdb", consumes = {"multipart/form-data"})
    public ResponseEntity<?> addAll(
        @RequestParam("files") List<MultipartFile> files,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "itens", defaultValue = "12") int itens) {
        final Pageable pageConfig = PageRequest.of(page, itens, Sort.unsorted()); //TODO: UNUSED? (!)
        final List<ProcessLine<MovieEntity>> movies = files.stream()
            .map(service::parseFileToJson)
            .map(service::parseJsonToDto)
            .map(service::persistDtoTMDB)
            .toList();
        final MasterProcess<MovieEntity> process = new MasterProcess<>(movies);
        return new ResponseEntity<>(process, HttpStatus.OK);
    }

    //todo: remove this in production
    //DELETE: ALL FILES
    @ConsoleLog
    @DeleteMapping("del/all")
    public ResponseEntity<?> deleteAll() {
        service.deleteAll();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
