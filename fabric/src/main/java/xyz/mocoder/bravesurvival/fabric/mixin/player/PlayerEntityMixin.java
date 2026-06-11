package xyz.mocoder.bravesurvival.fabric.mixin.player;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.mocoder.bravesurvival.core.config.ConfigManager;
import xyz.mocoder.bravesurvival.core.logic.player.PlayerLogic;
import xyz.mocoder.bravesurvival.fabric.entity.FabricEntityWrapper;

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
    private void applyFallDamageDebuff(float fallDistance, float damageMultiplier, CallbackInfo ci) {
        // 检查是否启用摔落伤害debuff
        if (ConfigManager.getPlayerConfig().has("fall_damage_debuff") && 
            ConfigManager.getPlayerConfig().get("fall_damage_debuff").getAsBoolean()) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            
            // 获取持续时间
            int weaknessDuration = ConfigManager.getPlayerConfig().has("weakness_duration") ? 
                                  ConfigManager.getPlayerConfig().get("weakness_duration").getAsInt() : 10;
            int slownessDuration = ConfigManager.getPlayerConfig().has("slowness_duration") ? 
                                  ConfigManager.getPlayerConfig().get("slowness_duration").getAsInt() : 10;
            
            // 添加虚弱效果
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, weaknessDuration, 0, false, false));
            
            // 添加缓慢效果
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, slownessDuration, 0, false, false));
        }
    }
    
    /**
     * 注入玩家tick方法
     * 处理饥饿效果
     */
    @Inject(method = "tick", at = @At("HEAD"))
    private void applyHungerEffects(CallbackInfo ci) {
        if (ConfigManager.getPlayerConfig().has("hunger_effects") && 
            ConfigManager.getPlayerConfig().get("hunger_effects").getAsBoolean()) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            
            // 检查饥饿值
            if (player.getHungerManager().getFoodLevel() < 6) {
                // 饥饿时给予虚弱和挖掘疲劳
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20, 0, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 20, 0, false, false));
            }
        }
    }
}
