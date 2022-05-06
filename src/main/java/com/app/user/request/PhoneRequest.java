package com.app.user.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.app.user.models.permisos.Phone;

import lombok.Data;

@Data
public class PhoneRequest {
	
	@JsonProperty("listPhone")
	private List<Phone> listPhone;
	
	@JsonProperty("numberPhone")
	private String numberPhone;
	
	@JsonProperty("cityCode")
	private String cityCode;
	
	@JsonProperty("countryCode")
	private String countryCode;

}
