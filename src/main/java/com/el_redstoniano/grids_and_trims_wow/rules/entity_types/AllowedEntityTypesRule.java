package com.el_redstoniano.grids_and_trims_wow.rules.entity_types;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.Set;

public class AllowedEntityTypesRule implements EntityTypeRule{
    private final Set<? extends EntityType<?>> entityTypesSet;
    public AllowedEntityTypesRule(Set<? extends EntityType<?>> entityTypesSet){
        this.entityTypesSet = Set.copyOf(entityTypesSet);
    }
    @Override
    public boolean matches(Entity entity) {
        return entityTypesSet.contains(entity.getType());
    }
}
