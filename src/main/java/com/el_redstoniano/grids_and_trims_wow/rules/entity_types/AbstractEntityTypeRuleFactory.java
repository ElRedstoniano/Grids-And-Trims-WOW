package com.el_redstoniano.grids_and_trims_wow.rules.entity_types;

import net.minecraft.entity.EntityType;

import java.util.Set;

public class AbstractEntityTypeRuleFactory {
    public static EntityTypeRule buildRules(){
        return new AbstractEntityTypeRule();
    }
}
