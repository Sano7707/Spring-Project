public class FT {
    private double[] real; // Real part of the complex numbers
    private double[] imag; // Imaginary part of the complex numbers
    private int n; // Number of time samples

    public FT(double[] x) {
        n = x.length;
        real = new double[n];
        imag = new double[n];
        for (int k = 0; k < n; k++) {
            for (int t = 0; t < n; t++) {
                double angle = 2 * Math.PI * t * k / n;
                real[k] += x[t] * Math.cos(angle);
                imag[k] -= x[t] * Math.sin(angle);
            }
        }
    }

    public double[] getAmplitudes() {
        double[] amplitudes = new double[n / 2];
        for (int k = 1; k < n / 2; k++) {
            amplitudes[k] = Math.sqrt(real[k] * real[k] + imag[k] * imag[k]);
        }
        return amplitudes;
    }

    public double[] getFrequencies(double dt) {
        double[] frequencies = new double[n / 2];
        for (int k = 1; k < n / 2; k++) {
            frequencies[k] = k / (n * dt);
        }
        return frequencies;
    }

    public void print() {
        double[] frequencies = getFrequencies(1.0);
        double[] amplitudes = getAmplitudes();
        for (int k = 1; k < n / 2; k++) {
            System.out.printf("Amplitude of frequency %.2f Hz: %.2f\n", frequencies[k], amplitudes[k]);
        }
    }
}
