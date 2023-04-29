public class Spring {
    private double k = 1.0; // default stiffness

    public Spring() {
    }

    public Spring(double k) {
        this.k = k;
    }

    public double getStiffness() {
        return k;
    }

    private void setStiffness(double k) {
        this.k = k;
    }

    public double[] move(double t, double dt, double x0, double v0) {
        double omega = Math.sqrt(k);
        int n = (int) Math.ceil(t / dt);
        double[] x = new double[n];
        x[0] = x0;
        double v = v0;
        for (int i = 1; i < n; i++) {
            double phase = omega * i * dt;
            x[i] = x0 * Math.cos(phase) + v0 / omega * Math.sin(phase);
            v = -omega * x0 * Math.sin(phase) + v0 * Math.cos(phase);
            x0 = x[i];
        }
        return x;
    }

    public double[] move(double t, double dt, double x0) {
        return move(t, dt, x0, 0.0);
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0) {
        return move(t0,t1,dt,x0,v0,1.0);
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0, double m) {
        double omega = Math.sqrt(k / m);
        int n = (int) Math.ceil((t1 - t0) / dt);
        double[] x = new double[n];
        x[0] = x0;
        double v = v0;
        double t = t0;
        for (int i = 1; i < n; i++) {
            double phase = omega * (t + dt) - omega * t0;
            x[i] = x0 * Math.cos(phase) + v0 / omega * Math.sin(phase);
            v = -omega * x0 * Math.sin(phase) + v0 * Math.cos(phase);
            x0 = x[i];
            t += dt;
        }
        return x;
    }
    public Spring inSeries(Spring that) {
        double k = (this.k  * that.k)/(this.k + that.k);
        return new Spring(k);
    }

    public Spring inParallel(Spring that) {
        double k = this.k + that.k;
        return new Spring(k);
    }

}
