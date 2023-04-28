public class Spring {
    private double k = 1.0;

    public Spring() {}

    public Spring(double k) {
        this.k = k;
    }

    public double getStiffness() {
        return k;
    }

    void setStiffness(double k) {
        this.k = k;
    }

    public double[] move(double t, double dt, double x0, double v0) {
        int numSteps = (int) (t / dt);
        double[] x = new double[numSteps];
        double v = v0;
        double xPrev = x0;
        for (int i = 0; i < numSteps; i++) {
            double acceleration = -k * xPrev;
            double xNew = xPrev + v * dt;
            v = v + acceleration * dt;
            x[i] = xPrev;
            xPrev = xNew;
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
        int numSteps = (int) ((t1 - t0) / dt);
        double[] x = new double[numSteps];
        double v = v0;
        double xPrev = x0;
        for (int i = 0; i < numSteps; i++) {
            double force = -k * xPrev;
            double acceleration = force / m;
            double xNew = xPrev + v * dt;
            v = v + acceleration * dt;
            x[i] = xPrev;
            xPrev = xNew;
        }
        return x;
    }

    public Spring inSeries(Spring that) {
        double k = this.k + that.k;
        return new Spring(k);
    }

    public Spring inParallel(Spring that) {
        double k = (this.k * that.k)/(this.k + that.k);
        return new Spring(k);
    }

}
