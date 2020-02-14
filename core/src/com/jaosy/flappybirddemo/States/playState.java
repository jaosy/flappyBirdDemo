package com.jaosy.flappybirddemo.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.jaosy.flappybirddemo.FlappyBirdDemo;
import com.jaosy.flappybirddemo.Sprites.Bird;
import com.jaosy.flappybirddemo.Sprites.Tube;

public class playState extends State {

    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4; // total tubes visible at once

    private Bird bird;
    private Texture bg;
    private Tube tube;

    private Array<Tube> tubes; // use LibGDX's Array, not Java's

    public playState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,300);
        cam.setToOrtho(false, FlappyBirdDemo.WIDTH/2, FlappyBirdDemo.HEIGHT/2);
        bg = new Texture("bg.png");

        tubes = new Array<Tube>();

        for (int i = 1; i <= TUBE_COUNT; i ++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump(); // tap to make it fly upwards
        }
    }

    @Override
    public void update(float dt) {
        handleInput(); // typically this call comes first!
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80; // camera follows bird

        // we will be "reusing tubes", moving them back to the right side of the screen
        for(Tube tube : tubes) {
            if (cam.position.x - (cam.viewportWidth/2) > tube.getPosTopTube().x
                + tube.getTopTube().getWidth()) { // if tube is off the left side of the screen
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(bird.getBounds())) {
                // note that this method is not efficient for larger games with more collision
                // for now, this will reset the game to the beginning of the play state
                gsm.set(new playState(gsm));
            }
        }

        cam.update(); // tells LibGDX that the camera has changed
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth/2), 0); // - (cam.viewport/2) is to get the
                                                                    // right half of the bg image
        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
