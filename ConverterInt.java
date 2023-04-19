public class ConverterInt extends Converter {

    @Override
    public double convertToDecimal(String binarySequence) {
        int decimalValue = 0;
        for (int i = 0; i < binarySequence.length(); i++) {
            char bit = binarySequence.charAt(i);
            if (bit == '1') {
                decimalValue += Math.pow(2, binarySequence.length() - i - 1);
            }
        }
        return (double) decimalValue;
    }

    @Override
    public double evaluateDecimalValue(double[] frequencyAmplitudes) {
        double decimalValue = 0;
        for (int i = 0; i < frequencyAmplitudes.length; i++) {
            decimalValue += frequencyAmplitudes[i] * Math.sin((i + 1) * Math.PI / (frequencyAmplitudes.length + 1));
        }
        return decimalValue;
    }

    public static void main(String[] args) {
        ConverterInt converter = new ConverterInt();

        // Test the convertToDecimal() method
        String binarySequence = "1010";
        double decimalValue = converter.convertToDecimal(binarySequence);
        System.out.println(binarySequence + " in decimal is " + decimalValue);

        // Test the evaluateDecimalValue() method
        Spring[] springs = {new Spring(1.0), new Spring(2.0), new Spring(3.0)};
        double[] frequencyAmplitudes = converter.computeFrequencyAmplitudes(springs);
        double evaluatedDecimalValue = converter.evaluateDecimalValue(frequencyAmplitudes);
        System.out.println("Evaluated decimal value: " + evaluatedDecimalValue);
    }

}
