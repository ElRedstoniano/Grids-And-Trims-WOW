package com.el_redstoniano.grids_and_trims_wow.rules.entity_types;

import net.minecraft.entity.EntityType;

import java.util.Set;

public class EntityTypeRuleFactory {
    public static EntityTypeRule buildRules(Set<? extends EntityType<?>> entityTypes){
        AllowedEntityTypesRule concretEntityTypeRule = new AllowedEntityTypesRule(entityTypes);
        return EntityTypeRule.anyOf(
                concretEntityTypeRule //entity -> entityTypes.contains(entity.getType())
        );
    }
}
