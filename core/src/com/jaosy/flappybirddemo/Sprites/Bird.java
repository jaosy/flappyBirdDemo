package com.jaosy.flappybirddemo.Sprites;

import com.badlogic.gdx.graphics.Texture;
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

    public Bird(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0,0,0);
        bird = new Texture("bird.png");
        bounds = new Rectangle(x, y, bird.getWidth(), bird.getHeight());
    }

    public void update(float dt) {
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
    }

    public void dispose(){
        bird.dispose();
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return bird;
    }

}