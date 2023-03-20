package streams;

import java.util.List;

public class StreamMinMaxExample {
	public static void main(String[] args) {
		int min = List.of(1, 2, 3)
		              .stream()
		              .min(Integer::compare)
		              .get();
		System.out.println(min);
		int max = List.of(1, 2, 3)
		              .stream()
		              .max(Integer::compare)
		              .get();
		System.out.println(max);
	}
}
