package com.hampton.game.demo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.hampton.game.GameScreen;
import com.hampton.game.utils.ActorUtils;

/**
 * Created by turnerd on 10/13/17.
 */
public class DemoGame extends GameScreen {

    private Actor actorFromImage;
    private Actor buttonFromText;

    @Override
    public void initialize() {

    }

    @Override
    public void createActors() {
        backgroundColor = new Color(0, 1, 1, 1);
        actorFromImage = ActorUtils.createActorFromImage("badlogic.jpg");
        actorFromImage.setPosition(70, 70);
        buttonFromText = ActorUtils.createButtonFromText("Go back to menu", new Color(1, 1, 1, 1));
        stage.addActor(actorFromImage);
        stage.addActor(buttonFromText);
    }

    @Override
    public void setInputForActors() {
        buttonFromText.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gotoScreen("Menu");
            }
        });
    }

    @Override
    public void setActionsForActors() {
        actorFromImage.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                actorFromImage.rotateBy(1);
                return false;
            }
        });
    }

    @Override
    protected void calledEveryFrame() {

    }
}
