package com.anti;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gui.DrawContext;

public class CalcScreen extends Screen {
    private TextFieldWidget displayField;
    String Tab = "";
    boolean Plus = false;
    boolean Minus = false;
    boolean Del = false;
    boolean Mod = false;
    String N1 = "";
    String N2 = "";
    int Itog;
    private void Button(int x, int y, String TAB) {
        addDrawableChild(ButtonWidget.builder(Text.literal(TAB), button -> {
            if (Plus || Minus || Del || Mod) {
                N2 += TAB;
                Tab += TAB;
                displayField.setText(Tab);
            } else {
                N1 += TAB;
                Tab += TAB;
                displayField.setText(Tab);
            };
        }).dimensions(x, y, 20, 20).build());
    }

    protected CalcScreen() {
        super(Text.literal("1"));
    }

    @Override
    protected void init() {
        int x = width / 2 - 100;
        int y = height / 2 - 100;

        displayField = new TextFieldWidget(textRenderer, x, y, 200, 20, Text.empty());
        displayField.setEditable(false);
        addDrawableChild(displayField);

        Button(x, y + 25, "1");
        Button(x + 25, y + 25, "2");
        Button(x + 50, y + 25, "3");
        Button(x, y + 50, "4");
        Button(x + 25, y + 50, "5");
        Button(x + 50, y + 50, "6");
        Button(x, y + 75, "7");
        Button(x + 25, y + 75, "8");
        Button(x + 50, y + 75, "9");
        Button(x + 75, y + 75, "0");

        addDrawableChild(ButtonWidget.builder(Text.literal("+"), button -> {           
            Plus = true;
            Tab += "+";
            displayField.setText(Tab);
        }).dimensions(x + 75, y + 25, 20, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("-"), button -> {           
            Minus = true;
            Tab += "-";
            displayField.setText(Tab);
        }).dimensions(x + 75, y + 50, 20, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("/"), button -> {           
            Del = true;
            Tab += "/";
            displayField.setText(Tab);
        }).dimensions(x + 100, y + 25, 20, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("*"), button -> {           
            Mod = true;
            Tab += "*";
            displayField.setText(Tab);
        }).dimensions(x + 100, y + 50, 20, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("="), button -> {           
            if (N1.isEmpty() || N2.isEmpty()) {
                displayField.setText("err");
                Minus = false;
                Plus = false;
                Mod = false;
                Del = false;
            } else {
                int num1 = Integer.parseInt(N1);
                int num2 = Integer.parseInt(N2);
                if (Plus) {
                    Plus = false;
                    Itog = num1 + num2;
                } else if (Minus) {
                    Minus = false;
                    Itog = num1 - num2;
                } else if (Del) {
                    Del = false;
                    Itog = num1 / num2;
                } else if (Mod) {
                    Mod = false;
                    Itog = num1 * num2;
                }
            }
            Tab += "=";
            displayField.setText(Tab);
            Tab += Itog;
            displayField.setText(Tab);
        }).dimensions(x + 100, y + 75, 20, 20).build());
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }
}