package com.atmx.moarmusicdiscs.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxBlock.class)
public class JukeboxMixin {

    @Inject(method = "setRecord", at = @At("RETURN"))
    private void onSetRecord(Entity entity, LevelAccessor level, BlockPos pos, BlockState state, ItemStack stack, CallbackInfo ci) {
        if (!level.isClientSide() && entity != null) {
            System.out.println(entity.getName().getString() + " inserted " + stack.getItem().getName(stack).getString() + " at " + pos);
        }
    }

    @Inject(method = "onRemove", at = @At("HEAD"))
    private void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving, CallbackInfo ci) {
        if (!level.isClientSide() && state.getValue(JukeboxBlock.HAS_RECORD)) {
            System.out.println("Record removed from jukebox at " + pos);
        }
    }
}
