package com.littleinferno.flowchart.scene;


import com.littleinferno.flowchart.node.NodeManager;
import com.littleinferno.flowchart.project.Project;

public class MainScene extends Scene {

    @SuppressWarnings("WeakerAccess")
    public MainScene(SceneHandle sceneHandle, Project project) {
        super(sceneHandle, project);
    }

    @Override
    public String getType() {
        return "main";
    }

    @SuppressWarnings("unused")
    public MainScene(NodeManager.NodeManagerHandle nodeManager, Project project) {
        this(new SceneHandle(nodeManager, "main", false), project);
    }
}
