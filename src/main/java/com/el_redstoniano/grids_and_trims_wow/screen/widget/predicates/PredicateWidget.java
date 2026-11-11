package com.el_redstoniano.grids_and_trims_wow.screen.widget.predicates;

import com.el_redstoniano.grids_and_trims_wow.screen.widget.ListNamedWidget;
import com.el_redstoniano.grids_and_trims_wow.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PredicateWidget extends ListNamedWidget {

    private final Text name;
    private final TextFieldWidget textFieldWidget;
    //private final ScrollableTextFieldWidget scrollableTextFieldWidget;

    public PredicateWidget(@Nullable List<OrderedText> description, Text name, MinecraftClient client) {
        super(description, name, client);
        this.name = name;
        this.textFieldWidget = new TextFieldWidget(client.textRenderer, 0, 0, 20, 200, name);
        this.textFieldWidget.setText(Integer.toString(23));
        this.textFieldWidget.setTooltip(Tooltip.of(Text.literal("a")));
        this.children.add(this.textFieldWidget);
    }



    /*@Override
    public List<? extends Selectable> selectableChildren() {
        return ImmutableList.of(new Selectable() {
            @Override
            public SelectionType getType() {
                return Selectable.SelectionType.HOVERED;
            }

            @Override
            public void appendNarrations(NarrationMessageBuilder builder) {
                builder.put(NarrationPart.TITLE, name);
            }
        });
    }

    @Override
    public List<? extends Element> children() {
        return List.of();
    }*/



    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickProgress) {
        drawName(context, y, x - 90);
        //this.textFieldWidget.setX(x + entryWidth / 2 - 20 / 2);
        RenderUtils.setClickableWidgetCentered(textFieldWidget, x, entryWidth, textFieldWidget.getWidth());
        this.textFieldWidget.setY(y);
        textFieldWidget.render(context, mouseX, mouseY, tickProgress);

    }
}
