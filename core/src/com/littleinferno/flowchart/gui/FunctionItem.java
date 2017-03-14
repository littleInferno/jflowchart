package com.littleinferno.flowchart.gui;

import com.kotcrab.vis.ui.widget.MenuItem;
import com.littleinferno.flowchart.function.Function;
import com.littleinferno.flowchart.util.EventWrapper;

public class FunctionItem extends DropItem {

    private final Function function;

    public FunctionItem(Function fun) {
        function = fun;
    }

    @Override
    public void init(com.littleinferno.flowchart.scene.Scene scene) {

        if (scene != function.getScene()) {
        //    scene.getNodeManager()
                   // .createNode(FunctionCallNode.class, codegen)
               //     .setPosition(FunctionItem.this.getX(), FunctionItem.this.getY());
            return;
        }

        scene.addActor(this);

        addItem(new MenuItem("call", new EventWrapper((event, actor) -> getStage()
             //   .getNodeManager()
          //      .createNode(FunctionCallNode.class, codegen)
          //      .setPosition(FunctionItem.this.getX(), FunctionItem.this.getY())
        )));

        addSeparator();

        addItem(new MenuItem("return", new EventWrapper((event, actor) -> getStage()
            //    .getNodeManager()
            //    .createNode(FunctionReturnNode.class, codegen)
            //    .setPosition(FunctionItem.this.getX(), FunctionItem.this.getY())
        )));

    }
}
