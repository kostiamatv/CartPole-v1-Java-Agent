package org.kostiamatv.cart_pole_v1_agent;

public class CartPoleEnvironmentBuilder {
    private double gravity = 9.8;
    private double massCart = 1.0;
    private double massPole = 0.1;
    private double length = 0.5;
    private double forceMag = 10.0;
    private double tau = 0.02;
    private String kinematicsIntegrator = "euler";
    private double thetaThresholdDegrees = 12;
    private double thetaThresholdRadians = 12 * 2 * Math.PI / 360;
    private double xThreshold = 2.4;

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public double getMassCart() {
        return massCart;
    }

    public void setMassCart(double massCart) {
        this.massCart = massCart;
    }

    public double getMassPole() {
        return massPole;
    }

    public void setMassPole(double massPole) {
        this.massPole = massPole;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getForceMag() {
        return forceMag;
    }

    public void setForceMag(double forceMag) {
        this.forceMag = forceMag;
    }

    public double getTau() {
        return tau;
    }

    public void setTau(double tau) {
        this.tau = tau;
    }

    public String getKinematicsIntegrator() {
        return kinematicsIntegrator;
    }

    public void setKinematicsIntegrator(String kinematicsIntegrator) {
        this.kinematicsIntegrator = kinematicsIntegrator;
    }

    public double getThetaThresholdRadians() {
        return thetaThresholdRadians;
    }

    public void setThetaThresholdRadians(double thetaThresholdRadians) {
        this.thetaThresholdRadians = thetaThresholdRadians;
        this.thetaThresholdDegrees = thetaThresholdRadians * 360 / (Math.PI * 2);
    }

    public double getThetaThresholdDegrees() {
        return thetaThresholdRadians;
    }

    public void setThetaThresholdDegrees(double thetaThresholdDegrees) {
        this.thetaThresholdDegrees = thetaThresholdDegrees;
        this.thetaThresholdRadians = thetaThresholdDegrees * 2 * Math.PI / 360;
    }

    public double getxThreshold() {
        return xThreshold;
    }

    public void setxThreshold(double xThreshold) {
        this.xThreshold = xThreshold;
    }

    public CartPoleEnvironment build() {
        return new CartPoleEnvironment(gravity,
                massCart,
                massPole,
                length,
                forceMag,
                tau,
                kinematicsIntegrator,
                thetaThresholdRadians,
                xThreshold);
    }
}
