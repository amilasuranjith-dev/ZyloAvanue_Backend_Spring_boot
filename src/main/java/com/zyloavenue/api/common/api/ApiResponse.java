package com.zyloavenue.api.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zyloavenue.api.common.exception.ApiError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
	private boolean success;
	private T data;
	private ApiError error;

	public static <T> ApiResponse<T> ok(T data) {
		return ApiResponse.<T>builder().success(true).data(data).build();
	}

	public static ApiResponse<Void> ok() {
		return ApiResponse.<Void>builder().success(true).build();
	}

	public static ApiResponse<Void> fail(ApiError error) {
		return ApiResponse.<Void>builder().success(false).error(error).build();
	}

	public static <T> ApiResponse<T> error(ApiError error) {
		return ApiResponse.<T>builder().success(false).error(error).build();
	}
}

