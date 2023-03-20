package streams;

import java.util.List;
import java.util.stream.Stream;

public class StreamDistinctExample {
	public static void main(String[] args) {
		List<String> list = List.of("a", "b", "a");
		Stream<String> stream = list.stream()
		                            .distinct();
		stream.forEach(System.out::println);
	}
}
