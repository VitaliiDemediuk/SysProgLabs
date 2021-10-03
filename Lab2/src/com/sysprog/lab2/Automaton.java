package com.sysprog.lab2;

import java.util.*;

public class Automaton {

    private static class State {

        private static class Transition {
            // code point
            public final int input;
            public final int state;

            // aInput - code point
            Transition (int aInput, int aState) {
                input = aInput;
                state = aState;
            }
        }

        public final ArrayList<Transition> transitions = new ArrayList<>();

        // input - code point
        public void addTransition(int input, int nextStage)
        {
            transitions.add(new Transition(input, nextStage));
        }
    }

    private final int startState;
    private final List<State> statesTo;
    private final List<State> statesFrom;
    private final Set<Integer> finalStates = new HashSet<>();

    Automaton (int nState, int aStart)
    {
        startState = aStart;
        statesTo = new ArrayList<>(nState);
        statesFrom = new ArrayList<>(nState);
        for (int i = 0; i < nState; ++i) {
            statesTo.add(new State());
            statesFrom.add(new State());
        }
    }

    // input - code point
    public void addTransition (int curState, int input, int nextStage)
    {
        statesTo.get(curState).addTransition(input, nextStage);
        statesFrom.get(nextStage).addTransition(input, curState);
    }

    public void addFinalState(int state)
    {
        finalStates.add(state);
    }

    // return ArrayList were visited states marked true
    private ArrayList<Boolean> bfs(List<State> states, int aStartState)
    {
        final ArrayList<Boolean> visited = new ArrayList<>(Collections.nCopies(states.size(), false));
        final Queue<Integer> q = new LinkedList<>();
        q.offer(aStartState);
        while (!q.isEmpty()) {
            final int nextSt = q.remove();
            visited.set(nextSt, true);
            for (var t : states.get(nextSt).transitions) {
                if (!visited.get(t.state)) {
                    q.offer(t.state);
                }
            }
        }
        return visited;
    }

    public Set<Integer> deadStates()
    {
        final Set<Integer> result = new HashSet<>();
        for (var finalSt : finalStates) {
            final var visited = bfs(statesFrom, finalSt);
            for (int i = 0; i < visited.size(); ++i) {
                if (!visited.get(i)) {
                    result.add(i);
                }
            }
        }
        return result;
    }

    public Set<Integer> unreachableStates() {
        final Set<Integer> result = new HashSet<>();
        final var visited = bfs(statesTo, startState);
        for (int i = 0; i < visited.size(); ++i) {
            if (!visited.get(i)) {
                result.add(i);
            }
        }
        return result;
    }
}
