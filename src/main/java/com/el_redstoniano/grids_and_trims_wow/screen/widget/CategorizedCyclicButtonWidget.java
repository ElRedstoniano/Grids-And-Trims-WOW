package com.el_redstoniano.grids_and_trims_wow.screen.widget;

import com.el_redstoniano.grids_and_trims_wow.screen.widget.categories.EnumSubcategory;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CategorizedCyclicButtonWidget<T extends EnumSubcategory> extends ListNamedWidget {
    protected Screen screen;
    protected Text name;
    private final MinecraftClient client;
    private final CyclingButtonWidget<T> predicatesCyclingButton;

    public CategorizedCyclicButtonWidget(Text name, @Nullable List<OrderedText> description, MinecraftClient client,
                                         @Nullable CyclingButtonWidget<T> predicatesCyclingButton) {
        super(description, name, client);
        //this.screen = screen;
        this.name = name;
        this.client = client;
        this.predicatesCyclingButton = predicatesCyclingButton;
        //this.predicatesCyclingButton = CyclingButtonWidget.<T>builder(t -> Text.translatable(t.getTranslationKey())).values(PredicateTypes.values()).build();
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
        return List.of(predicatesCyclingButton);
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickProgress) {
        //context.drawCenteredTextWithShadow(client.textRenderer, this.name, x + entryWidth / 2, y + 5, Colors.WHITE);
        context.drawWrappedTextWithShadow(client.textRenderer, this.name, (x + entryWidth / 2) - client.textRenderer.getWidth(name), y + 5, 175, Colors.WHITE);
        if (predicatesCyclingButton != null) {
            this.predicatesCyclingButton.setX(x);
            this.predicatesCyclingButton.setY(y + predicatesCyclingButton.getHeight() + 20);
            this.predicatesCyclingButton.render(context, mouseX, mouseY, tickProgress);
        }
    }
}
