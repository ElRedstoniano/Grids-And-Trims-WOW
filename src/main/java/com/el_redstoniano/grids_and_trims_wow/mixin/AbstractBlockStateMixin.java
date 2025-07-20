package com.el_redstoniano.grids_and_trims_wow.mixin;

import com.el_redstoniano.grids_and_trims_wow.utils.ChunkUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin {

    @Inject(method = "neighborUpdate", at = @At("HEAD"), cancellable = true)
    private void cancelNeighborUpdate(World world, BlockPos pos, Block sourceBlock, WireOrientation wireOrientation, boolean notify, CallbackInfo ci) {
        if (ChunkUtils.isModCustomWorldType(world) && world.getWorldChunk(pos).getInhabitedTime() <= 5) {
            ci.cancel();
        }
    }
}
