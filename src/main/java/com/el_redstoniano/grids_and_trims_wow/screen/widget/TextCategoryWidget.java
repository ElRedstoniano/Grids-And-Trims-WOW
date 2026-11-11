package com.el_redstoniano.grids_and_trims_wow.screen.widget;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TextCategoryWidget extends ListNamedWidget {
    public Text name;
    private final MinecraftClient client;

    public TextCategoryWidget(Text name, @Nullable List<OrderedText> description, MinecraftClient client) {
        super(description, name, client);
        this.name = name;
        this.client = client;
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return ImmutableList.of(new Selectable() {
            @Override
            public SelectionType getType() {
                return SelectionType.NONE;
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
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickProgress) {
        //context.drawCenteredTextWithShadow(client.textRenderer, this.name, x + entryWidth / 2, y + 5, Colors.WHITE);
        context.drawWrappedTextWithShadow(client.textRenderer, this.name, (x + entryWidth / 2) - client.textRenderer.getWidth(name), y + 5, 175, Colors.WHITE);
    }
}
