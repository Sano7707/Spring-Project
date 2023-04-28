public class ConverterFloat extends Converter {
    private String intPart;
    private String fracPart;

    public ConverterFloat(String intPart, String fracPart) {
        this.intPart = intPart;
        this.fracPart = fracPart;
    }

    @Override
    public Spring[] binaryToSprings(String binary) {
        // implementation not relevant for this class
        return null;
    }

    @Override
    public double evaluateDecimal(String binary) {
        double intVal = 0.0;
        double fracVal = 0.0;

        for (int i = 0; i < intPart.length(); i++) {
            if (intPart.charAt(i) == '1') {
                intVal += Math.pow(2, intPart.length() - i - 1);
            }
        }

        for (int i = 0; i < fracPart.length(); i++) {
            if (fracPart.charAt(i) == '1') {
                fracVal += Math.pow(2, -(i + 1));
            }
        }

        return intVal + fracVal;
    }
    public static void main(String[] args) {
        // Test case 1
        String intPart1 = "1011";
        String fracPart1 = "1101";
        ConverterFloat converter1 = new ConverterFloat(intPart1, fracPart1);
        double result1 = converter1.evaluateDecimal("");
        System.out.println("Test case 1:");
        System.out.println("Binary: " + intPart1 + "." + fracPart1);
        System.out.println("Decimal: " + result1);
        System.out.println();

        // Test case 2
        String intPart2 = "111001";
        String fracPart2 = "10011";
        ConverterFloat converter2 = new ConverterFloat(intPart2, fracPart2);
        double result2 = converter2.evaluateDecimal("");
        System.out.println("Test case 2:");
        System.out.println("Binary: " + intPart2 + "." + fracPart2);
        System.out.println("Decimal: " + result2);
        System.out.println();

        // Test case 3
        String intPart3 = "10";
        String fracPart3 = "01";
        ConverterFloat converter3 = new ConverterFloat(intPart3, fracPart3);
        double result3 = converter3.evaluateDecimal("");
        System.out.println("Test case 3:");
        System.out.println("Binary: " + intPart3 + "." + fracPart3);
        System.out.println("Decimal: " + result3);
        System.out.println();
    }


}
