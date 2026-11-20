package com.el_redstoniano.grids_and_trims_wow.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.tabs.GridLayoutTab;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;

public class GridsAndTrimsTab extends GridLayoutTab {
    private static final Component GRIDS_AND_TRIMS_TITLE_TEXT = Component.translatable("createWorld.tab.grids_and_trims.title");
    private static final Component MODIFY_PREDICATES_BUTTON_TITLE_TEXT = Component.translatable("selectWorld.grids_and_trims.modify_predicates");
    private static final Component DIMENSION_PREDICATES_BUTTON_TITLE_TEXT = Component.translatable("selectWorld.grids_and_trims.manage_dimension_predicates");

    protected Minecraft client;
    public GridsAndTrimsTab(CreateWorldScreen currentScreen) {
        super(GRIDS_AND_TRIMS_TITLE_TEXT);

        client = Minecraft.getInstance();
        GridLayout.RowHelper adder = ((GridLayout) this.getLayout()).rowSpacing(8).createRowHelper(1);
        adder.addChild(Button.builder(MODIFY_PREDICATES_BUTTON_TITLE_TEXT, (button) -> {
            client.setScreenAndShow(new WorldGenerationPredicatesScreen(currentScreen));
        }).width(210).build());
        adder.addChild(Button.builder(DIMENSION_PREDICATES_BUTTON_TITLE_TEXT, (button) -> {
            client.setScreenAndShow(new WorldGenerationPredicatesScreen(currentScreen));
        }).width(210).build());
    }


}
