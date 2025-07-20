package com.el_redstoniano.grids_and_trims_wow.mixin;

import com.el_redstoniano.grids_and_trims_wow.GridsAndTrims;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class ModifyWorldSpawnMixin {

    @Inject(method = "setupSpawn", at = @At("TAIL"))
    private static void modifySpawn(ServerWorld world, ServerWorldProperties worldProperties, boolean bonusChest, boolean debugWorld, CallbackInfo ci){
        //BlockPos blockPos = worldProperties.getSpawnPos();
        //worldProperties.setSpawnPos(new BlockPos(blockPos.getX(), 100, blockPos.getZ()), worldProperties.getSpawnAngle());
        // Will try to adjust at the closest surface Y level
        world.getGameRules().get(GameRules.SPAWN_RADIUS).set(0, world.getServer()); // Disable radius spawn spreading
        //world.setSpawnPos(worldProperties.getSpawnPos(), worldProperties.getSpawnAngle());
        //GridsAndTrims.LOGGER.info(worldProperties.getSpawnPos() + "-------------spawn");
       // }
    }
}
