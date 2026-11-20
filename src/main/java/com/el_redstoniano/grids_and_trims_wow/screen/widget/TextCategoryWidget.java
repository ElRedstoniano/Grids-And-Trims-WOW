package com.el_redstoniano.grids_and_trims_wow.screen.widget;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class TextCategoryWidget extends ListNamedWidget {
    public Component name;
    private final Minecraft client;

    public TextCategoryWidget(Component name, @Nullable List<FormattedCharSequence> description, Minecraft client) {
        super(description, name, client);
        this.name = name;
        this.client = client;
    }

    @Override
    public @NonNull List<? extends NarratableEntry> narratables() {
        return ImmutableList.of(new NarratableEntry() {
            @Override
            public void updateNarration(@NonNull NarrationElementOutput output) {
                output.add(NarratedElementType.TITLE, name);
            }

            @Override
            public @NonNull NarrationPriority narrationPriority() {
                return NarrationPriority.NONE;
            }
        });
    }

    @Override
    public @NonNull List<? extends GuiEventListener> children() {
        return List.of();
    }

    @Override
    public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float tickProgress) {
        //super.extractContent(graphics, mouseX, mouseY, hovered, a);
        //context.drawCenteredTextWithShadow(client.textRenderer, this.name, x + entryWidth / 2, y + 5, Colors.WHITE);
        graphics.textWithWordWrap(client.font, this.name, (getX() + getWidth() / 2) - client.font.width(name),
                getY() + 5, 175, CommonColors.WHITE);
    }
}
