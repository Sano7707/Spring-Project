public class Converter8Bit extends Converter {
    private final static int NUM_SPRINGS = 8;

    public Converter8Bit() {
        // Initialize the system of springs with unit stiffness
        Spring[] systemOfSprings = new Spring[NUM_SPRINGS];
        for (int i = 0; i < NUM_SPRINGS; i++) {
            systemOfSprings[i] = new Spring();
        }
        setSystemOfSprings(systemOfSprings);
    }

    @Override
    public Spring[] binaryToSprings(String binary) {
        if (binary.length() != NUM_SPRINGS) {
            throw new IllegalArgumentException("Binary string must be of length " + NUM_SPRINGS);
        }

        Spring[] systemOfSprings = new Spring[NUM_SPRINGS];
        for (int i = 0; i < NUM_SPRINGS; i++) {
            systemOfSprings[i] = new Spring(binary.charAt(i) == '1' ? 1.0 : 0.0);
        }

        return systemOfSprings;
    }

    @Override
    public double evaluateDecimal(String binary) {
        if (binary.length() != NUM_SPRINGS) {
            throw new IllegalArgumentException("Binary string must be of length " + NUM_SPRINGS);
        }

        int decimal = 0;
        for (int i = 0; i < NUM_SPRINGS; i++) {
            decimal += (binary.charAt(i) - '0') * Math.pow(2, NUM_SPRINGS - 1 - i);
        }

        return decimal;
    }
    public static void main(String[] args) {
        Converter8Bit converter = new Converter8Bit();

        // Test binaryToSprings method
        Spring[] springs = converter.binaryToSprings("01010101");
        for (Spring spring : springs) {
            System.out.println(spring.getStiffness());
        }

        // Test evaluateDecimal method
        double decimal = converter.evaluateDecimal("11110000");
        System.out.println(decimal);
    }

}
