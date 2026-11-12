package com.el_redstoniano.grids_and_trims_wow.rules.blocks;

import com.el_redstoniano.grids_and_trims_wow.model.ChunkProcessingContext;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class AttachedEntityProtectionRule implements BlockProtectionRule{

    public AttachedEntityProtectionRule() {
    }

    @Override
    public boolean protects(BlockPos pos, BlockState blockState, ChunkProcessingContext context) {
        return context.isProtected(pos);
    }
}
