package kr.jay.reactorprac2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class Main {

	public static void main(String[] args) {
		final Publisher publisher = new Publisher();
		publisher.startFlux()
			.subscribe(System.out::println);

		publisher.startMono()
			.subscribe(System.out::println);

		publisher.startMono2()
			.subscribe(System.out::println);
	}

}
