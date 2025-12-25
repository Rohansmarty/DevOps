	package com.devops.demo.controller;

	import java.time.Instant;
	import java.util.LinkedHashMap;
	import java.util.Map;

	import org.springframework.http.MediaType;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;

	@RestController
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public class DevOpsController {
		@GetMapping("/")
		public Map<String, Object> hello() {
			Map<String, Object> payload = new LinkedHashMap<>();
			payload.put("message", "Hello from DevOps Java app");
			payload.put("timestamp", Instant.now().toString());
			return payload;
		}

		@GetMapping("/health")
		public Map<String, Object> health() {
			Map<String, Object> payload = new LinkedHashMap<>();
			payload.put("status", "UP");
			return payload;
		}
	}
