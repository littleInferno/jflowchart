var NativeNode = com.littleinferno.flowchart.node.PluginNode;
var NativePin = com.littleinferno.flowchart.pin.Pin;
var NativeType = com.littleinferno.flowchart.DataType;
var NativeConnection = com.littleinferno.flowchart.Connection;
var NativeGenerator = com.littleinferno.flowchart.codegen.JSCodeGenerator;
var NativeCell = com.badlogic.gdx.scenes.scene2d.ui.Cell;
var NativeCell = com.badlogic.gdx.scenes.scene2d.ui.Cell;
var NativeTextField = com.kotcrab.vis.ui.widget.VisTextField;

var codegen = new NativeGenerator();

function exportNodes() {
    return [ifNode(), beginNode(), printNode(), integerNode(), floatNode(), stringNode()];
}

function ifNode() {
    return {
        name: "if node",
        title: "if",
        gen: function (node) {
            var conditionStr = node.getPin("condition").generate(codegen);
            var trueString = node.getPin("True").generate(codegen);
            var falseString = node.getPin("False").isConnect() ? node.getPin("False").generate(codegen) : "";

            var string;
            if (!(!falseString || 0 === falseString.length))
                string = codegen.makeIfElse(conditionStr,
                    codegen.makeBlock(trueString), codegen.makeBlock(falseString));
            else
                string = codegen.makeIf(conditionStr, codegen.makeBlock(trueString));

            return string;
        },
        pins: [
            {
                name: "ex in",
                type: NativeType.EXECUTION,
                connection: NativeConnection.INPUT
            },
            {
                name: "condition",
                type: NativeType.BOOL,
                connection: NativeConnection.INPUT
            },
            {
                name: "True",
                type: NativeType.EXECUTION,
                connection: NativeConnection.OUTPUT
            },
            {
                name: "False",
                type: NativeType.EXECUTION,
                connection: NativeConnection.OUTPUT
            },
        ]
    }
}

function beginNode() {
    return {
        name: "begin node",
        title: "begin",
        gen: function (node) {
            return node.getPin("start").isConnect() ? node.getPin("start").generate(codegen) : "";
        },
        pins: [
            {
                name: "start",
                type: NativeType.EXECUTION,
                connection: NativeConnection.OUTPUT
            }
        ],
        programstart: true,
        single: true,
        sceneType: "main",
        closable:false
    }
}

function printNode() {
    return {
        name: "print node",
        title: "print",
        gen: function (node) {

            var valStr = node.getPin("item").generate(codegen);

            var nextStr = node.getPin("ex out").isConnect() ? node.getPin("ex out").generate(codegen) : "";

            var format = "com.littleinferno.flowchart.jsutil.IO.print(" + valStr + ")";

            return codegen.makeStatement(format) + nextStr;
        },
        pins: [
            {
                name: "ex in",
                type: NativeType.EXECUTION,
                connection: NativeConnection.INPUT
            },
            {
                name: "ex out",
                type: NativeType.EXECUTION,
                connection: NativeConnection.OUTPUT
            },
            {
                name: "item",
                type: [NativeType.BOOL, NativeType.INT, NativeType.FLOAT, NativeType.STRING],
                connection: NativeConnection.INPUT
            },
        ],
    }
}

function integerNode() {
    return {
        name: "integer node",
        title: "int",
        init: function (node) {
            var field = new NativeTextField("");
            field.setName("field");
            field.setTextFieldFilter(function (textField, c) { return c >= '0' && c <= '9' || c == '+' || c == '-'; });
            node.getContainer().add(field).colspan(2).growX();
            node.pack();
        },
        gen: function (node) {
            return node.findActor("field").getText();
        },
        pins: [
            {
                name: "data",
                type: NativeType.INT,
                connection: NativeConnection.OUTPUT
            }
        ],
    }
}

function floatNode() {
    return {
        name: "float node",
        title: "float",
        init: function (node) {
            var field = new NativeTextField("");
            field.setName("field");
            field.setTextFieldFilter(function (textField, c) { return c >= '0' && c <= '9' || c == '.' || c == '+' || c == '-';});
        node.getContainer().add(field).colspan(2).growX();
        node.pack();
    },
        gen: function (node) {
            return node.findActor("field").getText();
        },
    pins: [
        {
            name: "data",
            type: NativeType.FLOAT,
            connection: NativeConnection.OUTPUT
        }
    ],
    }
}

function stringNode() {
    return {
        name: "string node",
        title: "string",
        init: function (node) {
            var field = new NativeTextField("");
            field.setName("field");
            node.getContainer().add(field).colspan(2).growX();
            node.pack();
        },
        gen: function (node) {
            return codegen.makeString(node.findActor("field").getText());
        },
        pins: [
            {
                name: "data",
                type: NativeType.FLOAT,
                connection: NativeConnection.OUTPUT
            }
        ],
    }
}