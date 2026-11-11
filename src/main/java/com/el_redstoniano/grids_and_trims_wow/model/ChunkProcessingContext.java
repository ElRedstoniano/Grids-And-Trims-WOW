package com.el_redstoniano.grids_and_trims_wow.model;

import net.minecraft.util.math.BlockPos;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

// Parameter Object
public class ChunkProcessingContext {

    private final Set<BlockPos> protectedBlocksPos = new HashSet<>();

    public ChunkProcessingContext() {}

    public void addProtectedBlocks(Collection<BlockPos> protectedBlocks) {
        protectedBlocksPos.addAll(protectedBlocks);
    }

    public boolean isProtected(BlockPos blockPos) {
        return protectedBlocksPos.contains(blockPos);
    }
}
