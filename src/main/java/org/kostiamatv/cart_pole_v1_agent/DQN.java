package org.kostiamatv.cart_pole_v1_agent;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Nadam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.ArrayList;
import java.util.Random;

public class DQN {

    private static final double GAMMA = 0.95;
    private static final double BATCH_SIZE = 20;
    private static final double EXPLORATION_MIN = 0.01;
    private static final double EXPLORATION_DECAY = 0.995;
    private static final String MODEL_FILE_NAME = "classpath:dqn.h5";
    private static final Random RANDOM = new Random();

    private static double explorationRate = 1;
    private final ArrayList<ArrayList<Object>> memory;
    private final MultiLayerNetwork model;


    public DQN() {
        memory = new ArrayList<>();
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .weightInit(WeightInit.XAVIER)
                .updater(new Nadam())
                .list()
                .layer(new DenseLayer.Builder()
                        .nIn(4)
                        .nOut(24)
                        .activation(Activation.RELU)
                        .build())
                .layer(new DenseLayer.Builder()
                        .nIn(24)
                        .nOut(24)
                        .activation(Activation.RELU)
                        .build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nOut(2)
                        .build()
                )
                .build();
        model = new MultiLayerNetwork(conf);
        model.init();
    }

    public int act(EnvironmentState state) {
        if (RANDOM.nextDouble() < explorationRate) {
            return RANDOM.nextInt(2);
        }
        INDArray prediction = model.output(convertState(state));
        return Nd4j.argMax(prediction).toIntVector()[0];
    }

    public void experienceReplay() {
        if (memory.size() < BATCH_SIZE) {
            return;
        }
        for (int i = 0; i < BATCH_SIZE; i++) {
            ArrayList<Object> batchElement = memory.get(RANDOM.nextInt(memory.size()));
            EnvironmentState state = (EnvironmentState) batchElement.get(0);
            int action = (int) batchElement.get(1);
            int reward = (int) batchElement.get(2);
            EnvironmentState nextState = (EnvironmentState) batchElement.get(3);
            boolean done = (boolean) batchElement.get(4);
            double newQ = reward;
            if (!done) {
                newQ += GAMMA * (Nd4j.max(model.output(convertState(nextState))).toDoubleVector()[0]);
            }
            INDArray qValues = model.output(convertState(state));
            qValues.put(action, Nd4j.create(new double[]{newQ}));
            model.fit(convertState(state), qValues);
        }
        explorationRate *= EXPLORATION_DECAY;
        explorationRate = Math.max(explorationRate, EXPLORATION_MIN);
    }

    public void remember(EnvironmentState state, int action, int reward, EnvironmentState nextState, boolean done) {
        ArrayList<Object> result = new ArrayList<>();
        result.add(state);
        result.add(action);
        result.add(reward);
        result.add(nextState);
        result.add(done);
        memory.add(result);
    }


    private INDArray convertState(EnvironmentState state) {
        return Nd4j.create(new double[][]{{state.getX(),
                state.getXVel(),
                state.getTheta(),
                state.getThetaVel()}});
    }


}
