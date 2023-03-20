package streams;

import java.util.List;
import java.util.stream.Stream;

public class StreamFilterExample {
	public static void main(String[] args) {
		List<String> list = List.of("a", "b", "c");
		Stream<String> stream = list.stream()
		                            .filter(s -> s.equals("a"));
		stream.forEach(System.out::println);
	}
}
