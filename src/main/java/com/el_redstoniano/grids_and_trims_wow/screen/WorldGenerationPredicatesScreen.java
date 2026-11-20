package com.el_redstoniano.grids_and_trims_wow.screen;

import com.el_redstoniano.grids_and_trims_wow.GridsAndTrims;
import com.el_redstoniano.grids_and_trims_wow.screen.widget.AbstractCategorizedWidget;
import com.el_redstoniano.grids_and_trims_wow.screen.widget.CategorizedCyclicButtonWidget;
import com.el_redstoniano.grids_and_trims_wow.screen.widget.categories.PredicateCategory;
import com.el_redstoniano.grids_and_trims_wow.screen.widget.categories.PredicateTypes;
import com.el_redstoniano.grids_and_trims_wow.screen.widget.predicates.PredicateWidget;
import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.*;
import java.util.stream.Collectors;

public class WorldGenerationPredicatesScreen extends Screen {
    private static final Component WORLD_PREDICATES_SCREEN_TITLE = Component.translatable("selectWorld.grids_and_trims.modify_predicates.title");

    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    private PredicatesListWidget predicatesListWidget;
    private final Screen parent;

    protected WorldGenerationPredicatesScreen(Screen parentScreen) {
        super(WORLD_PREDICATES_SCREEN_TITLE);
        this.parent = parentScreen;
    }

    @Override
    protected void init() {
        this.layout.addTitleHeader(WORLD_PREDICATES_SCREEN_TITLE, this.font);
        predicatesListWidget = this.layout.addToContents(new PredicatesListWidget());
        //directionalBodyLayoutWidget.add();
        LinearLayout directionalLayoutWidget = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        directionalLayoutWidget.addChild(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose()).build());
        directionalLayoutWidget.addChild(Button.builder(CommonComponents.GUI_CANCEL, button -> this.onClose()).build());
        this.layout.visitWidgets(child -> {
            child.setTabOrderGroup(1);
            this.addRenderableWidget(child);
        });
        this.repositionElements();
    }

    //private void onCloseScreen() {}

    public void onClose() {
        this.minecraft.gui.setScreen(this.parent);
    }

    protected void repositionElements() {
        this.layout.arrangeElements();
        if (this.predicatesListWidget != null) {
            this.predicatesListWidget.updateSize(this.width, this.layout);
        }

    }

    public class PredicatesListWidget extends ContainerObjectSelectionList<AbstractCategorizedWidget> {
        public PredicatesListWidget() {
            super(Minecraft.getInstance(), WorldGenerationPredicatesScreen.this.width,
                    WorldGenerationPredicatesScreen.this.layout.getContentHeight(), WorldGenerationPredicatesScreen.this.layout.getHeaderHeight(), 24 * 4);
            //final HashMap<AbstractCategorizedWidget.Category, Map<AbstractCategorizedWidget.Key, AbstractCategorizedWidget>> map = Maps.newHashMap();
            final HashMap<PredicateCategory, Map<AbstractCategorizedWidget.Key<PredicateCategory>, AbstractCategorizedWidget>> map = Maps.newHashMap();

            PredicateCategory category = PredicateCategory.X_AXIS;
            AbstractCategorizedWidget.Key<PredicateCategory> key = new AbstractCategorizedWidget.Key<>("", category);

            Map<AbstractCategorizedWidget.Key<PredicateCategory>, AbstractCategorizedWidget> widgetMap = new HashMap<>() {
                //{put(key, new );}
            };

            //Component textName = Component.translatable(category.getTranslationKey()).withStyle(ChatFormatting.BOLD, ChatFormatting.YELLOW);

            //this.addEntry(new TextCategoryWidget(textName, null, WorldGenerationPredicatesScreen.this.client));
            //this.addEntry(new CategorizedCyclicButtonWidget(textName, null, WorldGenerationPredicatesScreen.this.client));
            //this.addEntry(new CategorizedCyclicButtonWidget(Text.of("aa"), null, WorldGenerationPredicatesScreen.this.client, null));
            this.addEntry(new CategorizedCyclicButtonWidget<PredicateTypes>(Component.nullToEmpty("aa"), null, Minecraft.getInstance(),
                    CycleButton.builder(PredicateTypes::getTranslationText, PredicateTypes.GRIDS)
                            .withValues( PredicateTypes.values())
                            .create(0, 0, 210, 20, Component.nullToEmpty("B"),
                                    (button, value) -> GridsAndTrims.LOGGER.info("a"))));
            //this.addEntry(new PredicateWidget(null, Text.literal("sddsd"), WorldGenerationPredicatesScreen.this.client));
            //this.addEntry(new PredicateWidget(null, Component.literal("2"), Minecraft.getInstance()));
        }

        @Override // Maybe is this one the right one?
        public void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float deltaTicks) {
            super.extractWidgetRenderState(graphics, mouseX, mouseY, deltaTicks);
            AbstractCategorizedWidget abstractCategorizedWidget = this.getHovered();
            if (abstractCategorizedWidget != null && abstractCategorizedWidget.description != null) {
                graphics.setTooltipForNextFrame(abstractCategorizedWidget.description, mouseX, mouseY);
            }
        }
    }

    /*public class PredicateCategorizedWidget extends AbstractCategorizedWidget{

    }*/

    @FunctionalInterface
    @Environment(value= EnvType.CLIENT)
    static interface PredicateWidgetFactory<T extends AbstractCategorizedWidget.Key> {
        public AbstractCategorizedWidget create(Component var1, List<FormattedCharSequence> var2, String var3, T var4);
    }
}
