package com.el_redstoniano.grids_and_trims_wow.rules.blocks;

import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ProtectionRuleFactory {
    public static BlockProtectionRule buildRules(Set<Block> blocks, boolean removeBlocksBehindDecoEntities){
        BlacklistedBlockProtectionRule blacklistedBlockProtectionRule = new BlacklistedBlockProtectionRule(blocks);
        AttachedEntityProtectionRule attachedEntityProtectionRule = new AttachedEntityProtectionRule();
        return BlockProtectionRule.anyOf(
                blacklistedBlockProtectionRule, // If the Set is empty, nothing should happen anyways
                removeBlocksBehindDecoEntities ? BlockProtectionRule.none() : attachedEntityProtectionRule
        );
    }
}
