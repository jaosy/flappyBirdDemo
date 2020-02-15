package com.jaosy.flappybirddemo.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime; // how long the frame stays before switching to next one
    private float currentFrameTime; // time animation has been in current frame
    private int frameCount; // num frames in animation
    private int frame; // current frame

    public Animation(TextureRegion region, int frameCount, float cycleTime) {
        // frame count is number of frames in TextureRegion
        // region is all frames combined into 1 image

        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
            // we want the width of a single frame
        for (int i = 0; i < frameCount; i ++) {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));

            /**
             * The birdanimation.png is 3 pictures of the flying bird stitched together - that is the
             * texture region. We start from the left of the image (i * frameWidth) and then we get the
             * first frame which is the first bird. In the next iteration x moves to 1 * frameWidth which
             * gets us to the start of the second bird.
             */


        }

        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount; // each frame gets equal count
        frame = 0;

    }

    public void update(float dt) {
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }
        if(frame >= frameCount) {
            frame = 0; // go back to start
        }
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }

}
