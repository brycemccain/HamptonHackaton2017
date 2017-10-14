package com.hampton.game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.hampton.game.GameScreen;
import com.hampton.game.utils.ActorUtils;

//* Created by turnerd on 10/13/17.

public class WhackAMole extends GameScreen {

    class Mole {

        public float xMove;
        public float yMove;
        public Actor moleActor;
    }
    private  float MAX_MOVE = 20;
    private int score = 0;
    private Label.LabelStyle scoreStyle;
    private Label scoreLabel;

    private Music slapMusic;
    
    @Override
    public void initialize() {
        score = 0;
        scoreStyle = new Label.LabelStyle(new BitmapFont(), new Color(1,1,1,1));
        scoreStyle.font.getData().setScale(4);
        scoreLabel = new Label("Score: 0", scoreStyle);
        scoreLabel.setPosition(0, stage.getViewport().getScreenHeight() - scoreLabel.getHeight());
        stage.addActor(scoreLabel);

        slapMusic = Gdx.audio.newMusic(Gdx.files.internal("slap.mp3"));
        slapMusic.setLooping(true);
        slapMusic.play();


    }


    @Override
    public void createActors() {
        //call addMole
        addMole();
        Actor clouds = ActorUtils.createActorFromImage("cloud.jpg");
        stage.addActor(clouds);
    }

    public void addMole() {
        final Mole mole = new Mole();
        mole.moleActor = ActorUtils.createActorFromImage("animal-158236_1280.png");
        mole.moleActor.setSize(mole.moleActor.getWidth()/3, mole.moleActor.getHeight()/3);
        mole.moleActor.setSize(100,100);
        mole.moleActor.setPosition(
                stage.getViewport().getScreenWidth()/2 - mole.moleActor.getWidth()/2,
                stage.getViewport().getScreenHeight()/2 - mole.moleActor.getHeight()/2);
        mole.xMove = MathUtils.random(MAX_MOVE) - MAX_MOVE/2;
        mole.yMove = MathUtils.random(MAX_MOVE) - MAX_MOVE/2;
        mole.moleActor.addListener(new ActorGestureListener() {
               @Override
               public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                   // Stop any other actions
                   mole.moleActor.remove();
                   score++;
                   scoreLabel.setText("Score: " + score);
                   if (score % 10 == 0) {
                       //nextLevel();
                   }
               }
           });

        mole.moleActor.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if (mole.moleActor.getX() + mole.xMove < 0) {
                    mole.xMove = -mole.xMove;
                }
                if (mole.moleActor.getX() + mole.moleActor.getWidth() + mole.xMove > stage.getViewport().getScreenWidth()) {
                    mole.xMove = -mole.xMove;
                }
                if (mole.moleActor.getY() + mole.yMove < 0) {
                    mole.yMove = -mole.yMove;
                }
                if (mole.moleActor.getY() + mole.moleActor.getHeight() + mole.yMove > stage.getViewport().getScreenHeight()) {
                    mole.yMove = -mole.yMove;
                }
                mole.moleActor.moveBy(mole.xMove, mole.yMove);
                return false;
            }
        });

        stage.addActor(mole.moleActor);

    }

    @Override
    public void setInputForActors() {

    }

    @Override
    public void setActionsForActors() {
    } private  void nextLevel() {
        MAX_MOVE++;
        
    }

    @Override
    protected void calledEveryFrame() {

        if(numFrames % 115 == 0){
            addMole();

        }
    }
}
