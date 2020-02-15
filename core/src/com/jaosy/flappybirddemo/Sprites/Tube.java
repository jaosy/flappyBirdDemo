package com.jaosy.flappybirddemo.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
import java.util.Vector;

public class Tube {

    private static final int FLUCTUATION = 130; // moves up and down randomly btwn 0 and 130
    private static final int TUBE_GAP = 150; // diff. btwn tube openings
    private static final int LOWEST_OPENING = 100; // lowest opening for top tube
    public static final int TUBE_WIDTH = 52; // based on size of picture in pixels

    private Texture topTube;
    private Texture bottomTube;
    private Vector2 posTopTube;
    private Vector2 posBotTube;
    private Random rand;
    private Rectangle boundsTop, boundsBot; // to detect collision with tube

    public Tube (float x) {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();

        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION )+ TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());

    }

    public void reposition(float x) {
        // input: float because it could "partially be on the x-axis or something"
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    public boolean collides(Rectangle player){
        return (player.overlaps(boundsTop) || player.overlaps(boundsBot));
    }

    public void dispose(){
        topTube.dispose();
        bottomTube.dispose();
    }
    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }
}
