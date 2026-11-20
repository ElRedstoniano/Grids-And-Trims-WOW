package com.el_redstoniano.grids_and_trims_wow.screen.widget.categories;

import net.minecraft.client.renderer.feature.TextFeatureRenderer;
import net.minecraft.network.chat.Component;

public enum PredicateCategory implements EnumSubcategory {
    X_AXIS("x_axis"),
    Y_AXIS("y_axis"),
    Z_AXIS("z_axis");

    private final String name;

    private PredicateCategory(String name) {
        this.name = name;
    }

    @Override
    public String getTranslationKey() { return getParentCategory().getCategoryTranslationKey() + "." + name; }

    public Component getTranslationText() { return Component.translatable(getTranslationKey()); }

    @Override
    public ScreenCategories getParentCategory() {
        return ScreenCategories.PREDICATES;
    }
}
