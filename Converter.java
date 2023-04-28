public abstract class Converter {
    private Spring[] systemOfSprings;

    // Abstract method for converting binary representation to system of springs
    public abstract Spring[] binaryToSprings(String binary);

    // Method for computing oscillations of unit mass on the spring system
    public double[] computeOscillations(double t, double dt, double x0, double v0) {
        double[] coords = new double[(int) (t / dt)];
        double x = x0;
        double v = v0;
        for (int i = 0; i < coords.length; i++) {
            double a = 0;
            for (Spring spring : systemOfSprings) {
                a += (-spring.getStiffness() * x);
            }
            double dv = a * dt;
            v += dv;
            double dx = v * dt;
            x += dx;
            coords[i] = x;
        }
        return coords;
    }

    // Method for computing frequency amplitudes of the oscillations
    public double[] computeAmplitudes(double[] coords, double dt) {
        FT ft = new FT(coords);
        double[] amplitudes = ft.getAmplitudes();
        double[] frequencies = ft.getFrequencies(dt);
        double[] result = new double[amplitudes.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = amplitudes[i] / (frequencies[i] * 2 * Math.PI);
        }
        return result;
    }

    // Abstract method for evaluating decimal value of binary sequence
    public abstract double evaluateDecimal(String binary);

    // Method for setting the system of springs
    protected void setSystemOfSprings(Spring[] systemOfSprings) {
        this.systemOfSprings = systemOfSprings;
    }
}
