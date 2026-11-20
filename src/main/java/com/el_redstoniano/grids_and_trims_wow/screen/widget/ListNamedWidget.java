package com.el_redstoniano.grids_and_trims_wow.screen.widget;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public abstract class ListNamedWidget extends AbstractCategorizedWidget {
    private final List<FormattedCharSequence> name;
    protected final List<AbstractWidget> children;
    protected final Minecraft client;

    public ListNamedWidget(@Nullable List<FormattedCharSequence> description, Component name, Minecraft client) {
        super(description);
        this.children = Lists.newArrayList();
        this.name = client.font.split(name, 175);
        this.client = client;
    }

    @Override
    public @NonNull List<? extends NarratableEntry> narratables() {
        return this.children;
    }

    @Override
    public @NonNull List<? extends GuiEventListener> children() {
        return this.children;
    }

    @Override
    public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
        if (this.name.size() == 1) {
            graphics.text(client.font, this.name.getFirst(), getY(), getX() + 5, -1); // check if textWithBackdrop was the correct method
        } else if (this.name.size() >= 2) {
            for (int i = 0; i < this.name.size(); i++) {
                graphics.text(client.font, this.name.get(i), getY(), getY() + 10 * i, -1);
            }
            //context.drawTextWithShadow(client.textRenderer, this.name.get(1), y, x + 10, -1);
        }
    }
}
