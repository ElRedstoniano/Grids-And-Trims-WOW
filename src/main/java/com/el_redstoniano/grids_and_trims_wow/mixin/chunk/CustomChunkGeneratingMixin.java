package com.el_redstoniano.grids_and_trims_wow.mixin.chunk;

import com.el_redstoniano.grids_and_trims_wow.GridsAndTrims;
import com.el_redstoniano.grids_and_trims_wow.model.ChunkProcessingContext;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.status.ChunkStatusTasks;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.ValueInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Mixin(ChunkStatusTasks.class)
public abstract class CustomChunkGeneratingMixin {

    @Inject(method = "lambda$full$0", at = @At("HEAD"))
    private static void onChunkLoadAtHead(ChunkAccess chunk, WorldGenContext context, GenerationChunkHolder holder, CallbackInfoReturnable<ChunkAccess> cir
            /*, @Share("chunkGeneratorManagerID") LocalRef<ChunkGeneratorManager> chunkGeneratorManagerLocalRef*/
    ) {
        if(!(chunk instanceof ImposterProtoChunk)) {
            ChunkProcessingContext chunkContext = new ChunkProcessingContext();
            GridsAndTrims.threadLocalChunkContext.set(chunkContext);
        }
    }

    @WrapOperation(method = "postLoadProtoChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;loadEntitiesRecursive(Lnet/minecraft/world/level/storage/ValueInput$ValueInputList;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/EntitySpawnReason;)Ljava/util/stream/Stream;"))
    private static Stream<Entity> onAddedEntities(ValueInput.ValueInputList entities, Level level, EntitySpawnReason reason, Operation<Stream<Entity>> original) {
        ChunkProcessingContext chunkContext = GridsAndTrims.threadLocalChunkContext.get();
        Identifier dimensionType = level.dimension().identifier();

        chunkContext.assignDimensionId(dimensionType);
        Supplier<Stream<Entity>> supplier = () -> original.call(entities, level, reason);
        // Once the stream has been processed, it gets terminated and no more operations can be done, so it must be provided as a supplier as a workaround instead of doing original.call() two times
        chunkContext.addProtectedBlocks(GridsAndTrims.getChunkManager()
                .searchForBlockAttachedEntities(supplier.get(), chunkContext));

        return supplier.get();
    }
}
