package com.littleinferno.flowchart.node;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class BeginNode extends Node {
    public BeginNode(Skin skin) {
        super("Begin", false, skin);

        addExecutionOutputPin("start");

        Button button = new Button(skin);
        left.add(button).expandX().fillX().height(30);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                execute();
            }
        });
    }

    @Override
    public void execute() {
        Node next = getPin("start").getConnectionNode();

        if (next != null) {
            try {
                next.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
