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
import xyz.mocoder.bravesurvival.core.config.ConfigManager;

/**
 * 方块Mixin
 * 用于处理挖石头爆蠹虫和其他方块相关逻辑
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
        if (!world.isClient() && shouldSpawnSilverfish(state.getBlock())) {
            // 检查概率
            double chance = ConfigManager.getBlocksConfig().has("silverfish_chance") ? 
                           ConfigManager.getBlocksConfig().get("silverfish_chance").getAsDouble() : 0.0625;
            
            if (world.getRandom().nextDouble() < chance) {
                // 生成蠹虫
                SilverfishEntity silverfish = new SilverfishEntity(EntityType.SILVERFISH, (World) world);
                silverfish.updatePosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                world.spawnEntity(silverfish);
            }
        }
    }
    
    /**
     * 检查方块是否应该生成蠹虫
     */
    private boolean shouldSpawnSilverfish(Block block) {
        if (!ConfigManager.getBlocksConfig().has("silverfish_blocks")) {
            return false;
        }
        
        // 检查方块是否在列表中
        String blockId = block.toString();
        return block == Blocks.STONE || 
               block == Blocks.SANDSTONE || 
               block == Blocks.COBBLESTONE ||
               block == Blocks.ANDESITE || 
               block == Blocks.GRANITE || 
               block == Blocks.BASALT || 
               block == Blocks.BLACKSTONE;
    }
    
    /**
     * 注入方块破坏方法
     * 处理TNT破坏爆炸
     */
    @Inject(method = "onBroken", at = @At("HEAD"))
    private void handleTntBreak(WorldAccess world, BlockPos pos, BlockState state, CallbackInfo ci) {
        // 检查是否是TNT
        if (state.getBlock() == Blocks.TNT) {
            double chance = ConfigManager.getBlocksConfig().has("tnt_break_explodes_chance") ? 
                           ConfigManager.getBlocksConfig().get("tnt_break_explodes_chance").getAsDouble() : 0.1;
            
            if (world.getRandom().nextDouble() < chance) {
                // 生成爆炸
                World realWorld = (World) world;
                realWorld.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 4.0F, World.ExplosionSourceType.TNT);
            }
        }
    }
}
