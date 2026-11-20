package com.el_redstoniano.grids_and_trims_wow.screen.widget.predicates;

import com.el_redstoniano.grids_and_trims_wow.screen.widget.ListNamedWidget;
import com.el_redstoniano.grids_and_trims_wow.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PredicateWidget extends ListNamedWidget {

    private final Component name;
    private final EditBox textFieldWidget;
    //private final ScrollableTextFieldWidget scrollableTextFieldWidget;

    public PredicateWidget(@Nullable List<FormattedCharSequence> description, Component name, Minecraft client) {
        super(description, name, client);
        this.name = name;
        this.textFieldWidget = new EditBox(client.font, 0, 0, 20, 200, name);
        this.textFieldWidget.insertText(Integer.toString(23));
        this.textFieldWidget.setTooltip(Tooltip.create(Component.literal("a")));
        this.children.add(this.textFieldWidget);
    }

    @Override
    public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
        super.extractContent(graphics, mouseX, mouseY, hovered, a);
        textFieldWidget.setX(getX() + textFieldWidget.getWidth() / 2 - 20 / 2);
        RenderUtils.setClickableWidgetCentered(textFieldWidget, getX(), textFieldWidget.getWidth(), textFieldWidget.getWidth());
        this.textFieldWidget.setY(getY());
        textFieldWidget.extractRenderState(graphics, mouseX, mouseY, a);
    }
}
