package com.el_redstoniano.grids_and_trims_wow.screen.widget.unused;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class NestedListEntry extends ContainerObjectSelectionList.Entry<NestedListEntry> {
    protected List<AbstractWidget> widgets;
    protected Screen screen;

    public NestedListEntry(List<AbstractWidget> widgets, Screen screen) {
        this.widgets = widgets;
        this.screen = screen;
    }
    @Override
    public @NonNull List<? extends GuiEventListener> children() {
        return List.of();
    }
    @Override
    public @NonNull List<? extends NarratableEntry> narratables() {
        return List.of();
    }

    @Override
    public void extractContent(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
        int i = 0;
        int j = this.screen.width / 2 - 155;
        for (AbstractWidget clickableWidget : this.widgets) {
            clickableWidget.setPosition(j + i, getY());
            clickableWidget.extractRenderState(graphics, mouseX, mouseY, a);
            i += 160;
        }
    }
}
