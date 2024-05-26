import java.util.*;

public class MissionariesAndCannibals {
    
    // Representation of a state
    static class State {
        int missionaries;
        int cannibals;
        boolean boat;

        State(int missionaries, int cannibals, boolean boat) {
            this.missionaries = missionaries;
            this.cannibals = cannibals;
            this.boat = boat;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (!(obj instanceof State)) return false;
            State other = (State) obj;
            return missionaries == other.missionaries && cannibals == other.cannibals && boat == other.boat;
        }

        @Override
        public int hashCode() {
            return Objects.hash(missionaries, cannibals, boat);
        }
    }

    // Hill Climbing algorithm with an informed strategy
    static List<State> hillClimbing() {
        List<State> path = new ArrayList<>();
        State currentState = new State(3, 3, true);
        path.add(currentState);

        while (!goalState(currentState)) {
            List<State> neighbors = getNeighbors(currentState);
            State nextState = null;
            int minHeuristic = Integer.MAX_VALUE;

            for (State neighbor : neighbors) {
                int heuristic = heuristic(neighbor);
                if (heuristic < minHeuristic) {
                    minHeuristic = heuristic;
                    nextState = neighbor;
                }
            }

            if (nextState == null)
                break;

            path.add(nextState);
            currentState = nextState;
        }

        return path;
    }

    // Heuristic function: number of missionaries and cannibals on the wrong side
    static int heuristic(State state) {
        int onWrongSide = state.missionaries + state.cannibals;
        if (state.missionaries != 0 && state.missionaries < state.cannibals)
            onWrongSide += state.cannibals - state.missionaries;
        return onWrongSide;
    }

    // Check if a state is the goal state
    static boolean goalState(State state) {
        return state.missionaries == 0 && state.cannibals == 0 && !state.boat;
    }

    // Get possible neighbors (successor states)
    static List<State> getNeighbors(State state) {
        List<State> neighbors = new ArrayList<>();
        int m = state.missionaries;
        int c = state.cannibals;

        if (state.boat) {
            for (int i = 0; i <= 2; i++) {
                for (int j = 0; j <= 2; j++) {
                    if (i + j >= 1 && i + j <= 2 && m - i >= 0 && c - j >= 0 && (m - i == 0 || m - i >= c - j))
                        neighbors.add(new State(m - i, c - j, false));
                }
            }
        } else {
            for (int i = 0; i <= 2; i++) {
                for (int j = 0; j <= 2; j++) {
                    if (i + j >= 1 && i + j <= 2 && m + i >= 0 && c + j >= 0 && (m + i == 0 || m + i >= c + j))
                        neighbors.add(new State(m + i, c + j, true));
                }
            }
        }

        return neighbors;
    }

    public static void main(String[] args) {
        List<State> path = hillClimbing();

        System.out.println("Solution path:");
        for (State state : path) {
            System.out.println(state.missionaries + " missionaries, " + state.cannibals + " cannibals, boat: " + (state.boat ? "left" : "right"));
        }
    }
}

