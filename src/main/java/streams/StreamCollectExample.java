package streams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamCollectExample {
	public static void main(String[] args) {
		List<String> list = List.of("a", "b", "c", "a");
		Set<String> set = list.stream()
		                      .map(String::toUpperCase).collect(Collectors.toSet());
		System.out.println(set); // [A, B, C]
		// note that the double a is not in the set, as set does not allow duplicates
	}
}
