package streams;

import java.util.List;

public class StreamCountExample {
	public static void main(String[] args) {
		long count = List.of("a", "b", "c")
		                 .stream()
		                 .count();
		System.out.println(count);
	}
}
