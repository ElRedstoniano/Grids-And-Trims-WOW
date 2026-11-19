package com.el_redstoniano.grids_and_trims_wow.rules.entity_types;

public class AbstractEntityTypeRuleFactory {
    public static EntityTypeRule buildRules(){
        return new AbstractDecorationEntityTypeRule();
    }
}
