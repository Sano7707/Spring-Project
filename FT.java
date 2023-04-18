import java.util.Arrays;

//Is this fine or add something?
public class FT {
    private int N;          // number of sample points
    private double[] x;     // array of sample points
    private double[] c;     // array of Fourier coefficients

    public FT(double[] x) {
        this.N = x.length;
        this.x = Arrays.copyOf(x, N);
        this.c = new double[N];
    }

    // Perform the Fourier transform
    public void transform() {
        for (int k = 0; k < N; k++) {
            double sumRe = 0.0;
            double sumIm = 0.0;
            for (int n = 0; n < N; n++) {
                double angle = 2 * Math.PI * k * n / N;
                sumRe += x[n] * Math.cos(angle);
                sumIm -= x[n] * Math.sin(angle);
            }
            c[k] = Math.sqrt(sumRe * sumRe + sumIm * sumIm) / N;
        }
    }

    // Get the array of Fourier coefficients
    public double[] getC() {
        return c;
    }
}
