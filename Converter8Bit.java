public class Converter8Bit extends Converter {

    private final Spring[] springs;

    public Converter8Bit() {
        this.springs = new Spring[8];
        for (int i = 0; i < 8; i++) {
            this.springs[i] = new Spring(1.0);
        }
    }

    @Override
    public double convertToDecimal(String binarySequence) {
        int decimalValue = 0;
        int base = 1;

        for (int i = 0; i < binarySequence.length(); i++) {
            char bit = binarySequence.charAt(binarySequence.length() - 1 - i);
            if (bit == '1') {
                decimalValue += base;
            }
            base *= 2;
        }

        return decimalValue;
    }

    @Override
    public double evaluateDecimalValue(double[] frequencyAmplitudes) {
        double[] positions = new double[8];
        positions[0] = 0.0;

        for (int i = 1; i < 8; i++) {
            positions[i] = positions[i-1] + frequencyAmplitudes[i-1] / this.springs[i-1].getK();
        }

        double displacement = positions[7] - positions[0];

        return displacement * 256.0 / 2.0;
    }

    public static void main(String[] args) {
        Converter8Bit converter = new Converter8Bit();

        // Convert binary string "11001010" to decimal value
        double decimalValue = converter.convertToDecimal("11001010");
        System.out.println("Decimal value of binary string 11001010: " + decimalValue);

        // Evaluate decimal value of frequency amplitudes [0.0, 0.2, 0.4, 0.6, 0.8, 1.0, 0.6, 0.2]
        double[] frequencyAmplitudes = {0.0, 0.2, 0.4, 0.6, 0.8, 1.0, 0.6, 0.2};
        double decimalValue2 = converter.evaluateDecimalValue(frequencyAmplitudes);
        System.out.println("Decimal value of frequency amplitudes [0.0, 0.2, 0.4, 0.6, 0.8, 1.0, 0.6, 0.2]: " + decimalValue2);
    }

}
