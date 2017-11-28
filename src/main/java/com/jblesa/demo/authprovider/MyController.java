package com.jblesa.demo.authprovider;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
	
	@GetMapping("/me")
	@PreAuthorize("hasRole('ADMIN')")
	public String getRole() {
		return "Hello " + SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	@GetMapping("/publish")
	@PreAuthorize("hasAuthority('PUBLISH')")
	public String publish() {
		return "Hello " + SecurityContextHolder.getContext().getAuthentication().getName();
	}

}