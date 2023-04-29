public class FT {
    private double[] real;
    private double[] imag;
    private int N;
    private int pow;

    public FT(double[] coordinates) {
        N = coordinates.length;
        pow = 0;
        while (N/2>0) {
            if (N%2==0) { // N should be even
                pow++;
                N /= 2;
            } else {
                throw new IllegalArgumentException("Number of points in this FFT implementation must be even.");
            }
        }
        N /= 2;
        real = new double[N];
        imag = new double[N];
        for (int i = 0; i < N; i++) {
            real[i] = coordinates[2*i];
            imag[i] = coordinates[2*i+1];
        }
        transform();
    }

    public double[] getAmplitudes() {
        double[] amplitudes = new double[N];
        for (int i = 0; i < N; i++) {
            amplitudes[i] = Math.sqrt(real[i]*real[i] + imag[i]*imag[i]);
        }
        return amplitudes;
    }

    public double[] getFrequencies(double samplingRate) {
        double[] frequencies = new double[N];
        for (int i = 0; i < N; i++) {
            frequencies[i] = i * samplingRate / N;
        }
        return frequencies;
    }

    private void transform() {
        int N2 = N/2;
        int jj = N2;
        // rearrange input according to bit reversal
        for (int i = 1; i < N-1; i++) {
            if (i < jj) {
                double tempRe = real[jj];
                double tempIm = imag[jj];
                real[jj] = real[i];
                imag[jj] = imag[i];
                real[i] = tempRe;
                imag[i] = tempIm;
            }
            int k = N2;
            while (k <= jj) {
                jj = jj - k;
                k = k / 2;
            }
            jj = jj + k;
        }
        jj = 1;
        for (int p = 1; p <= pow; p++) {
            int inc = 2 * jj;
            double wp1 = 1, wp2 = 0;
            double theta = Math.PI / jj;
            double cos = Math.cos(theta), sin = -Math.sin(theta);
            for (int j = 0; j < jj; j++) {
                for (int i = j; i < N; i += inc) {
                    // calculate the transform of 2^p
                    int ip = i + jj;
                    double tempRe = wp1 * real[ip] - wp2 * imag[ip];
                    double tempIm = wp2 * real[ip] + wp1 * imag[ip];
                    real[ip] = real[i] - tempRe;
                    imag[ip] = imag[i] - tempIm;
                    real[i] = real[i] + tempRe;
                    imag[i] = imag[i] + tempIm;
                }
                double temp = wp1;
                wp1 = wp1 * cos - wp2 * sin;
                wp2 = temp * sin + wp2 * cos;
            }
            jj = inc;
        }
    }
}
