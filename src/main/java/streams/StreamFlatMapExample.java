package streams;

import java.util.List;

public class StreamFlatMapExample {
	public static void main(String[] args) {
		// example of stream flatMap
		List<String> list1 = List.of("a", "b", "c");
		List<String> list2 = List.of("d", "e", "f");
		List<String> list3 = List.of("g", "h", "i");
		List<List<String>> listOfLists = List.of(list1, list2, list3);
		List<String> flattedList = listOfLists.stream()
		                                      .flatMap(list -> list.stream()).toList();
		System.out.println(flattedList);
	}
}
