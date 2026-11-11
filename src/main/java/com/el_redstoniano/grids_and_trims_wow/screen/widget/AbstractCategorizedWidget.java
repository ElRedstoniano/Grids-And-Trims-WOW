package com.el_redstoniano.grids_and_trims_wow.screen.widget;

import com.el_redstoniano.grids_and_trims_wow.screen.widget.categories.EnumSubcategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.OrderedText;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Environment(value= EnvType.CLIENT)
public abstract class AbstractCategorizedWidget extends ElementListWidget.Entry<AbstractCategorizedWidget> {

    @Nullable
    public final List<OrderedText> description;

    protected AbstractCategorizedWidget(@Nullable List<OrderedText> description) {
        this.description = description;
    }

    public static class Key<T extends EnumSubcategory> {
        private final String name;
        private final T subcategory;
        //private MutableText displayText;

        public Key(String name, T enumSubcategory) {
            this.name = name;
            this.subcategory = enumSubcategory;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            return o instanceof Key && ((Key<?>) o).name.equals(this.name);
        }
        public int hashCode() { return this.name.hashCode(); }
        public String getTranslationKey() { return subcategory.getTranslationKey() + "." + name; }
        public String toString() { return this.name; }
    }
}
