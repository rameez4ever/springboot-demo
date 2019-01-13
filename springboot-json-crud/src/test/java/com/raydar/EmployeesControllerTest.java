package com.raydar;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CabaranApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeesControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void verifyGetAllEmployees() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee/get").accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.status").value(true));	
	}

	@Test
	public void verifyGetSpecificEmployees() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee/get?key=age&operator=lt&value=10&sort=asc").accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.status").value(true));	
	}
}
