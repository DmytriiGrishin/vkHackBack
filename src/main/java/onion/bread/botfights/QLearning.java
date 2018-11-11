package onion.bread.botfights;

import burlap.behavior.policy.EpsilonGreedy;
import burlap.behavior.policy.Policy;
import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.MDPSolver;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.valuefunction.ConstantValueFunction;
import burlap.behavior.valuefunction.QFunction;
import burlap.behavior.valuefunction.QProvider;
import burlap.behavior.valuefunction.QValue;
import burlap.domain.singleagent.gridworld.GridWorldDomain;
import burlap.domain.singleagent.gridworld.GridWorldTerminalFunction;
import burlap.domain.singleagent.gridworld.state.GridAgent;
import burlap.domain.singleagent.gridworld.state.GridWorldState;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.environment.Environment;
import burlap.mdp.singleagent.environment.EnvironmentOutcome;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.statehashing.HashableState;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import javafx.util.Pair;

import java.util.*;

public class QLearning extends MDPSolver implements LearningAgent, QProvider {

    Map<HashableState, List<QValue>> qValues;
    QFunction qinit;
    double learningRate;
    Policy learningPolicy;

    public QLearning(SADomain domain, double gamma, HashableStateFactory hashingFactory,
                      QFunction qinit, double learningRate, double epsilon){

        this.solverInit(domain, gamma, hashingFactory);
        this.qinit = qinit;
        this.learningRate = learningRate;
        this.qValues = new HashMap<HashableState, List<QValue>>();
        this.learningPolicy = new EpsilonGreedy(this, epsilon);

    }

    @Override
    public Episode runLearningEpisode(Environment env) {
        return this.runLearningEpisode(env, -1);
    }

    @Override
    public Episode runLearningEpisode(Environment env, int maxSteps) {
        //initialize our episode object with the initial state of the environment
        Episode e = new Episode(env.currentObservation());

        //behave until a terminal state or max steps is reached
        State curState = env.currentObservation();
        int steps = 0;
        while(!env.isInTerminalState() && (steps < maxSteps || maxSteps == -1)){

            //select an action
            Action a = this.learningPolicy.action(curState);

            //take the action and observe outcome
            EnvironmentOutcome eo = env.executeAction(a);

            //record result
            e.transition(eo);

            //get the max Q value of the resulting state if it's not terminal, 0 otherwise
            double maxQ = eo.terminated ? 0. : this.value(eo.op);

            //update the old Q-value
            QValue oldQ = this.storedQ(curState, a);
            oldQ.q = oldQ.q + this.learningRate * (eo.r + this.gamma * maxQ - oldQ.q);


            //update state pointer to next environment state observed
            curState = eo.op;
            steps++;

        }

        return e;
    }

    @Override
    public void resetSolver() {
        this.qValues.clear();
    }

    @Override
    public List<QValue> qValues(State s) {
        //first get hashed state
        HashableState sh = this.hashingFactory.hashState(s);

        //check if we already have stored values
        List<QValue> qs = this.qValues.get(sh);

        //create and add initialized Q-values if we don't have them stored for this state
        if(qs == null){
            List<Action> actions = this.applicableActions(s);
            qs = new ArrayList<QValue>(actions.size());
            //create a Q-value for each action
            for(Action a : actions){
                //add q with initialized value
                qs.add(new QValue(s, a, this.qinit.qValue(s, a)));
            }
            //store this for later
            this.qValues.put(sh, qs);
        }

        return qs;
    }

    @Override
    public double qValue(State s, Action a) {
        return storedQ(s, a).q;
    }


    protected QValue storedQ(State s, Action a){
        //first get all Q-values
        List<QValue> qs = this.qValues(s);

        //iterate through stored Q-values to find a match for the input action
        for(QValue q : qs){
            if(q.a.equals(a)){
                return q;
            }
        }

        throw new RuntimeException("Could not find matching Q-value.");
    }

    @Override
    public double value(State s) {
        return QProvider.Helper.maxQ(this, s);
    }

    public static List<PlayerData> calculate() {

        GridWorldDomain gwd = new GridWorldDomain(20, 20);
        gwd.setProbSucceedTransitionDynamics(0.8);
        gwd.setTf(new GridWorldTerminalFunction(0, 0));

        Random r = new Random();

        gwd.horizontalWall(r.nextInt(9) + 1, r.nextInt(17) + 1, r.nextInt(17) + 1);
        gwd.verticalWall(r.nextInt(9) + 1, r.nextInt(17) + 1, r.nextInt(17) + 1);
        gwd.horizontalWall(r.nextInt(9) + 1, r.nextInt(17) + 1, r.nextInt(17) + 1);
        gwd.verticalWall(r.nextInt(9) + 1, r.nextInt(17) + 1, r.nextInt(17) + 1);
        gwd.horizontalWall(r.nextInt(9) + 1, r.nextInt(17) + 1, r.nextInt(17) + 1);
        gwd.verticalWall(r.nextInt(9) + 1, r.nextInt(17) + 1, r.nextInt(17) + 1);
        gwd.horizontalWall(r.nextInt(9) + 1, r.nextInt(17) + 1, r.nextInt(17) + 1);
        gwd.verticalWall(r.nextInt(9) + 1, r.nextInt(17) + 1, r.nextInt(17) + 1);
        gwd.horizontalWall(r.nextInt(9) + 1, r.nextInt(17) + 1, r.nextInt(17) + 1);
        gwd.verticalWall(r.nextInt(9) + 1, r.nextInt(17) + 1, r.nextInt(17) + 1);

        State s1 = new GridWorldState(new GridAgent(9, 9));
        OOSADomain domain = gwd.generateDomain();
        SimulatedEnvironment env1 = new SimulatedEnvironment(domain, s1);
        gwd.setTf(new GridWorldTerminalFunction(19, 0));
        State s2 = new GridWorldState(new GridAgent(10, 9));
        OOSADomain domain2 = gwd.generateDomain();
        SimulatedEnvironment env2 = new SimulatedEnvironment(domain2, s2);
        gwd.setTf(new GridWorldTerminalFunction(0, 19));
        State s3 = new GridWorldState(new GridAgent(9, 10));
        OOSADomain domain3 = gwd.generateDomain();
        SimulatedEnvironment env3 = new SimulatedEnvironment(domain3, s3);
        gwd.setTf(new GridWorldTerminalFunction(19, 19));
        State s4 = new GridWorldState(new GridAgent(10, 10));
        OOSADomain domain4 = gwd.generateDomain();
        SimulatedEnvironment env4 = new SimulatedEnvironment(domain4, s4);

        //create Q-learning
        QLearning agent = new QLearning(domain, 0.99, new SimpleHashableStateFactory(),
                new ConstantValueFunction(), 0.1, 0.1);

        QLearning agent2 = new QLearning(domain2, 0.99, new SimpleHashableStateFactory(),
                new ConstantValueFunction(), 0.1, 0.1);

        QLearning agent3 = new QLearning(domain3, 0.99, new SimpleHashableStateFactory(),
                new ConstantValueFunction(), 0.1, 0.1);

        QLearning agent4 = new QLearning(domain4, 0.99, new SimpleHashableStateFactory(),
                new ConstantValueFunction(), 0.1, 0.1);

        List<PlayerData> result = new ArrayList<>();
        int map1 [][] = gwd.getMap();
        for (int i = 0; i < map1.length; i++) {
            for (int j = i+1; j < map1.length; j++) {
                int temp = map1[i][j];
                map1[i][j] = map1[j][i];
                map1[j][i] = temp;
            }
        }

        result.add(new PlayerData(map1, getData(getEpisodes(env1, agent))));
        result.add(new PlayerData(map1, getData(getEpisodes(env2, agent2))));
        result.add(new PlayerData(map1, getData(getEpisodes(env3, agent3))));
        result.add(new PlayerData(map1, getData(getEpisodes(env4, agent4))));
        return result;
    }

    public static List<Episode> getEpisodes(Environment env, QLearning agent) {
        List<Episode> episodes = new ArrayList<>(1000);
        for(int i = 0; i < 1000; i++){
            episodes.add(agent.runLearningEpisode(env));
            env.resetEnvironment();
        }
        return episodes;
    }

    public static List<Pair<Integer, Integer>> getData(List<Episode> episodes) {
        List<Pair<Integer, Integer>> resultSet = new ArrayList<>();
        for(State item : episodes.get(episodes.size()-1).stateSequence) {
            Integer x = (Integer) item.get(item.variableKeys().get(0));
            Integer y = (Integer) item.get(item.variableKeys().get(1));
            resultSet.add(new Pair(x,y));
        }
        return resultSet;
    }
}