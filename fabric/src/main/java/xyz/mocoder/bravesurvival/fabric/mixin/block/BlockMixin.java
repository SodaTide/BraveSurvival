package xyz.mocoder.bravesurvival.fabric.mixin.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.mocoder.bravesurvival.core.logic.world.WorldLogic;

/**
 * 方块Mixin
 * 用于处理挖石头爆蠹虫
 */
@Mixin(Block.class)
public abstract class BlockMixin {
    
    /**
     * 注入方块破坏方法
     * 在破坏特定方块时生成蠹虫
     */
    @Inject(method = "onBroken", at = @At("HEAD"))
    private void spawnSilverfishOnBreak(WorldAccess world, BlockPos pos, BlockState state, CallbackInfo ci) {
        // 检查是否应该生成蠹虫
        if (!world.isClient() && WorldLogic.shouldSpawnSilverfish(state.getBlock().toString())) {
            // 检查概率
            if (world.getRandom().nextDouble() < WorldLogic.getSilverfishChance()) {
                // 生成蠹虫
                SilverfishEntity silverfish = new SilverfishEntity(EntityType.SILVERFISH, (World) world);
                silverfish.updatePosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                world.spawnEntity(silverfish);
            }
        }
    }
}
