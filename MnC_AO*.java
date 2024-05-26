import java.util.*;

class State {
    int missionaries;
    int cannibals;
    boolean boat;

    public State(int missionaries, int cannibals, boolean boat) {
        this.missionaries = missionaries;
        this.cannibals = cannibals;
        this.boat = boat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return missionaries == state.missionaries &&
                cannibals == state.cannibals &&
                boat == state.boat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(missionaries, cannibals, boat);
    }
}

class Node {
    State state;
    Node parent;
    int cost;
    int heuristic;

    public Node(State state, Node parent, int cost, int heuristic) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
        this.heuristic = heuristic;
    }
}

public class aostar {
    public static List<State> getSuccessors(State state) {
        List<State> successors = new ArrayList<>();
        if (state.boat) {
            for (int m = 0; m <= 2; m++) {
                for (int c = 0; c <= 2; c++) {
                    if (m + c <= 2 && m + c > 0 && (m == 0 || m >= c)) {
                        successors.add(new State(state.missionaries - m, state.cannibals - c, !state.boat));
                    }
                }
            }
        } else {
            for (int m = 0; m <= 2; m++) {
                for (int c = 0; c <= 2; c++) {
                    if (m + c <= 2 && m + c > 0 && (m == 0 || m >= c)) {
                        successors.add(new State(state.missionaries + m, state.cannibals + c, !state.boat));
                    }
                }
            }
        }
        return successors;
    }

    public static int heuristic(State state) {
        // In this case, a simple heuristic could be the total number of missionaries and cannibals remaining to move
        return state.missionaries + state.cannibals;
    }

    public static List<State> aoStar(State initialState) {
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost + a.heuristic));
        Map<State, Integer> costMap = new HashMap<>();
        Map<State, Node> parentMap = new HashMap<>();

        open.add(new Node(initialState, null, 0, heuristic(initialState)));
        costMap.put(initialState, 0);

        while (!open.isEmpty()) {
            Node currentNode = open.poll();
            State currentState = currentNode.state;

            if (currentState.missionaries == 0 && currentState.cannibals == 0 && !currentState.boat) {
                List<State> path = new ArrayList<>();
                while (currentNode != null) {
                    path.add(currentNode.state);
                    currentNode = currentNode.parent;
                }
                Collections.reverse(path);
                return path;
            }

            for (State successor : getSuccessors(currentState)) {
                int newCost = currentNode.cost + 1;
                if (!costMap.containsKey(successor) || newCost < costMap.get(successor)) {
                    int heuristic = heuristic(successor);
                    open.add(new Node(successor, currentNode, newCost, heuristic));
                    costMap.put(successor, newCost);
                    parentMap.put(successor, currentNode);
                }
            }
        }

        return null; // No solution found
    }

    public static void main(String[] args) {
        State initialState = new State(3, 3, true);
        List<State> solution = aoStar(initialState);
        if (solution != null) {
            for (State state : solution) {
                System.out.println("Missionaries: " + state.missionaries + ", Cannibals: " + state.cannibals + ", Boat: " + (state.boat ? "Left" : "Right"));
            }
        } else {
            System.out.println("No solution found!");
        }
    }
}
