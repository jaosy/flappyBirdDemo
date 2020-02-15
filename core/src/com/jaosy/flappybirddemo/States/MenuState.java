package com.jaosy.flappybirddemo.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jaosy.flappybirddemo.FlappyBirdDemo;

public class MenuState extends State {

    private Texture background;
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture(("bg.png"));
        playBtn = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new playState(gsm));
            // removed call from dispose here because it's been put into State
        }
    }

    @Override
    public void update(float dt) {
        handleInput(); // check to see if user gave input

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin(); // like opening the box of sprites
        sb.draw(background, 0, 0, FlappyBirdDemo.WIDTH, FlappyBirdDemo.HEIGHT); // draws background at 0,0
        sb.draw(playBtn,(FlappyBirdDemo.WIDTH/2) - (playBtn.getWidth()/2), FlappyBirdDemo.HEIGHT/2);
        sb.end();

    }

    @Override
    public void dispose() {
        // get rid of resources so there's no memory leak
        background.dispose();
        playBtn.dispose();

        System.out.println("menu state disposed");
    }
}
