package com.littleinferno.flowchart.node;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.littleinferno.flowchart.value.BoolValue;
import com.littleinferno.flowchart.value.Value;

public class BoolNode extends Node {

    private final SelectBox box;

    public BoolNode(Skin skin) {
        super("Bool", true, skin);

        addDataOutputPin(Value.Type.BOOL, "data");

        box = new SelectBox(skin);
        box.setItems("True", "False");
        left.add(box).expandX().fillX().minWidth(0);
    }

    @Override
    public void eval() throws Exception {
        getPin("data").setValue(new BoolValue(box.getSelectedIndex() == 0));
    }
}
