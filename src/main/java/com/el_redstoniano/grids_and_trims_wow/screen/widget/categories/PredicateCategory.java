package com.el_redstoniano.grids_and_trims_wow.screen.widget.categories;

import net.minecraft.text.Text;

public enum PredicateCategory implements EnumSubcategory {
    X_AXIS("x_axis"),
    Y_AXIS("y_axis"),
    Z_AXIS("z_axis");

    private String name;

    private PredicateCategory(String name) {
        this.name = name;
    }

    @Override
    public String getTranslationKey() { return getParentCategory().getCategoryTranslationKey() + "." + name; }

    public Text getTranslationText() { return Text.translatable(getTranslationKey()); }

    @Override
    public ScreenCategories getParentCategory() {
        return ScreenCategories.PREDICATES;
    }
}
