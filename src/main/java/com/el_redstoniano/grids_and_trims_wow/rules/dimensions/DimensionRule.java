package com.el_redstoniano.grids_and_trims_wow.rules.dimensions;

import net.minecraft.resources.Identifier;

import java.util.Arrays;

@FunctionalInterface
public interface DimensionRule {
    boolean matches(Identifier identifier);

    default DimensionRule and(DimensionRule dimensionRule){
        return identifier -> this.matches(identifier) && dimensionRule.matches(identifier);
    }

    default DimensionRule or(DimensionRule dimensionRule){
        return identifier -> this.matches(identifier) || dimensionRule.matches(identifier);
    }

    default DimensionRule not(DimensionRule dimensionRule){
        return identifier -> !this.matches(identifier);
    }

    static DimensionRule none() {
        return context -> false;
    }

    static DimensionRule anyOf(DimensionRule... rules){
        return Arrays.stream(rules).reduce(none(), DimensionRule::or);
    }
}
