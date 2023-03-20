package streams;

import java.util.List;
import java.util.stream.Stream;

public class StreamSortedExample {
	public static void main(String[] args) {
		List<String> list = List.of("c", "b", "a");
		Stream<String> stream = list.stream()
		                            .sorted();
		stream.forEach(System.out::println);
	}
}
