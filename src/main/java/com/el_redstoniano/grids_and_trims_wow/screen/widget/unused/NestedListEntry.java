package com.el_redstoniano.grids_and_trims_wow.screen.widget.unused;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;

import java.util.List;

public class NestedListEntry extends ElementListWidget.Entry<NestedListEntry> {
    protected List<ClickableWidget> widgets;
    protected Screen screen;

    public NestedListEntry(List<ClickableWidget> widgets, Screen screen) {
        this.widgets = widgets;
        this.screen = screen;
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return List.of();
    }

    @Override
    public List<? extends Element> children() {
        return List.of();
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickProgress) {
        int i = 0;
        int j = this.screen.width / 2 - 155;
        for (ClickableWidget clickableWidget : this.widgets) {
            clickableWidget.setPosition(j + i, y);
            clickableWidget.render(context, mouseX, mouseY, tickProgress);
            i += 160;
        }
    }
}
