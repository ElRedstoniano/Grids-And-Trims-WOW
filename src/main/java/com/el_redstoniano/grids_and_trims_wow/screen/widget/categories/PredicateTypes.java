package com.el_redstoniano.grids_and_trims_wow.screen.widget.categories;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public enum PredicateTypes implements EnumSubcategory {
    GRIDS("type.grids"),
    ADVANCED("type.advanced");

    private final String name;

    private PredicateTypes(String name) {
        this.name = name;
    }

    @Override
    public ScreenCategories getParentCategory() {
        return ScreenCategories.PREDICATES;
    }

    public String getTranslationKey() {
        return getParentCategory().getCategoryTranslationKey() + "." + name;
    }

    public MutableComponent getTranslationText() { return Component.translatable(getTranslationKey()); }
}