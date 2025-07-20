package com.el_redstoniano.grids_and_trims_wow.managers;

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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ChunkGeneratorManager {
    private boolean REMOVE_BLOCKS_BEHIND_DECO_ENTITIES = false;

    private static List<BlockPos> blockAttachedEntityHolderPosList;
    public ChunkGeneratorManager() {
        blockAttachedEntityHolderPosList = new ArrayList<>();
    }

    public void searchForBlockAttachedEntities(Stream<Entity> loadedEntities) {
        // Filter by which attached entities can be processed
        Predicate<Entity> filterPredicate = entity -> entity instanceof AbstractDecorationEntity;
        // Adds all the blocks that are behind the blockAttached Entity bounding box to the HolderPosList
        loadedEntities.filter(filterPredicate).forEach(entity -> {
            BlockPos.stream(AbstractDecorationEntityHelper
                    .getAttachmentBox((AbstractDecorationEntity) entity)).forEach(blockPos -> {
                //SkygridTestMod.LOGGER.info("-------- behind blockpos:" + blockPos + "----" + entity);
                blockAttachedEntityHolderPosList.add(blockPos);
            });
        });
    }

    public void clearBlockAttachedEntityHolderPosList() {
        blockAttachedEntityHolderPosList.clear();
    }

    public void startRemovingBlocks(WorldChunk worldChunk, MinecraftServer server, List<Block> blocksBlackList) {
        //Starting iterating over local x y z coordinates
        for (int x = 0; x <= 15; x++) {
            for (int y = worldChunk.getBottomY(); y <= worldChunk.getTopYInclusive(); y++) {
                for (int z = 0; z <= 15; z++) {
                    BlockPos globalBlockPos = worldChunk.getPos().getBlockPos(x, y, z);
                    BlockPos localBlockPos = new BlockPos(x, y, z);
                    if (ChunkUtils.shouldRemoveBlock(globalBlockPos)) { // If block will get removed
                        BlockState blockState = worldChunk.getBlockState(localBlockPos);
                        if (blocksBlackList.contains(blockState.getBlock()) || blockAttachedEntityHolderPosList.contains(globalBlockPos)) {
                            continue; // Don't remove blacklisted blocks
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
            return !blocksBlackList.contains(block) && !blockAttachedEntityHolderPosList.contains(blockPos) &&
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
