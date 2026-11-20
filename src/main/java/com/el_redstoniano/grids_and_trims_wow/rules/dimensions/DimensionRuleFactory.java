package com.el_redstoniano.grids_and_trims_wow.rules.dimensions;

import net.minecraft.resources.Identifier;

import java.util.Set;

public class DimensionRuleFactory {
    public static DimensionRule buildRules(Set<Identifier> dimensionIdSet) {
        NamedDimensionRule namedDimensionRule = new NamedDimensionRule(dimensionIdSet);
        return DimensionRule.anyOf(namedDimensionRule); // + future rules
    }
}
