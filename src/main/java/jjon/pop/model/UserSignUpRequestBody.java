package jjon.pop.model;

import jakarta.validation.constraints.NotEmpty;

public record UserSignUpRequestBody(
		@NotEmpty String username, @NotEmpty String password) {}

