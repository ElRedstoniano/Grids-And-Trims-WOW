package com.el_redstoniano.grids_and_trims_wow.screen.widget.categories;

import net.minecraft.text.Text;

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

    public Text getTranslationText() { return Text.translatable(getTranslationKey()); }
}