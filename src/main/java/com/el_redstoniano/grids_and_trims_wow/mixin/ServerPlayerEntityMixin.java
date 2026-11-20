package com.el_redstoniano.grids_and_trims_wow.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(method = "adjustSpawnLocation", at = @At("HEAD"), cancellable = true)
    public void modifyGetWorldSpawnPos (ServerLevel level, BlockPos spawnSuggestion, CallbackInfoReturnable<BlockPos> cir) {
        level.getGameRules().set(GameRules.RESPAWN_RADIUS, 0, level.getServer());
        cir.setReturnValue(level.getServer().getRespawnData().pos());
    }
}
