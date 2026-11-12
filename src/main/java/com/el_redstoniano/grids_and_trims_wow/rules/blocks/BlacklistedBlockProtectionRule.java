package com.el_redstoniano.grids_and_trims_wow.rules.blocks;

import com.el_redstoniano.grids_and_trims_wow.model.ChunkProcessingContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class BlacklistedBlockProtectionRule implements BlockProtectionRule{
    private final Set<Block> blocks;

    public BlacklistedBlockProtectionRule(Set<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public boolean protects(BlockPos pos, BlockState blockState, ChunkProcessingContext context) {
        return blocks.contains(blockState.getBlock());
    }
}
