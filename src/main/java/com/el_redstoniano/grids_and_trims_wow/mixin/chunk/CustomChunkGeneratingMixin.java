package com.el_redstoniano.grids_and_trims_wow.mixin.chunk;

import com.el_redstoniano.grids_and_trims_wow.managers.ChunkGeneratorManager;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.storage.ReadView;
import net.minecraft.world.World;
import net.minecraft.world.chunk.AbstractChunkHolder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkGenerating;
import net.minecraft.world.chunk.ChunkGenerationContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.el_redstoniano.grids_and_trims_wow.GridsAndTrims.threadLocalChunkManager;

@Mixin(ChunkGenerating.class)
public abstract class CustomChunkGeneratingMixin {

    @Inject(method = "method_60553", at = @At("HEAD"))
    private static void onChunkLoadAtHead(Chunk chunk, ChunkGenerationContext chunkGenerationContext, AbstractChunkHolder chunkHolder,
                                     CallbackInfoReturnable<Chunk> callbackInfoReturnable/*, @Share("chunkGeneratorManagerID") LocalRef<ChunkGeneratorManager> chunkGeneratorManagerLocalRef*/
    ) {
        try {
            threadLocalChunkManager.set(new ChunkGeneratorManager());
        } catch (Exception e) {
            //chunkThreadLocal.remove();
        }
    }

    @WrapOperation(method = "addEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;streamFromData(Lnet/minecraft/storage/ReadView$ListReadView;Lnet/minecraft/world/World;Lnet/minecraft/entity/SpawnReason;)Ljava/util/stream/Stream;"))
    private static Stream<Entity> onAddedEntities(ReadView.ListReadView entities, World world, SpawnReason reason, Operation<Stream<Entity>> original) {
        ChunkGeneratorManager chunkGeneratorManager = threadLocalChunkManager.get();
        Supplier<Stream<Entity>> supplier = () -> original.call(entities, world, reason);
        // Once the stream has been processed, it gets terminated and no more operations can be done, so it must be provided as a supplier as a workaround instead of doing original.call() two times
        //Stream<Entity> entitiesStream = original.call(entities, world, reason); // EntityType.streamFromData(entities, world, SpawnReason.LOAD)
        if (chunkGeneratorManager != null) {
            chunkGeneratorManager.searchForBlockAttachedEntities(supplier.get());
            //entitiesStream.forEach(entity -> {
            //    SkygridTestMod.LOGGER.info(entity.toString() + "   " + (entity instanceof AbstractDecorationEntity ss ? ss.toString() : ""));
            //});
        }/* else { SkygridTestMod.LOGGER.info("EMPTY----------------------"); }*/
        return supplier.get();
    }
}
