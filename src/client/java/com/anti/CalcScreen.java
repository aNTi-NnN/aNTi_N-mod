package com.anti;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gui.DrawContext;

public class CalcScreen extends Screen {
    private TextFieldWidget displayField;
    private StringBuilder Tab = new StringBuilder();
    
    private void Button(int x, int y, String TAB) {
        addDrawableChild(ButtonWidget.builder(Text.literal(TAB), button -> {
            Tab.append(TAB);
            displayField.setText(Tab.toString());
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

        Button(x, y + 30, "1");
        Button(x + 30, y + 30, "2");
        Button(x + 60, y + 30, "3");
        Button(x + 90, y + 30, "4");
        Button(x + 120, y + 30, "5");
        Button(x + 150, y + 30, "6");
        Button(x + 180, y + 30, "7");
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }
}