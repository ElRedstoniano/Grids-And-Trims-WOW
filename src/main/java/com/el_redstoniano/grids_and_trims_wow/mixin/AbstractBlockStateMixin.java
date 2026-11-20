package com.el_redstoniano.grids_and_trims_wow.mixin;

import com.el_redstoniano.grids_and_trims_wow.utils.ChunkUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public class AbstractBlockStateMixin {

    @Inject(method = "neighborChanged", at = @At("HEAD"), cancellable = true)
    private void cancelNeighborUpdate(BlockState state, Level level, BlockPos pos, Block block, Orientation orientation, boolean movedByPiston, CallbackInfo ci) {
        if (ChunkUtils.isModCustomWorldType(level) && level.getChunkAt(pos).getInhabitedTime() <= 5) {
            ci.cancel();
        }
    }
}
