package com.zyloavenue.api.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
	private String code;
	private String message;
	private Map<String, Object> details;

	public static ApiError of(String code, String message) {
		return ApiError.builder().code(code).message(message).build();
	}
}

