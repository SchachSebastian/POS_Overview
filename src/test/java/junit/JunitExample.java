package junit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JunitExample {
	private static String forAllTests;
	private static List<String> forEveryTest;
	@BeforeAll
	public static void setupAll() {
		forAllTests = "forAllTests"; // will only be executed once
	}
	@BeforeEach
	public void setupEach() {
		forEveryTest = List.of("a", "b", "c"); // will be executed before every test
	}
	@Test
	public void test1() {
		System.out.println("forAllTests: " + forAllTests);
		System.out.println("forEveryTest: " + forEveryTest);
		// execute the function to be tested
		forEveryTest = JunitListManipulation.setFirstToX(forEveryTest);
		forAllTests = "test1Executed";
		// assert the result
		assertEquals("x", forEveryTest.get(0));
		System.out.println("--------------------");
		System.out.println("forEveryTest: " + forEveryTest);
	}
	@Test
	public void test2() {
		System.out.println("forAllTests: " + forAllTests);
		System.out.println("forEveryTest: " + forEveryTest);
		// execute the function to be tested
		forEveryTest = JunitListManipulation.setSecondToX(forEveryTest);
		// assert the result
		assertEquals("x", forEveryTest.get(1));
		assertTrue(forEveryTest.get(0).equals("a")); // test if the first element is still "a"
		System.out.println("--------------------");
		System.out.println("forEveryTest: " + forEveryTest);
	}
}
