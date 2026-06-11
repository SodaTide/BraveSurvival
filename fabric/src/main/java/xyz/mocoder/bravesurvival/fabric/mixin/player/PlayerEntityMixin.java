package xyz.mocoder.bravesurvival.fabric.mixin.player;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.mocoder.bravesurvival.fabric.entity.FabricEntityWrapper;
import xyz.mocoder.bravesurvival.core.logic.player.PlayerLogic;

/**
 * 玩家实体Mixin
 * 用于处理玩家摔落伤害后的debuff
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    
    /**
     * 注入玩家摔落伤害处理方法
     * 在摔落伤害后添加debuff
     */
    @Inject(method = "handleFallDamage", at = @At("RETURN"))
    private void applyFallDamageDebuff(float fallDistance, float damageMultiplier, CallbackInfoReturnable cir) {
        // 检查是否启用摔落伤害debuff
        if (PlayerLogic.isFallDamageDebuffEnabled()) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            FabricEntityWrapper wrapper = new FabricEntityWrapper(player);
            
            // 应用debuff
            PlayerLogic.handleFallDamageDebuff(wrapper);
        }
    }
}
