package com.jaosy.flappybirddemo.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;

public class Bird {

    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;
    private Vector3 position;
    private Vector3 velocity;
    private Texture bird;
    private Rectangle bounds; // to detect collision
    private Animation birdAnimation;
    Texture texture;
    private Sound flap;

    public Bird(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0,0,0);
        texture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(texture), 3,0.5f);
        bounds = new Rectangle(x, y, texture.getWidth()/3, texture.getHeight()/3);
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float dt) {
        birdAnimation.update(dt);
        if (position.y > 0) {
            // the bird only changes its y position
            velocity.add(0,GRAVITY,0);
        }
        velocity.scl(dt); // scale velocity by change in time
        position.add(0,velocity.y,0);
        position.add(MOVEMENT * dt, velocity.y, 0);

        if(position.y < 0) {
            position.y = 0;
        }

        velocity.scl(1/dt); // reverses what we scaled so it can be added back!
        bounds.setPosition(position.x, position.y);
    }

    public void jump(){
        // add positive velocity to make it fly upwards
        velocity.y = 250;
        flap.play(0.4f);
    }

    public void dispose(){
        texture.dispose();
        flap.dispose();
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

}