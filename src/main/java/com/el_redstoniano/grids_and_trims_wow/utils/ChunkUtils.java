package com.el_redstoniano.grids_and_trims_wow.utils;

import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

import java.util.function.Predicate;

public class ChunkUtils {
    //private static Predicate<BlockPos> xGenerationPredicate = blockPos -> blockPos.getX() % 5 != 0;
    //private static Predicate<BlockPos> xGenerationPredicate = blockPos -> Math.floorMod(blockPos.getX(), 7) != 0 && Math.floorMod(blockPos.getX(), 7) != 1 && Math.floorMod(blockPos.getX(), 7) != 2;
    private static Predicate<BlockPos> xGenerationPredicate = blockPos -> calculateGridPredicate(blockPos.getX(), 0, 4, 1);
    private static Predicate<BlockPos> yGenerationPredicate = blockPos -> calculateGridPredicate(blockPos.getY(), -2, 4, 1);
    private static Predicate<BlockPos> zGenerationPredicate = blockPos -> calculateGridPredicate(blockPos.getZ(), 0, 4, 1);

    private static boolean calculateGridPredicate(int posIndex, int posOffset, int blockSpaces, int blocksIslandsSize) {
        posIndex += posOffset;
        if (blocksIslandsSize <= 1)
            return Math.ceilMod(posIndex, blockSpaces+1) != 0;
        int modulus = Math.floorMod(posIndex, blockSpaces+blocksIslandsSize);
        return !(modulus >= 0 && modulus <= (blocksIslandsSize-1));
    }

    private static final Predicate<BlockPos> placedBlockPredicate =
            xGenerationPredicate.or(yGenerationPredicate).or(zGenerationPredicate);
    private static final Predicate<BlockPos> entitySafeFromFallingIntoVoidPredicate = xGenerationPredicate.or(zGenerationPredicate);

    public static boolean shouldBlockStay(BlockPos blockPos) { return !placedBlockPredicate.test(blockPos); }

    public static boolean shouldRemoveBlock(BlockPos blockPos) { return placedBlockPredicate.test(blockPos); }

    public static void setLeavesPersistent(WorldChunk worldChunk, BlockPos pos, LeavesBlock leavesBlock){
        //if (worldChunk.getPos().equals(new ChunkPos(pos))) { // Checks if the pos is inside the chunk
        worldChunk.setBlockState(pos, leavesBlock.getDefaultState()
                .with(LeavesBlock.PERSISTENT, true), 0);
        //}

    }


    public static boolean isModCustomWorldType(World world) {
        return true; /*world.getRegistryKey().getValue().toString().equals(SkygridTestMod.MOD_ID + ":skygrid");*/
    }
}
