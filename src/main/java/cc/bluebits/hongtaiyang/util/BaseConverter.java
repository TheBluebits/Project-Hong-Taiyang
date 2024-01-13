package cc.bluebits.hongtaiyang.util;

/**
 * Utility class for converting between decimal and base N numbers.
 */
public class BaseConverter {
	/**
	 * Convert a decimal number to a base N number
	 * @param value The decimal number to convert
	 * @param base The base to convert to
	 * @param minDigits The minimum number of digits to return. If the number of digits in the converted number is less than this value, the number will be padded with leading zeros
	 * @return An array of integers representing the digits of the converted number
	 */
	public static int[] convertDecimalToBaseNDigits(int value, int base, int minDigits) {
		if (value == 0) return new int[minDigits];

		int neededDigits = 1 + (int) Math.floor(Math.log10(value) / Math.log10(base));
		int nDigits = Integer.max(minDigits, neededDigits);
		int[] digits = new int[nDigits];

		int idx = nDigits - 1;
		while (value != 0 && idx >= 0) {
			digits[idx--] = value % base;
			value = value / base;
		}

		return digits;
	}

	/**
	 * Convert a base N number to a decimal number
	 * @param digits An array of integers representing the digits of the base N number
	 * @param base The base of the number
	 * @return The decimal value of the base N number
	 */
	public static int convertBaseNDigitsToDecimal(int[] digits, int base) {
		int value = 0;
		if (digits.length == 0) return value;

		for (int i = 0; i < digits.length; i++) {
			value += (int) (digits[i] * Math.pow(base, digits.length - i - 1));
		}

		return value;
	}
}
