public class ConverterInt extends Converter {
    private static final double SPRING_STIFFNESS = 1.0;

    @Override
    public Spring[] binaryToSprings(String binary) {
        Spring[] springs = new Spring[binary.length()];
        for (int i = 0; i < binary.length(); i++) {
            char c = binary.charAt(i);
            double k = c == '0' ? SPRING_STIFFNESS : 2 * SPRING_STIFFNESS;
            springs[i] = new Spring(k);
        }
        return springs;
    }

    @Override
    public double evaluateDecimal(String binary) {
        double decimal = 0;
        int exponent = 0;
        for (int i = binary.length() - 1; i >= 0; i--) {
            char c = binary.charAt(i);
            if (c == '1') {
                decimal += Math.pow(2, exponent);
            }
            exponent++;
        }
        return decimal;
    }

    public static void main(String[] args) {
        ConverterInt converter = new ConverterInt();

        // Test case 1: Binary 1010 should equal decimal 10
        String binary1 = "1010";
        double decimal1 = converter.evaluateDecimal(binary1);
        System.out.println(binary1 + " = " + decimal1);

        // Test case 2: Binary 110110 should equal decimal 54
        String binary2 = "110110";
        double decimal2 = converter.evaluateDecimal(binary2);
        System.out.println(binary2 + " = " + decimal2);

        // Test case 3: Binary 11111111 should equal decimal 255
        String binary3 = "11111111";
        double decimal3 = converter.evaluateDecimal(binary3);
        System.out.println(binary3 + " = " + decimal3);

        // Test case 4: Binary 1000000000 should equal decimal 512
        String binary4 = "1000000000";
        double decimal4 = converter.evaluateDecimal(binary4);
        System.out.println(binary4 + " = " + decimal4);
    }

}