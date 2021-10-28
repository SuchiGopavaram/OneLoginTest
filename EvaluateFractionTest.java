package com.suchi.maven.eclipse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.InvalidParameterException;

import org.junit.Test;

public class EvaluateFractionTest {
	
	EvaluateFraction evaluateFraction = new EvaluateFraction();
	
	@Test
	public void testAddTwoFractions() {
		String expression = "2_3/8 + 9/8";
		String expectedValue = "3_1/2";
		String actualValue = evaluateFraction.evaluate(expression);
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testSubtractTwoFractions() {
		String expression = "5_1/2 - 3/7";
		String expectedValue = "5_1/14";
		String actualValue = evaluateFraction.evaluate(expression);
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testMultiplyTwoFractions() {
		String expression = "1/2 * 3_3/4";
		String expectedValue = "1_7/8";
		String actualValue = evaluateFraction.evaluate(expression);
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testDivideTwoFractions() {
		String expression = "1/3 / 9/8";
		String expectedValue = "8/27";
		String actualValue = evaluateFraction.evaluate(expression);
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testAddTwoWholeNumbers() {
		String expression = "2 + 3";
		String expectedValue = "5";
		String actualValue = evaluateFraction.evaluate(expression);
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testSubtractTwoNegativeFractions() {
		String expression = "-1/2 - 1/7";
		String expectedValue = "-9/14";
		String actualValue = evaluateFraction.evaluate(expression);
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testDivideByZero() {
		String expression = "1/0 / 1/7";
		String expectedValue = "";
		String actualValue = evaluateFraction.evaluate(expression);
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testAddForInvalidInput() {
		String expression = "5_/2 * 3/7";
		String expectedValue = "";
		String actualValue = evaluateFraction.evaluate(expression);
		assertEquals(expectedValue, actualValue);
	}

}
