package com.jaosy.flappybirddemo.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jaosy.flappybirddemo.FlappyBirdDemo;

public class playState extends State {

    private Texture bird;

    public playState(GameStateManager gsm) {
        super(gsm);
        bird = new Texture("bird.png");
        cam.setToOrtho(false, FlappyBirdDemo.WIDTH/2, FlappyBirdDemo.HEIGHT/2);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bird, 50,50);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
