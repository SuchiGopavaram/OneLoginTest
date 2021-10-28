import java.security.InvalidParameterException;
import java.util.Scanner;

public class EvaluateFraction {
	
	/* This method takes the input equation and return a fraction for valid inputs
	 and empty string for invalid inputs. */
	public String evaluate(String expression) {
		String result = "";
		try {
			if(expression == null || expression.length() == 0) {
				throw new InvalidParameterException("Input is either null or empty. Please check you input.");
			}
			expression = expression.trim();
			String[] chars = expression.split("\\s+");
			
			// As the input has only 2 operands and 1 operator.
			// At any point of time there should only be 3 strings in the array.
			if(chars.length < 3 || chars.length > 3) {
				throw new InvalidParameterException("Input " + expression + " is invalid. Please check your input.");
			}
			Fraction fraction1 = getFraction(chars[0]);
			String operand = chars[1];
			Fraction fraction2 = getFraction(chars[2]);
			
			// Check for divide by zero.
			if(fraction1.denominator == 0 || fraction2.denominator == 0) {
				throw new ArithmeticException("Divide by zero is not allowed.");
			}
			
			int numerator = 0;
			int denominator = 0;
			
			if(operand.equals("+")) {
				Fraction fraction = add(fraction1, fraction2);
				result = getFractionAsString(fraction.numerator, fraction.denominator);
			}
			else if(operand.equals("-")) {
				Fraction fraction = subtract(fraction1, fraction2);
				result = getFractionAsString(fraction.numerator, fraction.denominator);
			}
			else if(operand.equals("*")) {
				numerator = fraction1.numerator * fraction2.numerator;
				denominator = fraction1.denominator * fraction2.denominator;
				result = getFractionAsString(numerator, denominator);
			} else if(operand.equals("/")) {
				numerator = fraction1.numerator * fraction2.denominator;
				denominator = fraction1.denominator * fraction2.numerator;
				result = getFractionAsString(numerator, denominator);
			}
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			System.out.println("NumberFormatException. Check your input.");
		}
		catch(ArithmeticException e) {
			e.printStackTrace();
			System.out.println("Divide by zero is not allowed.");
		}
		catch(InvalidParameterException e) {
			e.printStackTrace();
			System.out.println("Input in invalid. Please check your input.");
		}
		catch(Exception e) {
			System.out.println("Error occured while evaluation fraction: " + expression);
			e.printStackTrace();
		}
		return result;
	}
	
	private Fraction add(Fraction fraction1, Fraction fraction2) {
		int numerator = 0;
		int lcmDenominator = 0;
		
		// for fractions with different denominators
		if(fraction1.denominator != fraction2.denominator) {
			lcmDenominator = getLCM(fraction1.denominator, fraction2.denominator);
			int aMultiplier = lcmDenominator / fraction1.denominator;
			int bMultiplier = lcmDenominator / fraction2.denominator;
			numerator = (fraction1.numerator * aMultiplier) + 
					(fraction2.numerator * bMultiplier);
			return new Fraction(numerator, lcmDenominator);
		} else {
			numerator = fraction1.numerator + fraction2.numerator;
			lcmDenominator = fraction1.denominator;
			return new Fraction(numerator, lcmDenominator);
		}
	}
	
	private Fraction subtract(Fraction fraction1, Fraction fraction2) {
		fraction2.setNumerator(fraction2.getNumerator() * -1);
		return add(fraction1, fraction2);
	}
	
	/* This method will reduce the input fraction to its simple form. */
	private Fraction simplifyFraction(int numerator, int denominator) {
		int gcd = getGCD(numerator, denominator);
		if(gcd > 1) {
			numerator /= gcd;
			denominator /= gcd;
		}
		return new Fraction(numerator, denominator);
	}
	
	/* This method will provide the result in string format. */
	private String getFractionAsString(int numerator, int denominator) {
		StringBuilder mixedNumber = new StringBuilder();
		int top = numerator;
		if(Math.abs(numerator) > denominator) {
			int wholeNumber = numerator / denominator;
			mixedNumber.append(wholeNumber);
			top = numerator % denominator;
			if(top == 0) {
				return mixedNumber.toString();
			}
			mixedNumber.append("_");
		}
		Fraction fraction = simplifyFraction(top, denominator);
		mixedNumber.append(fraction.numerator);
		mixedNumber.append("/");
		mixedNumber.append(fraction.denominator);
		return mixedNumber.toString();
	}
	
	/* This method return Least common multiple*/
	private int getLCM(int num1, int num2) {
		int gcd = getGCD(num1, num2);
		return (num1 * num2) / gcd;
	}
	
	/* This method return Greatest common divisor*/
	private int getGCD(int num1, int num2) {
		while(num1 != num2) {
			if(num1 > num2) {
				num1 = num1 - num2;
			} else {
				num2 = num2 - num1;
			}
		}
		return num2;
	}
	
	/* This method converts fraction from string format to Fraction object. */
	private Fraction getFraction(String word) {
		int underscoreIndex = word.indexOf('_');
		int divisionIndex = word.indexOf('/');
		
		int wholeNumber = 0;
		int numerator = 0;
		int denominator = 0;
		
		// check if mixed number is present;
		if(underscoreIndex >= 0) {
			wholeNumber = Integer.parseInt(word.substring(0, underscoreIndex));
		}
		
		// check if fraction is present. If not gather whole number.
		if(divisionIndex < 0) {
			wholeNumber = Integer.parseInt(word);
			denominator = 1;
		} else {
			String numeratorString = word.substring(underscoreIndex + 1, divisionIndex);
			String denominatorString = word.substring(divisionIndex + 1, word.length());
			// check for invalid fraction like (3_/1) or (9/).
			if(numeratorString == null || numeratorString.isEmpty() || 
					denominatorString == null || denominatorString.isEmpty()) {
				throw new InvalidParameterException("Fraction " + word + " is invalid. Please check your input.");
			}
			numerator = Integer.parseInt(numeratorString);
			denominator = Integer.parseInt(word.substring(divisionIndex + 1, word.length()));
		}
		if(wholeNumber < 0) { numerator *= -1; }
		numerator += (wholeNumber * denominator);
		return new Fraction(numerator, denominator);
	}
	
	public static void main(String[] args) {
		String input;
		EvaluateFraction ef = new EvaluateFraction();
		Scanner scanner = new Scanner(System.in);
		System.out.print("?");
		while((input = scanner.nextLine()) != null) {
			String result = ef.evaluate(input);
			System.out.println("= " + result);
			System.out.print("?");
		}
		
		String[][] testInputs = new String[][] {
			new String[] {"2_3/8 + 9/8", "3_1/2"}, // addition
			new String[] {"1/3 / 9/8", "8/27"}, // division
			new String[] {"1/2 * 3_3/4", "1_7/8"}, // multiply
			new String[] {"5_1/2 - 3/7", "5_1/14"}, // subtract
			new String[] {"      1/2      *      3_3/4", "1_7/8"}, // extra space
			new String[] {"2 * 7", "14"}, // whole numbers
			new String[] {"-1/2 - 1/7", "-9/14"}, // negative numbers
			new String[] {null, ""}, // null input
			new String[] {"", ""}, // empty input
			new String[] {"5_1/2 - 3/", ""}, // invalid input
			new String[] {"5_/2 * 3/7", ""}, // invalid input
			new String[] {"1/0 - 1/7", ""} // invalid input
			
		};
	}

}

class Fraction {
	int numerator;
	int denominator;
	
	public Fraction(int numerator, int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	public int getNumerator() {
		return numerator;
	}

	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}

	public int getDenominator() {
		return denominator;
	}

	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}
	
}
