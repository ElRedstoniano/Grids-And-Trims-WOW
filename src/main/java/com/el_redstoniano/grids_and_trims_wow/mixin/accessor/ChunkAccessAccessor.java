package com.el_redstoniano.grids_and_trims_wow.mixin.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ChunkAccess.class)
public interface ChunkAccessAccessor {
    @Accessor("pendingBlockEntities")
    @Mutable // to remove the final flag during application
    Map<BlockPos, CompoundTag> gatWOW$getPendingBlockEntities();
}
