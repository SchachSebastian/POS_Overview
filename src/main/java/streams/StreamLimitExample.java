package streams;

import java.util.List;
import java.util.stream.Stream;

public class StreamLimitExample {
	public static void main(String[] args) {
		List<String> list = List.of("a", "b", "c");
		Stream<String> stream = list.stream()
		                            .limit(2);
		stream.forEach(System.out::println);
		// output: a, b
	}
}
