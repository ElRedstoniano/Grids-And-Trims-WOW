package com.el_redstoniano.grids_and_trims_wow.mixin;

import com.el_redstoniano.grids_and_trims_wow.utils.ChunkUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FluidState.class)
public abstract class FluidStateMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void onFirstTickLoadedScheduledTick(ServerLevel level, BlockPos pos, BlockState blockState, CallbackInfo ci) {
        if (ChunkUtils.isModCustomWorldType(level) && level.getChunkAt(pos).getInhabitedTime() <= 5) {
            ci.cancel();
        } // Avoids updating fluids for the first ticks after chunk is loaded
    }
}
