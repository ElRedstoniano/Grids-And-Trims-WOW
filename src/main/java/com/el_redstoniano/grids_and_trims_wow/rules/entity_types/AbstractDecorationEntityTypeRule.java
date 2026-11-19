package com.el_redstoniano.grids_and_trims_wow.rules.entity_types;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.AbstractDecorationEntity;

public class AbstractDecorationEntityTypeRule implements EntityTypeRule{
    public AbstractDecorationEntityTypeRule(){}

    @Override
    public boolean matches(Entity entity) {
        return entity instanceof AbstractDecorationEntity;
    }
}
