package com.littleinferno.flowchart.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.littleinferno.flowchart.gui.function.FunctionTable;
import com.littleinferno.flowchart.variable.VariableManager;


public class ControlTable extends VisTable {

    public ControlTable() {

        final VisTable nodeTable = new NodeTable();


        final VisTable variableTable = new VisTable();

        VisTextButton create = new VisTextButton("create");
        create.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                VariableManager.instance.createVariable();
            }
        });
        variableTable.add(create).growX().row();
        variableTable.add(VariableManager.instance.getVarTable()).grow().row();
        variableTable.addSeparator();
        variableTable.add(VariableManager.instance.getDetailsTable()).growX().row();
        variableTable.addSeparator();


        final VisTable functionTable = new FunctionTable();

        final VisTextButton nodes = new VisTextButton("node", "toggle");
        final VisTextButton variables = new VisTextButton("variable", "toggle");
        final VisTextButton functions = new VisTextButton("function", "toggle");

        ButtonGroup<VisTextButton> tabs = new ButtonGroup<VisTextButton>();
        tabs.setMinCheckCount(1);
        tabs.setMaxCheckCount(1);
        tabs.add(nodes);
        tabs.add(variables);
        tabs.add(functions);

        ChangeListener tabListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                nodeTable.setVisible(nodes.isChecked());
                variableTable.setVisible(variables.isChecked());
                functionTable.setVisible(functions.isChecked());
            }
        };

        nodes.addListener(tabListener);
        variables.addListener(tabListener);
        functions.addListener(tabListener);

        variables.setChecked(true);

        Stack container = new Stack();
        container.add(nodeTable);
        container.add(variableTable);
        container.add(functionTable);

        VisTable tabTable = new VisTable(true);
        tabTable.add(nodes).grow();
        tabTable.add(variables).grow();
        tabTable.add(functions).grow();

        add(tabTable).growX().row();
        addSeparator();
        add(container).grow();

        setBackground(VisUI.getSkin().getDrawable("window-bg"));
    }
}
