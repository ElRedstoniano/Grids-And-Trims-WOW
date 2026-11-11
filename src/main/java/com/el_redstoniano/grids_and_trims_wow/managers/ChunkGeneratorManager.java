package com.el_redstoniano.grids_and_trims_wow.managers;

import com.el_redstoniano.grids_and_trims_wow.model.ChunkProcessingContext;
import com.el_redstoniano.grids_and_trims_wow.utils.ChunkUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.BlockAttachedEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.chunk.WorldChunk;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ChunkGeneratorManager {
    private final boolean REMOVE_BLOCKS_BEHIND_DECO_ENTITIES = false;

    public Set<BlockPos> searchForBlockAttachedEntities(Stream<Entity> loadedEntities) {
        Set<BlockPos> blockPosAttachedEntitySet = new HashSet<>();
        // Filter by which attached entities can be processed
        Predicate<Entity> filterPredicate = entity -> entity instanceof AbstractDecorationEntity;
        // Adds all the blocks that are behind the blockAttached Entity bounding box to the HolderPosList
        loadedEntities.filter(filterPredicate).forEach(entity -> {
            BlockPos.stream(AbstractDecorationEntityHelper
                    .getAttachmentBox((AbstractDecorationEntity) entity))
                    .forEach(blockPos -> blockPosAttachedEntitySet.add(blockPos.toImmutable()));
            // BlockPos.stream(box) doesn't create a new BlockPos for each position, meaning that adding a mutable position to the set
            // would lead to have the same repeated block position, which would be replaced with the last blockpos iterated (aka all the
            // blockpos iterated are the same object).
            // toImmutable() fixes this by creating a new copy of each BlockPos.
        });
        return blockPosAttachedEntitySet;
    }

    public void startRemovingBlocks(WorldChunk worldChunk, MinecraftServer server, Set<Block> blocksBlackList, ChunkProcessingContext chunkContext) {
        ChunkProcessingContext validatedChunkContext = chunkContext != null ? chunkContext : new ChunkProcessingContext();
        // Avoid null checks if the chunk for some reason didn't go through the CustomChunkGeneratingMixin
        //Starting iterating over local x y z coordinates
        for (int x = 0; x <= 15; x++) {
            for (int y = worldChunk.getBottomY(); y <= worldChunk.getTopYInclusive(); y++) {
                for (int z = 0; z <= 15; z++) {
                    BlockPos globalBlockPos = worldChunk.getPos().getBlockPos(x, y, z);
                    BlockPos localBlockPos = new BlockPos(x, y, z);
                    if (ChunkUtils.shouldRemoveBlock(globalBlockPos)) { // If block will get removed
                        BlockState blockState = worldChunk.getBlockState(localBlockPos);
                        if (blocksBlackList.contains(blockState.getBlock()) || validatedChunkContext.isProtected(globalBlockPos)) {
                            continue; // Don't remove blacklisted/protected blocks
                        }
                        worldChunk.removeBlockEntity(globalBlockPos); // Removes block entity at coordinates if exists, if not nothing happens
                        worldChunk.setBlockState(localBlockPos, Blocks.AIR.getDefaultState(), 0);
                    } else { // Checking non-replaced block areas
                        BlockState blockState = worldChunk.getBlockState(localBlockPos);
                        if (blockState.getBlock() instanceof LeavesBlock leavesBlock) { // Check if block is leaves
                            server.send(server.createTask(() -> {
                                ChunkUtils.setLeavesPersistent(worldChunk, localBlockPos, leavesBlock);
                                //worldChunk.setBlockState(blockPos, blockState.withIfExists(Properties.PERSISTENT, true));
                            }));  // Changing leaves persistent state to true 1 tick after the chunk is loaded.
                            // If this is done earlier, the game/world loading process will suffer a deadlock
                        }
                    }
                }
            }
        }
    }

    private static class AbstractDecorationEntityHelper { // Took a look from AbstractDecorationEntity#canStayAttached()
        public static Box getAttachmentBox(BlockAttachedEntity blockAttachedEntity) {
            return blockAttachedEntity.getBoundingBox().offset(blockAttachedEntity.getHorizontalFacing().getUnitVector().mul(-0.5F)).contract(1.0E-7);
        }
    }

    // Outdated: Can cause ArrayIndexOutOfBoundsException as this seems to be not thread safe, instead worldChunk.removeBlockEntity(pos) is used in the main method
    /*public void removeUnwantedBlockEntities(WorldChunk worldChunk, List<Block> blocksBlackList) {
        //Predicate<Block> blockBlackListPredicate = blockBlackList::contains;
        Predicate<Map. Entry<BlockPos, ?>> filterPredicate = blockPosMap -> {
            BlockPos blockPos = blockPosMap.getKey();
            Block block = worldChunk.getBlockState(blockPos).getBlock();
            return !blocksBlackList.contains(block) && !blockPosAttachedEntityHolderSet.contains(blockPos) &&
                    !ChunkGeneratorManager.shouldBlockStay(blockPos);
        };
        worldChunk.getBlockEntities().entrySet().stream().filter(filterPredicate)
                .forEach(blockPosBlockEntityEntry -> worldChunk.removeBlockEntity(blockPosBlockEntityEntry.getKey()));
        worldChunk.updateAllBlockEntities();
    }*/

    /*public static boolean isEntitySafeFromFallingIntoVoid(Entity entity) {
        return entitySafeFromFallingIntoVoidPredicate.test(entity.getBlockPos());
    }*/

    /*public void removeUnwantedEntities(WorldChunk worldChunk) {
        Predicate<Entity> filterPredicate = entity -> isEntitySafeFromFallingIntoVoid(entity) && entity.isAlive();

        BlockPos startBlockPos = worldChunk.getPos().getStartPos();
        //BlockPos endBlockPos = new BlockPos(worldChunk.getPos().getEndX(), worldChunk.getTopYInclusive(), worldChunk.getPos().getEndX());
        worldChunk.getWorld().getEntitiesByClass(Entity.class,
                Box.from(Vec3d.of(startBlockPos)).expand(15, worldChunk.getTopYInclusive(), 15),
                 filterPredicate)
                .forEach(entity -> {
                    if (entity instanceof ItemFrameEntity itemFrameEntity) {
                        this.blockAttachedEntityHolderPos.add(itemFrameEntity.getBlockPos());
                        //itemFrameEntity.getAttachedBlockPos()
                        //return;
                    } else {
                         entity.discard();
                    }
                });
    }*/
}
