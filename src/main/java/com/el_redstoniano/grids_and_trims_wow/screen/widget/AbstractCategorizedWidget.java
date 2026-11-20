package com.el_redstoniano.grids_and_trims_wow.screen.widget;

import com.el_redstoniano.grids_and_trims_wow.screen.widget.categories.EnumSubcategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Environment(value= EnvType.CLIENT)
public abstract class AbstractCategorizedWidget extends ContainerObjectSelectionList.Entry<AbstractCategorizedWidget> implements GuiEventListener {

    @Nullable
    public final List<FormattedCharSequence> description;

    protected AbstractCategorizedWidget(@Nullable List<FormattedCharSequence> description) {
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
