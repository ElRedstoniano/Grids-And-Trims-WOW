package com.el_redstoniano.grids_and_trims_wow.utils;

import com.el_redstoniano.grids_and_trims_wow.config.AxisGridConfig;
import com.el_redstoniano.grids_and_trims_wow.config.GridConfig;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

import java.util.function.Predicate;

public class ChunkUtils {

    public static void setLeavesPersistent(WorldChunk worldChunk, BlockPos pos, LeavesBlock leavesBlock){
        //if (worldChunk.getPos().equals(new ChunkPos(pos))) { // Checks if the pos is inside the chunk
        worldChunk.setBlockState(pos, leavesBlock.getDefaultState()
                .with(LeavesBlock.PERSISTENT, true), 0);
    }

    public static boolean isModCustomWorldType(World world) {
        return true; /*world.getRegistryKey().getValue().toString().equals(SkygridTestMod.MOD_ID + ":skygrid");*/
    }
}
