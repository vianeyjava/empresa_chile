/**
 * 
 */
package com.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Vianey Vargas Morales
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyApiResponse<T> {
	private Integer code;
	private String message;
	private T content;
}
