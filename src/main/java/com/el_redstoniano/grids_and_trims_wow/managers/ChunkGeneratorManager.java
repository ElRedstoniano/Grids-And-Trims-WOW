package com.el_redstoniano.grids_and_trims_wow.managers;

import com.el_redstoniano.grids_and_trims_wow.config.GridConfig;
import com.el_redstoniano.grids_and_trims_wow.model.ChunkProcessingContext;
import com.el_redstoniano.grids_and_trims_wow.rules.blocks.BlockProtectionRule;
import com.el_redstoniano.grids_and_trims_wow.rules.dimensions.DimensionRule;
import com.el_redstoniano.grids_and_trims_wow.rules.entity_types.EntityTypeRule;
import com.el_redstoniano.grids_and_trims_wow.utils.ChunkUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChunkGeneratorManager {
    private final GridConfig gridConfig;
    private final BlockProtectionRule rules;
    private final DimensionRule dimensionRules;
    private final EntityTypeRule entityTypeRules;

    public ChunkGeneratorManager(GridConfig gridConfig, BlockProtectionRule blockRules, DimensionRule dimensionRules, EntityTypeRule entityTypeRules) {
        this.gridConfig = gridConfig;
        this.rules = blockRules;
        this.dimensionRules = dimensionRules;
        this.entityTypeRules = entityTypeRules;
    }

    public Set<BlockPos> searchForBlockAttachedEntities(Stream<Entity> loadedEntities, ChunkProcessingContext ctx) {
        if (!dimensionRules.matches(ctx.getDimensionId()))
            return Set.of();

        // Filter by which attached entities can be processed
        //Predicate<Entity> filterPredicate = entity -> entity instanceof AbstractDecorationEntity;

        return loadedEntities.filter(entityTypeRules::matches)
                .flatMap(ChunkUtils::findBlocksBehindAttachedBlockEntities)
                .collect(Collectors.toSet());
    }

    public void startRemovingBlocks(LevelChunk worldChunk, MinecraftServer server, ChunkProcessingContext chunkContext) {
        ChunkProcessingContext validatedChunkContext = chunkContext != null ? chunkContext : new ChunkProcessingContext();
        // Avoid null checks if the chunk for some reason didn't go through the CustomChunkGeneratingMixin

        //Starting iterating over local x y z coordinates
        for (int x = 0; x <= 15; x++) {
            for (int y = worldChunk.getMinY(); y <= worldChunk.getMaxY(); y++) {
                for (int z = 0; z <= 15; z++) {
                    BlockPos globalBlockPos = worldChunk.getPos().getBlockAt(x, y, z);
                    processPosition(globalBlockPos, x, y, z, worldChunk, server, validatedChunkContext);
                }
            }
        }
    }

    private void processPosition(BlockPos globalBlockPos, int x, int y, int z, LevelChunk levelChunk,
                                 MinecraftServer server, ChunkProcessingContext chunkContext) {
        BlockPos localBlockPos = new BlockPos(x, y, z);
        if (!gridConfig.keeps(globalBlockPos)) { // If block will get removed
            BlockState blockState = levelChunk.getBlockState(localBlockPos);
            if (!rules.protects(globalBlockPos, blockState, chunkContext)) {
                // Don't remove blocks that meets rules built from the ProtectionRuleFactory
                ChunkUtils.removeBlock(globalBlockPos, localBlockPos, levelChunk);
            }
        } else { // Checking non-replaced block areas
            BlockState blockState = levelChunk.getBlockState(localBlockPos);
            scheduleLeavesPersistent(blockState, localBlockPos, server, levelChunk);
        }
    }

    private void scheduleLeavesPersistent(BlockState blockState, BlockPos localBlockPos, MinecraftServer server, LevelChunk levelChunk) {
        if (blockState.getBlock() instanceof LeavesBlock leavesBlock) { // Check if block is leaves
            server.schedule(new TickTask(server.getTickCount(), () -> {
                ChunkUtils.setLeavesPersistent(levelChunk, localBlockPos, leavesBlock);
                //worldChunk.setBlockState(blockPos, blockState.withIfExists(Properties.PERSISTENT, true));
            }));  // Changing leaves persistent state to true 1 tick after the chunk is loaded.
            // If this is done earlier, the game/world loading process will suffer a deadlock
        }
    }
}
