package org.kostiamatv.cart_pole_v1_agent;

import java.util.ArrayDeque;

public class CartPoleAgent {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CartPoleEnvironment environment = new CartPoleEnvironmentBuilder().build();
        ArrayDeque<Integer> scores = new ArrayDeque<>();
        double scoresSum = 0.0;
        int scoresNum = 0;
        DQN dqn = new DQN();
        int run = 0;
        double avg = 0;

        while (avg < 195) {
            run += 1;
            EnvironmentState state = environment.reset();
            int step = 0;
            while (true) {
                step += 1;
                int action = dqn.act(state);
                boolean done = environment.step(action);
                int reward = done ? -1 : 1;
                EnvironmentState nextState = environment.getState();
                dqn.remember(state, action, reward, nextState, done);
                state = new EnvironmentState(nextState);
                if (done) {
                    scores.add(step);
                    scoresSum += step;
                    scoresNum++;
                    if (scores.size() > 100) {
                        scoresSum -= scores.pop();
                        scoresNum -= 1;
                    }
                    avg = scoresSum / scoresNum;
                    System.out.printf("Run %d finished with score %d \n 100 avg: %.2f\n", run, step, avg);
                    break;
                }
                dqn.experienceReplay();
            }
        }
        long end = System.currentTimeMillis();
        long millis = end - start;
        long min = millis / 60000;
        float sec = millis / 1000F % 60;
        System.out.printf("Learning finished on run %d in %d mins %.1f sec", run, min, sec);
    }
}
