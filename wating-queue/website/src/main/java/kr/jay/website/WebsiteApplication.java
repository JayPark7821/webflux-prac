package kr.jay.website;

import java.net.URI;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@SpringBootApplication
@Controller
public class WebsiteApplication {

	private final RestTemplate restTemplate = new RestTemplate();
	
	public static void main(String[] args) {
		SpringApplication.run(WebsiteApplication.class, args);
	}

	@GetMapping("/")
	String index(
		@RequestParam(name = "queue", defaultValue = "default") final String queue,
		@RequestParam("user_id") final Long userId,
		HttpServletRequest request
	) {
		var cookies = request.getCookies();
		var cookieName = "user-queue-%s-token".formatted(queue);

		String token = "";
		if (cookies != null) {
			var cookie = Arrays.stream(cookies).filter(i -> i.getName().equalsIgnoreCase(cookieName)).findFirst();
			token = cookie.orElse(new Cookie(cookieName, "")).getValue();
		}

		final URI uri = UriComponentsBuilder
			.fromUriString("http://127.0.0.1:9010")
			.path("/api/v1/queue/allowed")
			.queryParam("queue", queue)
			.queryParam("user_id", userId)
			.queryParam("token", token)
			.encode()
			.build()
			.toUri();

		final ResponseEntity<AllowedUserResponse> response = restTemplate.getForEntity(uri, AllowedUserResponse.class);
		if (response.getBody() == null || !response.getBody().allowed()) {
			// redirect to waiting room
			return "redirect:http://127.0.0.1:9010/waiting-room?user_id=%d&redirect_url=%s".formatted(
				userId, "http://127.0.0.1:9000?user_id=%d".formatted(userId));
		}
		// allowed to enter
		return "index";
	}

	public record AllowedUserResponse(Boolean allowed) {}
}
