# CartPole-v1 Java agent

This is a solution for the CartPole-v1, OpenAI's gym environment.

Firstly, physics engine was rewritten in Java (Environment State, CartPoleEnvironment and CartPoleEnvironmentBuilder).

Then, solution based on Deep Q Network was implemented (DQN and CartPoleAgent).

## Dependecies

* [DL4J](https://deeplearning4j.org/)

## How to launch:

1) get FatJar from the [release](https://github.com/kostiamatv/CartPole-v1-Java-Agent/releases/tag/1.0)
2) java -jar CartPole-v1-Java-Agent-1.0.jar

The task is solved in 250-300 runs in average (usually either 100-150 or 400+).
