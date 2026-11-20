package com.el_redstoniano.grids_and_trims_wow.config;

import net.minecraft.core.BlockPos;

public record GridConfig(AxisGridConfig x, AxisGridConfig y, AxisGridConfig z) {
    public static final GridConfig DEFAULT = new GridConfig(
            new AxisGridConfig(0, 4, 1),
            new AxisGridConfig(-2, 4, 1),
            new AxisGridConfig(0, 4, 1)
    );

    public boolean keeps(BlockPos blockPos){
        return x.keeps(blockPos.getX()) && y.keeps(blockPos.getY()) && z.keeps(blockPos.getZ());
    }
}
