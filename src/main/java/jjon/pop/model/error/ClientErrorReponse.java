package jjon.pop.model.error;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ClientErrorReponse(HttpStatus status, Object message) {}
