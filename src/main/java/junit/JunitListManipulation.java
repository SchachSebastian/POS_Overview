package junit;

import java.util.ArrayList;
import java.util.List;

public class JunitListManipulation {
	public static List<String> setFirstToX(List<String> list) {
		List<String> newList = new ArrayList<>(list);
		newList.set(0, "x");
		return newList;
	}
	public static List<String> setSecondToX(List<String> forEveryTest) {
		List<String> newList = new ArrayList<>(forEveryTest);
		newList.set(1, "x");
		return newList;
	}
}
