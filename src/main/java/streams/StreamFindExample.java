package streams;

import java.util.List;

public class StreamFindExample {
	public static void main(String[] args) {
		List<String> list = List.of("a", "b", "c");
		String result = list.stream()
		                    .findAny()
		                    .orElse("d");
		System.out.println(result);
		List<String> list2 = List.of();
		String result2 = list2.stream()
		                      .findAny()
		                      .orElse("d");
		System.out.println(result2);
	}
}
