public abstract class Converter {
    public abstract double convertToDecimal(String binarySequence);

    public double computeOscillations(Spring[] springs) {
        double[] positions = new double[springs.length + 1];
        for (int i = 0; i < springs.length; i++) {
            positions[i + 1] = positions[i] + 1 / springs[i].getK();
        }
        return 2 * Math.PI / (positions[positions.length - 1] - positions[0]);
    }

    public double[] computeFrequencyAmplitudes(Spring[] springs) {
        int n = springs.length;
        double[] amplitudes = new double[n];
        double f0 = computeOscillations(springs);
        double[] x = new double[n + 1];
        for (int i = 0; i < n; i++) {
            x[i + 1] = x[i] + 1 / springs[i].getK();
        }
        double[] y = new double[n + 1];
        y[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            y[i] = y[i + 1] * Math.exp(-2 * Math.PI * f0 * (x[i + 1] - x[i]));
        }
        double[] z = new double[n + 1];
        z[0] = 1;
        for (int i = 1; i <= n; i++) {
            z[i] = z[i - 1] * Math.exp(-2 * Math.PI * f0 * (x[i] - x[i - 1]));
        }
        for (int i = 0; i < n; i++) {
            amplitudes[i] = 2 * Math.abs(z[i] * y[i + 1]) / (springs[i].getK() * (z[i] + y[i + 1]));
        }
        return amplitudes;
    }

    public abstract double evaluateDecimalValue(double[] frequencyAmplitudes);
}
