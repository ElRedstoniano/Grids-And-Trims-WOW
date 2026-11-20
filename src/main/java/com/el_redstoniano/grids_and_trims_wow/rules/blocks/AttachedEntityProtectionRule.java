package com.el_redstoniano.grids_and_trims_wow.rules.blocks;

import com.el_redstoniano.grids_and_trims_wow.model.ChunkProcessingContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AttachedEntityProtectionRule implements BlockProtectionRule{

    public AttachedEntityProtectionRule() {
    }

    @Override
    public boolean protects(BlockPos pos, BlockState blockState, ChunkProcessingContext context) {
        return context.isProtected(pos);
    }
}
