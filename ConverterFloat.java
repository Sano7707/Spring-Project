import java.util.Arrays;

public class ConverterFloat extends Converter {
    private int convertToInt(String binarySequence) {
        int result = 0;
        for (int i = 0; i < binarySequence.length(); i++) {
            if (binarySequence.charAt(i) == '1') {
                result += Math.pow(2, binarySequence.length() - 1 - i);
            }
        }
        return result;
    }

    @Override
    public double convertToDecimal(String binarySequence) {
        int dotIndex = binarySequence.indexOf(".");
        String integerPart = binarySequence.substring(0, dotIndex);
        String fractionalPart = binarySequence.substring(dotIndex + 1);
        int integer = convertToInt(integerPart);
        double fractional = 0;
        for (int i = 0; i < fractionalPart.length(); i++) {
            if (fractionalPart.charAt(i) == '1') {
                fractional += 1 / Math.pow(2, i + 1);
            }
        }
        return integer + fractional;
    }

    @Override
    public double evaluateDecimalValue(double[] frequencyAmplitudes) {
        double result = 0;
        for (int i = 0; i < frequencyAmplitudes.length; i++) {
            result += frequencyAmplitudes[i];
        }
        return result;
    }

    public static void main(String[] args) {
        ConverterFloat converter = new ConverterFloat();

        String binarySequence1 = "1010.1101";
        double decimalValue1 = converter.convertToDecimal(binarySequence1);
        System.out.println(binarySequence1 + " = " + decimalValue1);

        String binarySequence2 = "1111.0011";
        double decimalValue2 = converter.convertToDecimal(binarySequence2);
        System.out.println(binarySequence2 + " = " + decimalValue2);

        double[] frequencyAmplitudes = {1.2, 2.3, 3.4};
        double decimalValue3 = converter.evaluateDecimalValue(frequencyAmplitudes);
        System.out.println(Arrays.toString(frequencyAmplitudes) + " = " + decimalValue3);
    }

}
