package xyz.mocoder.bravesurvival.fabric.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.mocoder.bravesurvival.core.config.ConfigManager;

/**
 * 通用怪物Mixin
 * 处理各种怪物的状态效果和行为
 */
public class MobBehaviorMixin {
    
    /**
     * 苦力怕行为Mixin
     */
    @Mixin(CreeperEntity.class)
    public static abstract class CreeperBehaviorMixin {
        
        /**
         * 注入苦力怕tick方法
         * 持续添加隐形效果
         */
        @Inject(method = "tick", at = @At("HEAD"))
        private void applyInvisibility(CallbackInfo ci) {
            if (ConfigManager.getMobConfig("creeper").has("invisible") && 
                ConfigManager.getMobConfig("creeper").get("invisible").getAsBoolean()) {
                CreeperEntity creeper = (CreeperEntity) (Object) this;
                creeper.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 10, 0, false, false));
            }
        }
    }
    
    /**
     * 蜘蛛行为Mixin
     */
    @Mixin(SpiderEntity.class)
    public static abstract class SpiderBehaviorMixin {
        
        /**
         * 注入蜘蛛tick方法
         * 持续添加隐形效果
         */
        @Inject(method = "tick", at = @At("HEAD"))
        private void applyInvisibility(CallbackInfo ci) {
            if (ConfigManager.getMobConfig("spider").has("invisible") && 
                ConfigManager.getMobConfig("spider").get("invisible").getAsBoolean()) {
                SpiderEntity spider = (SpiderEntity) (Object) this;
                spider.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 10, 0, false, false));
            }
        }
    }
    
    /**
     * 恶魂行为Mixin
     */
    @Mixin(GhastEntity.class)
    public static abstract class GhastBehaviorMixin {
        
        /**
         * 注入恶魂tick方法
         * 持续添加隐形效果
         */
        @Inject(method = "tick", at = @At("HEAD"))
        private void applyInvisibility(CallbackInfo ci) {
            if (ConfigManager.getMobConfig("ghast").has("invisible") && 
                ConfigManager.getMobConfig("ghast").get("invisible").getAsBoolean()) {
                GhastEntity ghast = (GhastEntity) (Object) this;
                ghast.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 10, 0, false, false));
            }
        }
    }
    
    /**
     * 烈焰人行为Mixin
     */
    @Mixin(BlazeEntity.class)
    public static abstract class BlazeBehaviorMixin {
        
        /**
         * 注入烈焰人tick方法
         * 添加火焰抗性
         */
        @Inject(method = "tick", at = @At("HEAD"))
        private void applyFireResistance(CallbackInfo ci) {
            if (ConfigManager.isMobEnabled("blaze")) {
                BlazeEntity blaze = (BlazeEntity) (Object) this;
                blaze.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 10, 0, false, false));
            }
        }
    }
    
    /**
     * 铁傀儡行为Mixin
     */
    @Mixin(IronGolemEntity.class)
    public static abstract class IronGolemBehaviorMixin {
        
        /**
         * 注入铁傀儡tick方法
         * 攻击最近的玩家
         */
        @Inject(method = "tick", at = @At("HEAD"))
        private void attackNearestPlayer(CallbackInfo ci) {
            if (ConfigManager.getMobConfig("iron_golem").has("attack_players") && 
                ConfigManager.getMobConfig("iron_golem").get("attack_players").getAsBoolean()) {
                IronGolemEntity golem = (IronGolemEntity) (Object) this;
                // 查找最近的玩家
                PlayerEntity nearestPlayer = golem.getWorld().getClosestPlayer(golem, 16);
                if (nearestPlayer != null && golem.getTarget() == null) {
                    golem.setTarget(nearestPlayer);
                }
            }
        }
    }
}
