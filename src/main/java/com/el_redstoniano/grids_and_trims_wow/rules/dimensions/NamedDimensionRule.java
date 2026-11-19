package com.el_redstoniano.grids_and_trims_wow.rules.dimensions;

import net.minecraft.util.Identifier;

import java.util.Set;

public class NamedDimensionRule implements DimensionRule{
    private final Set<Identifier> idsToCheck;

    public NamedDimensionRule(Set<Identifier> nameToCheck) {
        this.idsToCheck = nameToCheck;
    }

    @Override
    public boolean matches(Identifier identifier) {
        return identifier != null && idsToCheck.contains(identifier);
    }
}
