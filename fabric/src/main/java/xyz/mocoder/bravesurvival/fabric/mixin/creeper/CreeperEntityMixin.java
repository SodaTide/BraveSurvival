package xyz.mocoder.bravesurvival.fabric.mixin.creeper;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.mocoder.bravesurvival.core.logic.mob.MobEnhancer;

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
     * 注入苦力怕初始化方法
     * 设置苦力怕为高压状态
     */
    @Inject(method = "initDataTracker", at = @At("RETURN"))
    private void makeCharged(CallbackInfo ci) {
        if (MobEnhancer.getMobConfig("creeper").has("always_charged") && 
            MobEnhancer.getMobConfig("creeper").get("always_charged").getAsBoolean()) {
            // 设置为高压状态
            // 注意：这需要访问dataTracker，可能需要更复杂的Mixin
            // 这里只是一个示例
        }
    }
    
    /**
     * 注入苦力怕tick方法
     * 设置苦力怕隐形
     */
    @Inject(method = "tick", at = @At("HEAD"))
    private void makeInvisible(CallbackInfo ci) {
        if (MobEnhancer.getMobConfig("creeper").has("invisible") && 
            MobEnhancer.getMobConfig("creeper").get("invisible").getAsBoolean()) {
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
        if (MobEnhancer.getMobConfig("creeper").has("instant_fuse") && 
            MobEnhancer.getMobConfig("creeper").get("instant_fuse").getAsBoolean()) {
            this.fuseTime = 1;
        }
    }
}
