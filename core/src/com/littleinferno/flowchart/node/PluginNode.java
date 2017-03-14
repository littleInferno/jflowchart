package com.littleinferno.flowchart.node;


import com.littleinferno.flowchart.codegen.BaseCodeGenerator;
import com.littleinferno.flowchart.pin.Pin;
import com.littleinferno.flowchart.plugin.NodePluginManager;

public class PluginNode extends Node {

    private NodePluginManager.PluginNodeHandle handle;

    public PluginNode(NodePluginManager.PluginNodeHandle handle) {
        this.handle = handle;
        addPins(handle.pins);
        handle.init.call(this);
    }

    public PluginNode(NodeHandle nodeHandle) {
        super(nodeHandle);
    }

    @Override
    public String gen(BaseCodeGenerator builder, Pin with) {
        return (String) handle.codegen.call(this);
    }

    NodePluginManager.PluginNodeHandle getPluginHandle() {
        return handle;
    }
}
