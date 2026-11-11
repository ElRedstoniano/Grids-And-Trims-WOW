package com.el_redstoniano.grids_and_trims_wow.screen.widget.categories;

public enum ScreenCategories {
    PREDICATES("grid_and_trims.widgets.category.predicate");

    private final String category;

    private ScreenCategories(String category) {
        this.category = category;
    }
    public String getCategoryTranslationKey() {
        return category;
    }
}
