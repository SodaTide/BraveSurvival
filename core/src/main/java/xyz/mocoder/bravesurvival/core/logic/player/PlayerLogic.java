package xyz.mocoder.bravesurvival.core.logic.player;

import xyz.mocoder.bravesurvival.core.config.ConfigManager;
import xyz.mocoder.bravesurvival.core.entity.EntityWrapper;

/**
 * 玩家逻辑类
 * 处理玩家相关的游戏逻辑
 */
public class PlayerLogic {
    
    /**
     * 处理摔落伤害后的debuff
     */
    public static void handleFallDamageDebuff(EntityWrapper player) {
        if (!ConfigManager.getPlayerConfig().has("fall_damage_debuff") || 
            !ConfigManager.getPlayerConfig().get("fall_damage_debuff").getAsBoolean()) {
            return;
        }
        
        // 获取持续时间
        int weaknessDuration = ConfigManager.getPlayerConfig().has("weakness_duration") ? 
                              ConfigManager.getPlayerConfig().get("weakness_duration").getAsInt() : 10;
        int slownessDuration = ConfigManager.getPlayerConfig().has("slowness_duration") ? 
                              ConfigManager.getPlayerConfig().get("slowness_duration").getAsInt() : 10;
        
        // 添加虚弱效果
        player.addStatusEffect("WEAKNESS", weaknessDuration, 0);
        
        // 添加缓慢效果
        player.addStatusEffect("SLOWNESS", slownessDuration, 0);
    }
    
    /**
     * 检查是否启用摔落伤害debuff
     */
    public static boolean isFallDamageDebuffEnabled() {
        return ConfigManager.getPlayerConfig().has("fall_damage_debuff") && 
               ConfigManager.getPlayerConfig().get("fall_damage_debuff").getAsBoolean();
    }
    
    /**
     * 获取虚弱效果持续时间
     */
    public static int getWeaknessDuration() {
        return ConfigManager.getPlayerConfig().has("weakness_duration") ? 
               ConfigManager.getPlayerConfig().get("weakness_duration").getAsInt() : 10;
    }
    
    /**
     * 获取缓慢效果持续时间
     */
    public static int getSlownessDuration() {
        return ConfigManager.getPlayerConfig().has("slowness_duration") ? 
               ConfigManager.getPlayerConfig().get("slowness_duration").getAsInt() : 10;
    }
}
