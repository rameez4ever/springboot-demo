package com.raydar.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CabaranController {

	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public String index() throws Exception {
		return "Hello from Cabaran Microservice!";
	}
}
