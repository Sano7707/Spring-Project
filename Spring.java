public class Spring {

    private double k = 1;

    public Spring(){

    }

    public Spring(double k){
        this.setK(k);
    }

    public double getK() {
        return k;
    }

    private void setK(double k) {
        this.k = k;
    }

    private double[] positionsCalculator(double dt, int n, double[] x, double[] v) {
        for (int i = 1; i < n; i++) {
            double a = -this.k * x[i-1];
            v[i] = v[i-1] + a * dt;
            x[i] = x[i-1] + v[i] * dt;
        }

        return x;
    }

    private void positionsCalculator(double dt, int n, double[] x, double[] v, double m) {
        for (int i = 1; i < n; i++) {
            double a = -this.k * x[i-1] / m;
            v[i] = v[i-1] + a * dt;
            x[i] = x[i-1] + v[i] * dt;
        }
    }

    public double[] move(double t, double dt, double x0, double v0) {
        int n = (int) Math.ceil(t / dt);
        double[] x = new double[n];
        double[] v = new double[n];
        x[0] = x0;
        v[0] = v0;

        return positionsCalculator(dt, n, x, v);
    }

    public double[] move(double t, double dt, double x0) {
        int n = (int) Math.ceil(t / dt);
        double[] x = new double[n];
        double[] v = new double[n];
        x[0] = x0;
        v[0] = 0;

        return positionsCalculator(dt, n, x, v);
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0) {
        int n = (int) Math.ceil((t1 - t0) / dt);
        double[] x = new double[n];
        double[] v = new double[n];

        x[0] = x0;
        v[0] = v0;

        positionsCalculator(dt, n, x, v);

        return x;
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0, double m) {
        int n = (int) Math.ceil((t1 - t0) / dt);
        double[] x = new double[n];
        double[] v = new double[n];

        x[0] = x0;
        v[0] = v0;

        positionsCalculator(dt, n, x, v, m);

        return x;
    }

    public Spring inSeries(Spring that) {
        double k = this.k + that.k;
        return new Spring(k);
    }

    public Spring inParallel(Spring that) {
        double k = 1.0 / (1.0/this.k + 1.0/that.k);
        return new Spring(k);
    }

}
