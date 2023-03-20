package streams;

import java.util.List;
import java.util.stream.Stream;

public class StreamForEachExample {
	public static void main(String[] args) {
		List<String> list = List.of("a", "b", "c");
		Stream<String> stream = list.stream();
		stream.forEach(System.out::println);
	}
}
