package jjon.pop.model;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginRequestBody(
		@NotEmpty String username, @NotEmpty String password) {}

