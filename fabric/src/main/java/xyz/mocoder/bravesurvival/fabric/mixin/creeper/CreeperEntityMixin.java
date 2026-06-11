package xyz.mocoder.bravesurvival.fabric.mixin.creeper;

import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.mocoder.bravesurvival.core.config.ConfigManager;

/**
 * 苦力怕实体Mixin
 * 用于强化苦力怕
 */
@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin {
    
    @Shadow
    private int fuseTime;
    
    @Shadow
    private int explosionRadius;
    
    /**
     * 注入苦力怕tick方法
     * 设置苦力怕隐形
     */
    @Inject(method = "tick", at = @At("HEAD"))
    private void makeInvisible(CallbackInfo ci) {
        if (ConfigManager.getMobConfig("creeper").has("invisible") && 
            ConfigManager.getMobConfig("creeper").get("invisible").getAsBoolean()) {
            CreeperEntity creeper = (CreeperEntity) (Object) this;
            creeper.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 10, 0, false, false));
        }
    }
    
    /**
     * 注入苦力怕爆炸方法
     * 设置瞬间爆炸
     */
    @Inject(method = "<init>", at = @At("RETURN"))
    private void setInstantFuse(CallbackInfo ci) {
        if (ConfigManager.getMobConfig("creeper").has("instant_fuse") && 
            ConfigManager.getMobConfig("creeper").get("instant_fuse").getAsBoolean()) {
            this.fuseTime = 1;
        }
    }
}
