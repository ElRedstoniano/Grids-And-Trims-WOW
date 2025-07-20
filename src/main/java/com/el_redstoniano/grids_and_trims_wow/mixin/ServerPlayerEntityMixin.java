package com.el_redstoniano.grids_and_trims_wow.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(method = "getWorldSpawnPos", at = @At("HEAD"), cancellable = true)
    public void modifyGetWorldSpawnPos (ServerWorld world, BlockPos basePos, CallbackInfoReturnable<BlockPos> cir) {
        world.getGameRules().get(GameRules.SPAWN_RADIUS).set(0, world.getServer());
        cir.setReturnValue(world.getSpawnPos());
    }
}
