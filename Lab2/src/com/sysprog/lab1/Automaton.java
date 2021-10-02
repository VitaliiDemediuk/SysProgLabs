package com.sysprog.lab1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Automaton {

    private static class State {
        private static class Transition {
            // code point
            public final int input;
            public final int nextState;

            // aInput - code point
            Transition (int aInput, int aNextState) {
                input = aInput;
                nextState = aNextState;
            }
        }

        private final List<Transition> transitions = new ArrayList<>();

        // input - code point
        void addTransition(int input, int nextStage)
        {
            transitions.add(new Transition(input, nextStage));
        }
    }

    private final int startState;
    private final List<State> states;
    private final Set<Integer> finalStates = new HashSet<>();

    Automaton (int nState, int aStart)
    {
        startState = aStart;
        states = new ArrayList<>(nState);
    }

    // input - code point
    void addTransition (int curState, int input, int nextStage)
    {
        states.get(curState).addTransition(input, nextStage);
    }

    void addFinalState(int state)
    {
        finalStates.add(state);
    }
}
