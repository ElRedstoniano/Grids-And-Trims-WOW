package com.el_redstoniano.grids_and_trims_wow.rules.blocks;

import com.el_redstoniano.grids_and_trims_wow.model.ChunkProcessingContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;

@FunctionalInterface
public interface BlockProtectionRule {
    boolean protects(BlockPos pos, BlockState blockState, ChunkProcessingContext context);

    default BlockProtectionRule or(BlockProtectionRule other) {
        return (pos, blockState, context) -> this.protects(pos,blockState,context)
                || other.protects(pos, blockState, context);
    }

    static BlockProtectionRule none() {
        return (pos, blockState, context) -> false;
    }

    static BlockProtectionRule anyOf(BlockProtectionRule... rules) {
        /*BlockProtectionRule none = none();
        for (BlockProtectionRule rule : rules) {
            none = none.or(rule);
        }
        return none;
        */
        return Arrays.stream(rules).reduce(none(), BlockProtectionRule::or);
    }
}
