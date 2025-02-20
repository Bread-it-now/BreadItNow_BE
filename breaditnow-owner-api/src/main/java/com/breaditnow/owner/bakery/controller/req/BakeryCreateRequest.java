package com.breaditnow.owner.bakery.controller.req;

import lombok.Data;

@Data
public class BakeryCreateRequest {
	private String name;
	private String addressCode;
	private String addressDescription;
	private String phone;
	private String openTime;
	private String introduction;
	private String profileImage;
}
