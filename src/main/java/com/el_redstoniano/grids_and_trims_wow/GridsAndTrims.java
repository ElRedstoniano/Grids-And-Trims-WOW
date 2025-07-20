package com.el_redstoniano.grids_and_trims_wow;

import com.el_redstoniano.grids_and_trims_wow.managers.ChunkGeneratorManager;
import com.el_redstoniano.grids_and_trims_wow.utils.ChunkUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GridsAndTrims implements ModInitializer {

    public static final String MOD_ID = "grids_and_trims_wow";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static ThreadLocal<ChunkGeneratorManager> threadLocalChunkManager = new ThreadLocal<>();
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello Fabric world!");

        ChunkGeneratorManager chunkGeneratorManager = new ChunkGeneratorManager();
        List<Block> blocksBlackList = List.of(Blocks.END_PORTAL_FRAME, Blocks.CHEST);


        ServerChunkEvents.CHUNK_GENERATE.register((serverWorld, worldChunk) -> {
            //ChunkSection[] chunkSections = worldChunk.getSectionArray();
            //int posXChunk = worldChunk.getPos().x + 1; // if ((x * posXChunk) % 5 != 0)
            //int posZChunk = worldChunk.getPos().z + 1;
            if (!ChunkUtils.isModCustomWorldType(serverWorld))
                return;
            MinecraftServer server = serverWorld.getServer();

            chunkGeneratorManager.startRemovingBlocks(worldChunk, server, blocksBlackList);
            chunkGeneratorManager.clearBlockAttachedEntityHolderPosList();

            serverWorld.getChunkManager().getLightingProvider().light(worldChunk, false); // Update light for new spaces
            server.send(server.createTask(() -> {
                //serverWorld.getChunkManager().getLightingProvider().propagateLight(worldChunk.getPos());
                serverWorld.getChunkManager().getLightingProvider().light(worldChunk, false); // Update light for new spaces
                worldChunk.setLightOn(true);
            }));
            threadLocalChunkManager.remove();
        });
    }

    public static Identifier id (String name){
        return Identifier.of(MOD_ID, name);
    }
}
