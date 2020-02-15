package com.jaosy.flappybirddemo.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jaosy.flappybirddemo.FlappyBirdDemo;
import com.jaosy.flappybirddemo.Sprites.Bird;
import com.jaosy.flappybirddemo.Sprites.Tube;

public class playState extends State {

    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4; // total tubes visible at once
    private static final int GROUND_Y_OFFSET = -40;

    private Texture ground;
    private Bird bird;
    private Texture bg;
    private Vector2 groundPos1;
    private Vector2 groundPos2;

    private Array<Tube> tubes; // use LibGDX's Array, not Java's

    public playState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,300);
        cam.setToOrtho(false, FlappyBirdDemo.WIDTH/2, FlappyBirdDemo.HEIGHT/2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth/2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth/2) + ground.getWidth(), GROUND_Y_OFFSET);
            // offset the second ground texture by the width of the first, so you have a second
            // ground to keep loading into the view of the camera

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
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80; // camera follows bird

        // we will be "reusing tubes", moving them back to the right side of the screen
        for(Tube tube : tubes) {
            if (cam.position.x - (cam.viewportWidth/3) > tube.getPosTopTube().x
                + tube.getTopTube().getWidth()) { // if tube is off the left side of the screen
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(bird.getBounds())) {
                // note that this method is not efficient for larger games with more collision
                // for now, this will reset the game to the beginning of the play state
                gsm.set(new playState(gsm));
            }
        }

        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            // bird dies if it touches the ground
            gsm.set(new playState(gsm)); // create a new play state - restart
        }

        cam.update(); // tells LibGDX that the camera has changed
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth/2), 0); // - (cam.viewport/2) is to get the
                                                                    // right half of the bg image

        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);

        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();
    }

    private void updateGround() {
        // to scroll the ground
        if(cam.position.x - (cam.viewportWidth/2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth()*2, 0);
        }

        if(cam.position.x - (cam.viewportWidth/2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth()*2, 0);
        }
    }
    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        for (Tube tube: tubes) {
            tube.dispose();
        }

        System.out.println("play state disposed");
    }
}
