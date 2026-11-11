package com.el_redstoniano.grids_and_trims_wow.screen;

import com.el_redstoniano.grids_and_trims_wow.GridsAndTrims;
import com.el_redstoniano.grids_and_trims_wow.screen.widget.AbstractCategorizedWidget;
import com.el_redstoniano.grids_and_trims_wow.screen.widget.CategorizedCyclicButtonWidget;
import com.el_redstoniano.grids_and_trims_wow.screen.widget.TextCategoryWidget;
import com.el_redstoniano.grids_and_trims_wow.screen.widget.categories.PredicateCategory;
import com.el_redstoniano.grids_and_trims_wow.screen.widget.categories.PredicateTypes;
import com.el_redstoniano.grids_and_trims_wow.screen.widget.predicates.PredicateWidget;
import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldGenerationPredicatesScreen extends Screen {
    private static final Text WORLD_PREDICATES_SCREEN_TITLE = Text.translatable("selectWorld.grids_and_trims.modify_predicates.title");

    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
    private PredicatesListWidget predicatesListWidget;
    private final Screen parent;

    protected WorldGenerationPredicatesScreen(Screen parentScreen) {
        super(WORLD_PREDICATES_SCREEN_TITLE);
        this.parent = parentScreen;
    }

    @Override
    protected void init() {
        this.layout.addHeader(WORLD_PREDICATES_SCREEN_TITLE, this.textRenderer);
        predicatesListWidget = this.layout.addBody(new PredicatesListWidget());
        //directionalBodyLayoutWidget.add();
        DirectionalLayoutWidget directionalLayoutWidget = this.layout.addFooter(DirectionalLayoutWidget.horizontal().spacing(8));
        directionalLayoutWidget.add(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close()).build());
        directionalLayoutWidget.add(ButtonWidget.builder(ScreenTexts.CANCEL, button -> this.close()).build());
        this.layout.forEachChild(child -> {
            child.setNavigationOrder(1);
            this.addDrawableChild(child);
        });
        this.refreshWidgetPositions();
    }

    //private void onCloseScreen() {}

    public void close() {
        if (client != null) {
            client.setScreen(this.parent);
        }
    }

    protected void refreshWidgetPositions() {
        this.layout.refreshPositions();
        if (this.predicatesListWidget != null) {
            this.predicatesListWidget.position(this.width, this.layout);
        }

    }

    public class PredicatesListWidget extends ElementListWidget<AbstractCategorizedWidget> {
        public PredicatesListWidget() {
            super(MinecraftClient.getInstance(), WorldGenerationPredicatesScreen.this.width,
                    WorldGenerationPredicatesScreen.this.layout.getContentHeight(), WorldGenerationPredicatesScreen.this.layout.getHeaderHeight(), 24 * 4);
            //final HashMap<AbstractCategorizedWidget.Category, Map<AbstractCategorizedWidget.Key, AbstractCategorizedWidget>> map = Maps.newHashMap();
            final HashMap<PredicateCategory, Map<AbstractCategorizedWidget.Key<PredicateCategory>, AbstractCategorizedWidget>> map = Maps.newHashMap();

            PredicateCategory category = PredicateCategory.X_AXIS;
            AbstractCategorizedWidget.Key<PredicateCategory> key = new AbstractCategorizedWidget.Key<>("", category);

            Map<AbstractCategorizedWidget.Key<PredicateCategory>, AbstractCategorizedWidget> widgetMap = new HashMap<>() {
                //{put(key, new );}
            };

            Text textName = Text.translatable(category.getTranslationKey()).formatted(Formatting.BOLD, Formatting.YELLOW);

            //this.addEntry(new TextCategoryWidget(textName, null, WorldGenerationPredicatesScreen.this.client));
            //this.addEntry(new CategorizedCyclicButtonWidget(textName, null, WorldGenerationPredicatesScreen.this.client));
            //this.addEntry(new CategorizedCyclicButtonWidget(Text.of("aa"), null, WorldGenerationPredicatesScreen.this.client, null));
            this.addEntry(new CategorizedCyclicButtonWidget<PredicateTypes>(Text.of("aa"), null, WorldGenerationPredicatesScreen.this.client,
                    CyclingButtonWidget.builder(PredicateTypes::getTranslationText)
                            .values(PredicateTypes.values())
                            .build(0, 0, 210, 20, Text.of("B"),
                                    (button, value) -> GridsAndTrims.LOGGER.info("a")/*button.setValue(value)*/))); // GridsAndTrims.LOGGER.info("a")).
            //this.addEntry(new PredicateWidget(null, Text.literal("sddsd"), WorldGenerationPredicatesScreen.this.client));
            //this.addEntry(new PredicateWidget(null, Text.literal("2"), WorldGenerationPredicatesScreen.this.client));


            //map.put(category, )
            /*List<AbstractCategorizedWidget> category1 = new ArrayList<>(
                    new
            );*/

        }

        @Override
        public void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
            super.renderWidget(context, mouseX, mouseY, deltaTicks);
            AbstractCategorizedWidget abstractCategorizedWidget = this.getHoveredEntry();
            if (abstractCategorizedWidget != null && abstractCategorizedWidget.description != null) {
                context.drawTooltip(abstractCategorizedWidget.description, mouseX, mouseY);
            }
        }
    }

    /*public class PredicateCategorizedWidget extends AbstractCategorizedWidget{

    }*/

    @FunctionalInterface
    @Environment(value= EnvType.CLIENT)
    static interface PredicateWidgetFactory<T extends AbstractCategorizedWidget.Key> {
        public AbstractCategorizedWidget create(Text var1, List<OrderedText> var2, String var3, T var4);
    }
}
