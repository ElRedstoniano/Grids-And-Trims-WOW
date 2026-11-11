package com.el_redstoniano.grids_and_trims_wow.screen.widget;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ListNamedWidget extends AbstractCategorizedWidget {
    private final List<OrderedText> name;
    protected final List<ClickableWidget> children;
    protected final MinecraftClient client;

    public ListNamedWidget(@Nullable List<OrderedText> description, Text name, MinecraftClient client) {
        super(description);
        this.children = Lists.newArrayList();
        this.name = client.textRenderer.wrapLines(name, 175);
        this.client = client;
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return this.children;
    }

    @Override
    public List<? extends Element> children() {
        return this.children;
    }

    protected void drawName(DrawContext context, int x, int y) {
        if (this.name.size() == 1) {
            context.drawTextWithShadow(client.textRenderer, this.name.getFirst(), y, x + 5, -1);
        } else if (this.name.size() >= 2) {
            for (int i = 0; i < this.name.size(); i++) {
                context.drawTextWithShadow(client.textRenderer, this.name.get(i), y, x + 10 * i, -1);
            }
            //context.drawTextWithShadow(client.textRenderer, this.name.get(1), y, x + 10, -1);
        }
    }

    /*@Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickProgress) {

    }*/
}
