package com.app.user.models.exception;

import java.util.Date;

import lombok.Data;

@Data
public class ExceptionResponse {

	private Date timestamp;
	private String mensaje;
	private String detalles;
	private int statusCode;
	

	public ExceptionResponse(int statusCode, Date timestamp, String mensaje, String detalles) {
		super();
		this.statusCode =statusCode;
		this.timestamp = timestamp;
		this.mensaje = mensaje;
		this.detalles = detalles;
	}


}
