import java.util.*;

public class MissionariesAndCannibals {
    // State class to represent the state of the problem
    static class State {
        int missionariesLeft;
        int cannibalsLeft;
        int missionariesRight;
        int cannibalsRight;
        boolean boatOnLeft;

        public State(int missionariesLeft, int cannibalsLeft, int missionariesRight, int cannibalsRight, boolean boatOnLeft) {
            this.missionariesLeft = missionariesLeft;
            this.cannibalsLeft = cannibalsLeft;
            this.missionariesRight = missionariesRight;
            this.cannibalsRight = cannibalsRight;
            this.boatOnLeft = boatOnLeft;
        }

        // Check if the state is valid
        public boolean isValid() {
            if (missionariesLeft < 0 || cannibalsLeft < 0 || missionariesRight < 0 || cannibalsRight < 0 ||
                    (missionariesLeft != 0 && missionariesLeft < cannibalsLeft) ||
                    (missionariesRight != 0 && missionariesRight < cannibalsRight)) {
                return false;
            }
            return true;
        }

        // Check if the state is goal state
        public boolean isGoal() {
            return missionariesLeft == 0 && cannibalsLeft == 0;
        }

        // Override equals and hashCode methods for hashing and comparison
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return missionariesLeft == state.missionariesLeft &&
                    cannibalsLeft == state.cannibalsLeft &&
                    missionariesRight == state.missionariesRight &&
                    cannibalsRight == state.cannibalsRight &&
                    boatOnLeft == state.boatOnLeft;
        }

        @Override
        public int hashCode() {
            return Objects.hash(missionariesLeft, cannibalsLeft, missionariesRight, cannibalsRight, boatOnLeft);
        }
    }

    // Method to perform Breadth First Search
    public static void bfs() {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        Map<State, State> parentMap = new HashMap<>();
        State initialState = new State(3, 3, 0, 0, true);
        queue.offer(initialState);
        visited.add(initialState);
        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            if (currentState.isGoal()) {
                printSolution(parentMap, currentState);
                return;
            }
            List<State> nextPossibleStates = getNextStates(currentState);
            for (State nextState : nextPossibleStates) {
                if (!visited.contains(nextState)) {
                    queue.offer(nextState);
                    visited.add(nextState);
                    parentMap.put(nextState, currentState);
                }
            }
        }
    }

    // Method to perform Depth First Search
    public static void dfs() {
        Stack<State> stack = new Stack<>();
        Set<State> visited = new HashSet<>();
        Map<State, State> parentMap = new HashMap<>();
        State initialState = new State(3, 3, 0, 0, true);
        stack.push(initialState);
        visited.add(initialState);
        while (!stack.isEmpty()) {
            State currentState = stack.pop();
            if (currentState.isGoal()) {
                printSolution(parentMap, currentState);
                return;
            }
            List<State> nextPossibleStates = getNextStates(currentState);
            for (State nextState : nextPossibleStates) {
                if (!visited.contains(nextState)) {
                    stack.push(nextState);
                    visited.add(nextState);
                    parentMap.put(nextState, currentState);
                }
            }
        }
    }

    // Method to get next possible states from current state
    public static List<State> getNextStates(State currentState) {
        List<State> nextStates = new ArrayList<>();
        int[] missionaries = {1, 0, 2, 0, 1};
        int[] cannibals = {0, 1, 0, 2, 1};
        for (int i = 0; i < missionaries.length; i++) {
            int deltaML = missionaries[i];
            int deltaCL = cannibals[i];
            int deltaMR = -deltaML;
            int deltaCR = -deltaCL;
            if (currentState.boatOnLeft) {
                State nextState = new State(currentState.missionariesLeft - deltaML, currentState.cannibalsLeft - deltaCL,
                        currentState.missionariesRight - deltaMR, currentState.cannibalsRight - deltaCR, false);
                if (nextState.isValid()) {
                    nextStates.add(nextState);
                }
            } else {
                State nextState = new State(currentState.missionariesLeft + deltaML, currentState.cannibalsLeft + deltaCL,
                        currentState.missionariesRight + deltaMR, currentState.cannibalsRight + deltaCR, true);
                if (nextState.isValid()) {
                    nextStates.add(nextState);
                }
            }
        }
        return nextStates;
    }

    // Method to print solution
    public static void printSolution(Map<State, State> parentMap, State goalState) {
        List<State> solution = new ArrayList<>();
        State currentState = goalState;
        while (currentState != null) {
            solution.add(currentState);
            currentState = parentMap.get(currentState);
        }
        Collections.reverse(solution);
        for (State state : solution) {
            System.out.println(state.missionariesLeft + "M " + state.cannibalsLeft + "C | BOAT | " +
                    state.missionariesRight + "M " + state.cannibalsRight + "C");
        }
    }

    public static void main(String[] args) {
        System.out.println("Breadth First Search Solution:");
        bfs();
        System.out.println("\nDepth First Search Solution:");
        dfs();
    }
}

