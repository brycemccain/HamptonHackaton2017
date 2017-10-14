package com.hampton.game.demo;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.hampton.game.GameScreen;
import com.hampton.game.utils.ActorUtils;

/**
 * Created by karat on 10/14/2017.
 */

public class homeScreen extends GameScreen {
    Actor buttonFromText;
    @Override
    public void initialize() {

    }

    @Override
    public void createActors() {

        buttonFromText = ActorUtils.createButtonFromText("Welcome to Whack-A-Mole! Click to go to Whack-A-Mole!",
                new Color(1, 1, 1, 1));
        stage.addActor(buttonFromText);
        backgroundColor = new Color(0, 0, 1, 1);
        buttonFromText.setPosition(
                stage.getViewport().getScreenWidth()/2 - buttonFromText.getWidth()/2,
                stage.getViewport().getScreenHeight()/2 - buttonFromText.getHeight()/2);

    }


    @Override
    public void setInputForActors() {

    }

    @Override
    public void setActionsForActors() {
        buttonFromText.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gotoScreen("Bounce");
            }
        });
    }


    @Override
    protected void calledEveryFrame() {

    }
}
