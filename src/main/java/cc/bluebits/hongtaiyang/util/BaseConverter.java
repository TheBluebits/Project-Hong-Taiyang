package cc.bluebits.hongtaiyang.util;

import java.util.ArrayList;

public class BaseConverter {
    public static int[] convertDecimalToBaseNDigits(int value, int base, int minDigits) {
        if(value == 0) return new int[minDigits];
        
        int neededDigits = 1 + (int) Math.floor(Math.log10(value) / Math.log10(base));
        int nDigits = Integer.max(minDigits, neededDigits);
        int[] digits = new int[nDigits];
        
        int idx = nDigits - 1;
        while(value != 0 && idx >= 0) {
            digits[idx--] = value % base;
            value = value / base;
        }
        
        return digits;
    }
    
    public static int convertBaseNDigitsToDecimal(int[] digits, int base) {
        int value = 0;
        if(digits.length == 0) return value;
        
        for(int i = 0; i < digits.length; i++) {
            value += (int) (digits[i] * Math.pow(base, digits.length - i - 1));
        }
        
        return value;
    }
}
