package br.com.jcaguiar.cinephiles.movie;

import br.com.jcaguiar.cinephiles.master.MasterDtoRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Transient;

import java.time.Duration;

@Value
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
public final class MoviePostRequest extends MovieModel implements MasterDtoRequest {

    @Transient
    @JsonIgnore
    String logo = null;
    @Transient
    @JsonIgnore
    String posters = null;
    @Transient
    @JsonIgnore
    String font = null;
    @Transient
    @JsonIgnore
    String position = null;
    @Transient
    @JsonIgnore
    String fit = null;
    @Transient
    @JsonIgnore
    Duration duration = null;

}
