package com.el_redstoniano.grids_and_trims_wow.screen.widget;

import com.el_redstoniano.grids_and_trims_wow.screen.widget.categories.EnumSubcategory;
import com.google.common.collect.ImmutableList;
/*import net.minecraft.client.MinecraftClient;
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
import net.minecraft.util.Colors;*/
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class CategorizedCyclicButtonWidget<T extends EnumSubcategory> extends ListNamedWidget {
    protected Screen screen;
    protected Component name;
    private final Minecraft client;
    private final CycleButton<T> predicatesCyclingButton;

    public CategorizedCyclicButtonWidget(Component name, @Nullable List<FormattedCharSequence> description, Minecraft client,
                                         @Nullable CycleButton<T> predicatesCyclingButton) {
        super(description, name, client);
        //this.screen = screen;
        this.name = name;
        this.client = client;
        this.predicatesCyclingButton = predicatesCyclingButton;
        //this.predicatesCyclingButton = CyclingButtonWidget.<T>builder(t -> Text.translatable(t.getTranslationKey())).values(PredicateTypes.values()).build();
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
                return NarratableEntry.NarrationPriority.NONE;
            }
        });
    }

    @Override
    public @NonNull List<? extends GuiEventListener> children() {
        return List.of(predicatesCyclingButton);
    }

    @Override
    public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float tickProgress) {
        //super.extractContent(graphics, mouseX, mouseY, hovered, a);
        //context.drawCenteredTextWithShadow(client.textRenderer, this.name, x + entryWidth / 2, y + 5, Colors.WHITE);
        graphics.textWithWordWrap(client.font, this.name, (getX() + getWidth() / 2) - client.font.width(name),
                getY() + 5, 175, CommonColors.WHITE);
        if (predicatesCyclingButton != null) {
            this.predicatesCyclingButton.setX(getX());
            this.predicatesCyclingButton.setY(getY() + predicatesCyclingButton.getHeight() + 20);
            this.predicatesCyclingButton.extractRenderState(graphics, mouseX, mouseY, tickProgress);
        }
    }
}
