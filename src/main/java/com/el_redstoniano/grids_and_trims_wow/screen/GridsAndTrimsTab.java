package com.el_redstoniano.grids_and_trims_wow.screen;

import com.el_redstoniano.grids_and_trims_wow.mixin.screen.CreateWorldScreenMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.text.Text;

public class GridsAndTrimsTab extends GridScreenTab {
    private static final Text GRIDS_AND_TRIMS_TITLE_TEXT = Text.translatable("createWorld.tab.grids_and_trims.title");
    private static final Text MODIFY_PREDICATES_BUTTON_TITLE_TEXT = Text.translatable("selectWorld.grids_and_trims.modify_predicates");
    private static final Text DIMENSION_PREDICATES_BUTTON_TITLE_TEXT = Text.translatable("selectWorld.grids_and_trims.manage_dimension_predicates");

    protected MinecraftClient client;
    public GridsAndTrimsTab(CreateWorldScreen currentScreen) {
        super(GRIDS_AND_TRIMS_TITLE_TEXT);

        client = MinecraftClient.getInstance();
        GridWidget.Adder adder = this.grid.setRowSpacing(8).createAdder(1);
        adder.add(ButtonWidget.builder(MODIFY_PREDICATES_BUTTON_TITLE_TEXT, (button) -> {
            client.setScreen(new WorldGenerationPredicatesScreen(currentScreen));
        }).width(210).build());
        adder.add(ButtonWidget.builder(DIMENSION_PREDICATES_BUTTON_TITLE_TEXT, (button) -> {
            client.setScreen(new WorldGenerationPredicatesScreen(currentScreen));
        }).width(210).build());
    }


}
