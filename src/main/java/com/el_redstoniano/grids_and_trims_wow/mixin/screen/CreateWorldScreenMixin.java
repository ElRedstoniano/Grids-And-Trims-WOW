package com.el_redstoniano.grids_and_trims_wow.mixin.screen;

import com.el_redstoniano.grids_and_trims_wow.screen.GridsAndTrimsTab;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.components.tabs.MenuTabBar;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {

    @WrapOperation(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/tabs/MenuTabBar$Builder;addTabs([Lnet/minecraft/client/gui/components/tabs/Tab;)Lnet/minecraft/client/gui/components/tabs/MenuTabBar$Builder;"))
    private MenuTabBar.Builder addModTab(MenuTabBar.Builder instance, Tab[] tabs, Operation<MenuTabBar.Builder> original) {

        return original.call(instance, tabs)
                .addTab(new GridsAndTrimsTab((CreateWorldScreen) (Object) this));
    }

}
