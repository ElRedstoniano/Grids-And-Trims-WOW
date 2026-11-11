package com.el_redstoniano.grids_and_trims_wow.config;

public record AxisGridConfig(int offset, int gap, int islandSize) {
    public AxisGridConfig {
        gap = Math.max(gap, 0);
        islandSize = Math.max(islandSize, 1);
    }

    public boolean keeps(int coord){
        coord += offset;
        int modulus = Math.floorMod(coord, gap+islandSize);
        return modulus >= 0 && modulus <= (islandSize-1);
    }
}
