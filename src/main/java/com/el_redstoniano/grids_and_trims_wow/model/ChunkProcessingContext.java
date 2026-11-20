package com.el_redstoniano.grids_and_trims_wow.model;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

// Parameter Object
public class ChunkProcessingContext {

    private final Set<BlockPos> protectedBlocksPos = new HashSet<>();
    private Identifier dimensionId;

    public void assignDimensionId(Identifier dimensionId) {
        this.dimensionId = dimensionId;
    }

    public Identifier getDimensionId() {
        return this.dimensionId;
    }

    public ChunkProcessingContext() {}

    public void addProtectedBlocks(Collection<BlockPos> protectedBlocks) {
        protectedBlocksPos.addAll(protectedBlocks);
    }

    public boolean isProtected(BlockPos blockPos) {
        return protectedBlocksPos.contains(blockPos);
    }
}
