package kr.jay.springwebfluxprac2;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringWebfluxPrac2Application implements ApplicationRunner {

	public static void main(String[] args) {
		// BlockHound.install();
		SpringApplication.run(SpringWebfluxPrac2Application.class, args);
	}

	@Override
	public void run(final ApplicationArguments args) throws Exception {
		// Mono.delay(Duration.ofSeconds(1))
		// 	.doOnNext(it -> {
		// 		try {
		// 			Thread.sleep(100);
		// 		} catch (InterruptedException e) {
		// 			throw new RuntimeException(e);
		// 		}
		// 	})
		// 	.subscribe();
	}
}
