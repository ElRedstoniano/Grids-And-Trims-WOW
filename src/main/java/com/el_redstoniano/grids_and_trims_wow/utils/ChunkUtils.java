package com.el_redstoniano.grids_and_trims_wow.utils;

import com.el_redstoniano.grids_and_trims_wow.mixin.accessor.ChunkAccessAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.BlockAttachedEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;

import java.util.stream.Stream;

public class ChunkUtils {

    public static void setLeavesPersistent(LevelChunk levelChunk, BlockPos pos, LeavesBlock leavesBlock){
        //if (worldChunk.getPos().equals(new ChunkPos(pos))) { // Checks if the pos is inside the chunk
        levelChunk.setBlockState(pos, leavesBlock.defaultBlockState()
                .setValue(LeavesBlock.PERSISTENT, true), 0);
    }

    public static boolean isModCustomWorldType(Level level) {
        return true; /*level.getRegistryKey().getValue().toString().equals(SkygridTestMod.MOD_ID + ":skygrid");*/
    }

    public static void removeBlock(BlockPos globalBlockPos, BlockPos localBlockPos, LevelChunk levelChunk) {
        levelChunk.removeBlockEntity(globalBlockPos); // Removes block entity at coordinates if exists, if not nothing happens
        levelChunk.setBlockState(localBlockPos, Blocks.AIR.defaultBlockState(), 0);
    }

    public static Stream<BlockPos> findBlocksBehindAttachedBlockEntities(Entity entity) {
        if (entity instanceof BlockAttachedEntity decorationEntity) { // Avoid casting like (AbstractDecorationEntity) entity,

            return BlockPos.betweenClosedStream(AbstractDecorationEntityHelper.calculateSupportBox(decorationEntity))
                    .map(BlockPos::immutable);
            // BlockPos.stream(box) doesn't create a new BlockPos for each position, meaning that adding a mutable position to the set
            // would lead to have the same repeated block position, which would be replaced with the last blockpos iterated (aka all the
            // blockpos iterated are the same object).
            // toImmutable() fixes this by creating a new copy of each BlockPos.
        } else {
            return Stream.empty();
        }
    }

    private static class AbstractDecorationEntityHelper { // Took a look from BlockAttachedEntity#calculateSupportBox()
        public static AABB calculateSupportBox(BlockAttachedEntity blockAttachedEntity) {
            return blockAttachedEntity.getBoundingBox().move(blockAttachedEntity.getDirection().step().mul(-0.5F)).deflate(1.0E-7);
        }
    }
}
