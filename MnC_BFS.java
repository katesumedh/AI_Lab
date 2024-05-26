import java.util.*;

class State {
    int missionariesLeft;
    int cannibalsLeft;
    int boat;
    int missionariesRight;
    int cannibalsRight;
    State parentState;

    public State(int missionariesLeft, int cannibalsLeft, int boat,
                 int missionariesRight, int cannibalsRight) {
        this.missionariesLeft = missionariesLeft;
        this.cannibalsLeft = cannibalsLeft;
        this.boat = boat;
        this.missionariesRight = missionariesRight;
        this.cannibalsRight = cannibalsRight;
    }

    public boolean isGoal() {
        return missionariesLeft == 0 && cannibalsLeft == 0;
    }

    public boolean isValid() {
        if (missionariesLeft >= 0 && cannibalsLeft >= 0 &&
                missionariesRight >= 0 && cannibalsRight >= 0 &&
                (missionariesLeft == 0 || missionariesLeft >= cannibalsLeft) &&
                (missionariesRight == 0 || missionariesRight >= cannibalsRight)) {
            return true;
        }
        return false;
    }

    public List<State> generateSuccessors() {
        List<State> successors = new ArrayList<>();
        if (boat == 0) {
            // Boat is on the left side
            for (int i = 0; i <= 2; i++) {
                for (int j = 0; j <= 2; j++) {
                    if (i + j >= 1 && i + j <= 2) {
                        State newState = new State(missionariesLeft - i, cannibalsLeft - j, 1,
                                missionariesRight + i, cannibalsRight + j);
                        if (newState.isValid()) {
                            newState.parentState = this;
                            successors.add(newState);
                        }
                    }
                }
            }
        } else {
            // Boat is on the right side
            for (int i = 0; i <= 2; i++) {
                for (int j = 0; j <= 2; j++) {
                    if (i + j >= 1 && i + j <= 2) {
                        State newState = new State(missionariesLeft + i, cannibalsLeft + j, 0,
                                missionariesRight - i, cannibalsRight - j);
                        if (newState.isValid()) {
                            newState.parentState = this;
                            successors.add(newState);
                        }
                    }
                }
            }
        }
        return successors;
    }

    public int getHeuristic() {
        // Using a simple heuristic: Number of missionaries and cannibals left on the left side
        return missionariesLeft + cannibalsLeft;
    }
}

class BestFirstSearch {
    public static void main(String[] args) {
        State initialState = new State(3, 3, 0, 0, 0);
        System.out.println("Initial State:");
        printSolution(initialState);
        State solution = solve(initialState);
        System.out.println("\nSolution:");
        printSolution(solution);
    }

    public static State solve(State initialState) {
        PriorityQueue<State> openSet = new PriorityQueue<>(Comparator.comparingInt(s -> s.getHeuristic()));
        Set<State> closedSet = new HashSet<>();
        openSet.add(initialState);

        while (!openSet.isEmpty()) {
            State currentState = openSet.poll();

            if (currentState.isGoal()) {
                return currentState;
            }

            closedSet.add(currentState);

            for (State successor : currentState.generateSuccessors()) {
                if (!closedSet.contains(successor)) {
                    openSet.add(successor);
                }
            }
        }

        return null;
    }

    public static void printSolution(State solution) {
        if (solution == null) {
            System.out.println("No solution found.");
            return;
        }

        List<State> path = new ArrayList<>();
        State currentState = solution;
        while (currentState != null) {
            path.add(currentState);
            currentState = currentState.parentState;
        }

        for (int i = path.size() - 1; i >= 0; i--) {
            currentState = path.get(i);
            System.out.println(String.format("Left: %d missionaries, %d cannibals | Right: %d missionaries, %d cannibals | Boat: %s",
                    currentState.missionariesLeft, currentState.cannibalsLeft,
                    currentState.missionariesRight, currentState.cannibalsRight,
                    currentState.boat == 0 ? "Left" : "Right"));
        }
    }
}

