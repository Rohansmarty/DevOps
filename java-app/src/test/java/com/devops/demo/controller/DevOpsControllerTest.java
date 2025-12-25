	package com.devops.demo.controller;

	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

	import org.junit.jupiter.api.Test;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.test.web.servlet.MockMvc;

	@SpringBootTest
	@AutoConfigureMockMvc
	class DevOpsControllerTest {
		@Autowired
		private MockMvc mockMvc;

		@Test
		void health_returnsUp() throws Exception {
			mockMvc.perform(get("/health"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("UP"));
		}

		@Test
		void root_returnsMessage() throws Exception {
			mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Hello from DevOps Java app"));
		}
	}
