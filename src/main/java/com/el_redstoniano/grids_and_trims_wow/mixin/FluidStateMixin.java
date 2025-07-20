package com.el_redstoniano.grids_and_trims_wow.mixin;

import com.el_redstoniano.grids_and_trims_wow.utils.ChunkUtils;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FluidState.class)
public abstract class FluidStateMixin {

    @Inject(method = "onScheduledTick", at = @At("HEAD"), cancellable = true)
    public void onFirstTickLoadedScheduledTick(ServerWorld world, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (ChunkUtils.isModCustomWorldType(world) && world.getWorldChunk(pos).getInhabitedTime() <= 5) {
            ci.cancel();
        } // Avoids updating fluids for the first ticks after chunk is loaded
    }
}
