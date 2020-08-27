package org.kostiamatv.cart_pole_v1_agent;

import java.util.HashSet;
import java.util.Random;

public class CartPoleEnvironment {

    private static final HashSet<Integer> possibleActions = new HashSet<>() {{
        add(0);
        add(1);
    }};

    private final double gravity;
    private final double massCart;
    private final double massPole;
    private final double totalMass;
    private final double length;
    private final double poleMassLength;
    private final double forceMag;
    private final double tau;
    private final String kinematicsIntegrator;
    private final double thetaThresholdRadians;
    private final double xThreshold;

    private EnvironmentState state = new EnvironmentState();

    private int seed;
    private Random random;


    public CartPoleEnvironment(double gravity,
                               double massCart,
                               double massPole,
                               double length,
                               double forceMag,
                               double tau,
                               String kinematicsIntegrator,
                               double thetaThresholdRadians,
                               double xThreshold
    ) {
        this.gravity = gravity;
        this.massCart = massCart;
        this.massPole = massPole;
        this.totalMass = massCart + massPole;
        this.length = length;
        this.poleMassLength = massPole * length;
        this.forceMag = forceMag;
        this.tau = tau;
        this.kinematicsIntegrator = kinematicsIntegrator;
        this.thetaThresholdRadians = thetaThresholdRadians;
        this.xThreshold = xThreshold;

        this.random = new Random();
        this.seed = random.nextInt();
        this.random = new Random(this.seed);

        reset();
    }

    public void setSeed(int seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }

    public int getSeed(int seed) {
        return seed;
    }

    public boolean step(int action) {
        assert (possibleActions.contains(action));
        double x = state.getX();
        double xVel = state.getXVel();
        double theta = state.getTheta();
        double thetaVel = state.getThetaVel();

        double force = action == 1 ? forceMag : -forceMag;
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);

        double temp = (force + poleMassLength * Math.pow(thetaVel, 2) * sinTheta)
                / totalMass;
        double thetaAcc = (gravity * sinTheta - cosTheta * temp)
                / (length * (4.0 / 3.0 - massPole * Math.pow(cosTheta, 2) / totalMass));
        double xAcc = temp - poleMassLength * thetaAcc * cosTheta
                / totalMass;
        if (kinematicsIntegrator.equals("euler")) {
            x += xVel * tau;
            xVel += xAcc * tau;
            theta += thetaVel * tau;
            thetaVel += thetaAcc * tau;
        } else {
            xVel += xAcc * tau;
            x += xVel * tau;
            thetaVel += thetaAcc * tau;
            theta += thetaVel * tau;
        }
        state.setX(x);
        state.setXVel(xVel);
        state.setTheta(theta);
        state.setThetaVel(thetaVel);

        return (x < -xThreshold
                || x > xThreshold
                || theta < -thetaThresholdRadians
                || theta > thetaThresholdRadians);
    }

    public EnvironmentState getState() {
        return new EnvironmentState(state);
    }

    public EnvironmentState reset() {
        state.setX(getRandomDoubleInRange(-0.05, 0.05));
        state.setXVel(getRandomDoubleInRange(-0.05, 0.05));
        state.setTheta(getRandomDoubleInRange(-0.05, 0.05));
        state.setThetaVel(getRandomDoubleInRange(-0.05, 0.05));
        return new EnvironmentState(state);
    }

    private double getRandomDoubleInRange(double min, double max) {
        return min + random.nextDouble() * (max - min);
    }

}
