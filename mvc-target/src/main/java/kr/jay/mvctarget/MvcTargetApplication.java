package kr.jay.mvctarget;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MvcTargetApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcTargetApplication.class, args);
	}

	@GetMapping("/posts/{id}")
	public Map<String, String> getPosts(@PathVariable("id") final Long id) throws Exception {
		Thread.sleep(3000);
		if (id > 10L) {
			throw new Exception("id must be less than 10");
		}
		return Map.of(
			"id", id.toString(),
			"content", "posts content id %d".formatted(id)
		);
	}
}
