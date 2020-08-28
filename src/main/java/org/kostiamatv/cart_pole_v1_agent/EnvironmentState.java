package org.kostiamatv.cart_pole_v1_agent;

public class EnvironmentState {
    private double x;
    private double xVel;
    private double theta;
    private double thetaVel;

    public EnvironmentState() {
    }

    public EnvironmentState(EnvironmentState other) {
        this.x = other.x;
        this.xVel = other.xVel;
        this.theta = other.theta;
        this.thetaVel = other.thetaVel;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getXVel() {
        return xVel;
    }

    public void setXVel(double xVel) {
        this.xVel = xVel;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getThetaVel() {
        return thetaVel;
    }

    public void setThetaVel(double thetaVel) {
        this.thetaVel = thetaVel;
    }

    public void update(double x, double xVel, double theta, double thetaVel){
        setX(x);
        setXVel(xVel);
        setTheta(theta);
        setThetaVel(thetaVel);
    }
}
