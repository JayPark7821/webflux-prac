package kr.jay.reactorpattern;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Main
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/04
 */
@Slf4j
public class Main {

	public static void main(String[] args) {
		log.info("start reactor pattern");
		List<EventLoop> eventLoops = List.of(new EventLoop(8080), new EventLoop(8080), new EventLoop(8080));
		eventLoops.forEach(EventLoop::run);
		log.info("end reactor pattern");

	}
}

