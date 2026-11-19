package com.el_redstoniano.grids_and_trims_wow.utils;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.BlockAttachedEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

import java.util.stream.Stream;

public class ChunkUtils {

    public static void setLeavesPersistent(WorldChunk worldChunk, BlockPos pos, LeavesBlock leavesBlock){
        //if (worldChunk.getPos().equals(new ChunkPos(pos))) { // Checks if the pos is inside the chunk
        worldChunk.setBlockState(pos, leavesBlock.getDefaultState()
                .with(LeavesBlock.PERSISTENT, true), 0);
    }

    public static boolean isModCustomWorldType(World world) {
        return true; /*world.getRegistryKey().getValue().toString().equals(SkygridTestMod.MOD_ID + ":skygrid");*/
    }

    public static void removeBlock(BlockPos globalBlockPos, BlockPos localBlockPos, WorldChunk worldChunk) {
        worldChunk.removeBlockEntity(globalBlockPos); // Removes block entity at coordinates if exists, if not nothing happens
        worldChunk.setBlockState(localBlockPos, Blocks.AIR.getDefaultState(), 0);
    }

    public static Stream<BlockPos> findBlocksBehindAttachedBlockEntities(Entity entity) {
        if (entity instanceof AbstractDecorationEntity decorationEntity) { // Avoid casting like (AbstractDecorationEntity) entity,
            return BlockPos.stream(AbstractDecorationEntityHelper.getAttachmentBox(decorationEntity))
                    .map(BlockPos::toImmutable);
            // BlockPos.stream(box) doesn't create a new BlockPos for each position, meaning that adding a mutable position to the set
            // would lead to have the same repeated block position, which would be replaced with the last blockpos iterated (aka all the
            // blockpos iterated are the same object).
            // toImmutable() fixes this by creating a new copy of each BlockPos.
        } else {
            return Stream.empty();
        }
    }

    private static class AbstractDecorationEntityHelper { // Took a look from AbstractDecorationEntity#canStayAttached()
        public static Box getAttachmentBox(BlockAttachedEntity blockAttachedEntity) {
            return blockAttachedEntity.getBoundingBox().offset(blockAttachedEntity.getHorizontalFacing().getUnitVector().mul(-0.5F)).contract(1.0E-7);
        }
    }
}
