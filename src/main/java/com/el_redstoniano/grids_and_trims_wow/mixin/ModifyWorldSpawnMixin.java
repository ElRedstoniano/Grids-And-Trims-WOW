package com.el_redstoniano.grids_and_trims_wow.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.LevelLoadListener;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class ModifyWorldSpawnMixin {

    @Inject(method = "setInitialSpawn", at = @At("TAIL"))
    private static void modifySpawn(ServerLevel level, ServerLevelData levelData, boolean spawnBonusChest, boolean isDebug, LevelLoadListener levelLoadListener, CallbackInfo ci){
        //BlockPos blockPos = worldProperties.getSpawnPos();
        //worldProperties.setSpawnPos(new BlockPos(blockPos.getX(), 100, blockPos.getZ()), worldProperties.getSpawnAngle());
        // Will try to adjust at the closest surface Y level
        level.getGameRules().set(GameRules.RESPAWN_RADIUS, 0, level.getServer()); // Disable radius spawn spreading
        //world.setSpawnPos(worldProperties.getSpawnPos(), worldProperties.getSpawnAngle());
        //GridsAndTrims.LOGGER.info(worldProperties.getSpawnPos() + "-------------spawn");
       // }
    }
}
