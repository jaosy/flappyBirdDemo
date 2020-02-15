package com.jaosy.flappybirddemo.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {

    private Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();
    }

    public void push(State state){
        states.push(state);
    }

    public void pop(){
         states.pop(); // popping means you don't intend to use that
                // state again, so we can dispose of it right away

    }

    public void set(State state){
        states.pop();
        states.push(state);
    }

    public void update(float dt){
        // dt: time between two renders
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);

    }
}
