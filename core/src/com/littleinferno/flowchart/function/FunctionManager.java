package com.littleinferno.flowchart.function;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.kotcrab.vis.ui.util.adapter.ArrayListAdapter;
import com.kotcrab.vis.ui.widget.ListView;
import com.kotcrab.vis.ui.widget.VisTable;
import com.littleinferno.flowchart.codegen.BaseCodeGenerator;
import com.littleinferno.flowchart.project.Project;
import com.littleinferno.flowchart.util.BaseHandle;

import java.util.ArrayList;
import java.util.List;

public class FunctionManager {

    private ArrayList<Function> functions;
    private int counter;
    private UI ui;

    public FunctionManager() {
        functions = new ArrayList<>();
        counter = 0;
        ui = new UI(functions);
    }

    public FunctionManager(FunctionManagerHandle functionManagerHandle) {
        this();
        counter = functionManagerHandle.counter;

        Stream.of(functionManagerHandle.functionHandles)
                .map(Function.FunctionHandle.class::cast)
                .forEach(this::createFunction);
    }

    private Function createFunction(Function.FunctionHandle functionHandle) {
        Function function = new Function(functionHandle);

        functions.add(function);
        ui.update();

        return function;
    }

    public Function createFunction() {
        Function function = new Function("newFun" + counter++);

        functions.add(function);
        ui.update();

        function.init();
        Project.instance().getUiScene().pinToTabbedPane(function.getScene().getUiTab());

        return function;
    }

    void removeFunction(Function function) {
        function.destroy();

        Project.instance().getUiScene().unpinFromTabbedPane(function.getScene().getUiTab());

        functions.remove(function);
        ui.update();
    }

    public Function getFunction(String name) {
        return Stream.of(functions)
                .filter(value -> value.getName().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Cannot find function with name:\"" + name + "\""));
    }

    public String gen(BaseCodeGenerator builder) {
        return Stream.of(functions)
                .map(function -> function.gen(builder))
                .collect(Collectors.joining());
    }

    public FunctionManager.UI getUi() {
        return ui;
    }

    public FunctionManagerHandle getHandle() {
        return new FunctionManagerHandle(counter,
                Stream.of(functions).map(Function::getHandle).toList());
    }

    @SuppressWarnings("WeakerAccess")
    public static class UI {

        private ArrayListAdapter<Function, VisTable> functionalAdapter;
        private final VisTable detailsTable;
        private final VisTable funTable;

        public VisTable getFunTable() {
            return funTable;
        }

        public VisTable getDetailsTable() {
            return detailsTable;
        }

        void update() {
            functionalAdapter.itemsChanged();
        }

        UI(ArrayList<Function> functions) {
            functionalAdapter = new FunctionListAdapter(functions);

            detailsTable = new VisTable(true);
            funTable = new VisTable(true);

            ListView<Function> view = new ListView<>(functionalAdapter);

            view.setItemClickListener(item -> {
                detailsTable.clearChildren();
                detailsTable.add(item.getTable()).grow();
                Project.instance().getUiScene().pinToTabbedPane(item.getScene().getUiTab());
            });

            funTable.add(view.getMainTable()).grow().row();
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static class FunctionManagerHandle implements BaseHandle{
        int counter;
        List<Function.FunctionHandle> functionHandles;

        @SuppressWarnings("unused")
        public FunctionManagerHandle() {
        }

        public FunctionManagerHandle(int counter, List<Function.FunctionHandle> functionHandles) {
            this.counter = counter;
            this.functionHandles = functionHandles;
        }
    }
}
