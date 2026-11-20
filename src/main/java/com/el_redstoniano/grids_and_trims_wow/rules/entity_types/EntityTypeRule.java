package com.el_redstoniano.grids_and_trims_wow.rules.entity_types;


import net.minecraft.world.entity.Entity;

import java.util.Arrays;

public interface EntityTypeRule {
    boolean matches(Entity entity);

    default EntityTypeRule and(EntityTypeRule otherEntity) {
        return entity -> this.matches(entity) && otherEntity.matches(entity);
    }
    default EntityTypeRule or(EntityTypeRule otherEntity) {
        return entity -> this.matches(entity) || otherEntity.matches(entity);
    }
    default EntityTypeRule not() {
        return entity -> !this.matches(entity);
    }

    //static <T extends Entity> EntityTypeRule<T> none(){
    static EntityTypeRule none(){
        return entity -> false;
    }

    static EntityTypeRule anyOf(EntityTypeRule... rules){
        return Arrays.stream(rules).reduce(none(), EntityTypeRule::or);
    }
}
