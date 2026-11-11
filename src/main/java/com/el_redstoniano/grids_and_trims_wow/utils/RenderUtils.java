package com.el_redstoniano.grids_and_trims_wow.utils;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;

public class RenderUtils {
    public static <T extends ClickableWidget> void setClickableWidgetCentered(T widget, int currentX, int entryWidth, int widgetWidth) {
        widget.setX(currentX + entryWidth / 2 - widgetWidth / 2);
    }
}
