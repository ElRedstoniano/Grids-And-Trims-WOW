package com.el_redstoniano.grids_and_trims_wow.rules.entity_types;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.BlockAttachedEntity;

public class AbstractDecorationEntityTypeRule implements EntityTypeRule{
    public AbstractDecorationEntityTypeRule(){}

    @Override
    public boolean matches(Entity entity) {
        return entity instanceof BlockAttachedEntity;
    }
}
