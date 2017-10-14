package com.hampton.game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.hampton.game.GameScreen;
import com.hampton.game.utils.ActorUtils;

//* Created by turnerd on 10/13/17.

public class WhackAMole extends GameScreen {

    private Actor mole;
    private float xMove;
    private float yMove;
    private  float MAX_MOVE = 40;
    private int score = 0;
    private Label.LabelStyle scoreStyle;
    private Label scoreLabel;

    private Music slapMusic;
    
    @Override
    public void initialize() {
        score = 0;
        scoreStyle = new Label.LabelStyle(new BitmapFont(), new Color(1,1,1,1));
        scoreStyle.font.getData().setScale(5);
        scoreLabel = new Label("Score: 0", scoreStyle);
        scoreLabel.setPosition(0, stage.getViewport().getScreenHeight() - scoreLabel.getHeight());
        stage.addActor(scoreLabel);

        mole.setSize(100,100);

        slapMusic = Gdx.audio.newMusic(Gdx.files.internal("slap.mp3"));
        slapMusic.setLooping(true);
        slapMusic.play();


    }

    

    @Override
    public void createActors() {
        //call addMole
        addMole();
        backgroundColor = new Color(0, 1, 1, 1);
        mole.setSize(mole.getWidth()/3, mole.getHeight()/3);
        mole.setPosition(
                stage.getViewport().getScreenWidth()/2 - mole.getWidth()/2,
                stage.getViewport().getScreenHeight()/2 - mole.getHeight()/2);
        stage.addActor(mole);
    }

    public void addMole() {
        mole = ActorUtils.createActorFromImage("animal-158236_1280.png");
        mole.setSize(mole.getWidth()/3, mole.getHeight()/3);
        mole.setPosition(
                stage.getViewport().getScreenWidth()/2 - mole.getWidth()/2,
                stage.getViewport().getScreenHeight()/2 - mole.getHeight()/2);
        stage.addActor(mole);
    }

    @Override
    public void setInputForActors() {
        mole.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Stop any other actions
                mole.clearActions();
                xMove = MathUtils.random(MAX_MOVE) - MAX_MOVE/2;
                yMove = MathUtils.random(MAX_MOVE) - MAX_MOVE/2;
                mole.remove();
                score ++ ;
                scoreLabel.setText("Score: " + score);
                if (score % 10 == 0) {
                    nextLevel();
                }

                mole.addAction(new Action() {
                    @Override
                    public boolean act(float delta) {
                        if (mole.getX() + xMove < 0) {
                            xMove = -xMove;
                        }
                        if (mole.getX() + mole.getWidth() + xMove > stage.getViewport().getScreenWidth()) {
                            xMove = -xMove;
                        }
                        if (mole.getY() + yMove < 0) {
                            yMove = -yMove;
                        }
                        if (mole.getY() + mole.getHeight() + yMove > stage.getViewport().getScreenHeight()) {
                            yMove = -yMove;
                        }
                        mole.moveBy(xMove, yMove);
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public void setActionsForActors() {
    } private  void nextLevel() {
        MAX_MOVE++;
        
    }

    @Override
    protected void calledEveryFrame() {
        if(Gdx.input.isTouched()) {
            // input.getY sets 0 as the top but actors use 0 for the bottom so we have to flip it
            Vector2 touchPoint = new Vector2(
                    Gdx.input.getX(),
                    stage.getViewport().getScreenHeight() - Gdx.input.getY());
            // Only move to the point if we didn't click on the mole
            if(!ActorUtils.actorContainsPoint(mole, touchPoint)) {
                mole.clearActions();
                // Move to touched location in 3 seconds
                mole.addAction(Actions.moveTo(
                        touchPoint.x - mole.getWidth() / 2,
                        touchPoint.y - mole.getHeight() / 2,
                        3,
                        Interpolation.circleOut));

            }
        }
        if(numFrames % 115 == 0){
            stage.addActor(mole);
        }
    }
}
