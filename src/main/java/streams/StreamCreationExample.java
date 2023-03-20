package streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamCreationExample {
	public void createStreamFromList() {
		List<String> list = List.of("a", "b", "c");
		Stream<String> stream = list.stream();
	}
	public void createStreamFromArray() {
		String[] array = {"a", "b", "c"};
		Stream<String> stream = Arrays.stream(array);
	}
	public void createStreamFromElements() {
		Stream<String> stream = Stream.of("a", "b", "c");
	}
	public void createStreamFromBuilder() {
		Stream<String> stream = Stream.<String>builder()
		                              .add("a")
		                              .add("b")
		                              .add("c")
		                              .build();
	}
	public static void main(String[] args) {
		StreamCreationExample streamCreationExample = new StreamCreationExample();
		streamCreationExample.createStreamFromList();
		streamCreationExample.createStreamFromArray();
		streamCreationExample.createStreamFromElements();
		streamCreationExample.createStreamFromBuilder();
	}
}
