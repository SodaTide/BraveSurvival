package xyz.mocoder.bravesurvival.core.logic.mob;

import xyz.mocoder.bravesurvival.core.config.ConfigManager;
import xyz.mocoder.bravesurvival.core.entity.EntityWrapper;

/**
 * 怪物强化器
 * 负责应用怪物强化逻辑
 */
public class MobEnhancer {
    
    /**
     * 强化僵尸
     */
    public static void enhanceZombie(EntityWrapper entity) {
        if (!ConfigManager.isMobEnabled("zombie")) {
            return;
        }
        
        // 设置属性
        entity.setMaxHealth(ConfigManager.getMobAttribute("zombie", "health", 20.0));
        entity.setAttackDamage(ConfigManager.getMobAttribute("zombie", "damage", 8.0));
        entity.setMovementSpeed(ConfigManager.getMobAttribute("zombie", "speed", 0.35));
        entity.setFollowRange(ConfigManager.getMobAttribute("zombie", "follow_range", 55.0));
        entity.setArmor(ConfigManager.getMobAttribute("zombie", "armor", 3.0));
        
        // 设置不在日光下燃烧
        if (!ConfigManager.getMobConfig("zombie").has("burn_in_daylight") || 
            !ConfigManager.getMobConfig("zombie").get("burn_in_daylight").getAsBoolean()) {
            entity.setBurnsInDaylight(false);
        }
    }
    
    /**
     * 强化苦力怕
     */
    public static void enhanceCreeper(EntityWrapper entity) {
        if (!ConfigManager.isMobEnabled("creeper")) {
            return;
        }
        
        // 苦力怕特殊处理：设置为高压状态
        // 这需要在具体的平台适配器中实现
    }
    
    /**
     * 强化末影人
     */
    public static void enhanceEnderman(EntityWrapper entity) {
        if (!ConfigManager.isMobEnabled("enderman")) {
            return;
        }
        
        // 设置属性
        entity.setMaxHealth(ConfigManager.getMobAttribute("enderman", "health", 40.0));
        entity.setAttackDamage(ConfigManager.getMobAttribute("enderman", "damage", 14.0));
        entity.setMovementSpeed(ConfigManager.getMobAttribute("enderman", "speed", 0.75));
        entity.setFollowRange(ConfigManager.getMobAttribute("enderman", "follow_range", 64.0));
    }
    
    /**
     * 强化骷髅
     */
    public static void enhanceSkeleton(EntityWrapper entity) {
        if (!ConfigManager.isMobEnabled("skeleton")) {
            return;
        }
        
        // 设置属性
        entity.setAttackDamage(ConfigManager.getMobAttribute("skeleton", "damage", 10.0));
        
        // 设置不在日光下燃烧
        if (!ConfigManager.getMobConfig("skeleton").has("burn_in_daylight") || 
            !ConfigManager.getMobConfig("skeleton").get("burn_in_daylight").getAsBoolean()) {
            entity.setBurnsInDaylight(false);
        }
    }
    
    /**
     * 强化猪灵
     */
    public static void enhancePiglin(EntityWrapper entity) {
        if (!ConfigManager.isMobEnabled("piglin")) {
            return;
        }
        
        // 设置属性
        entity.setMaxHealth(ConfigManager.getMobAttribute("piglin", "health", 16.0));
        entity.setAttackDamage(ConfigManager.getMobAttribute("piglin", "damage", 9.0));
        entity.setMovementSpeed(ConfigManager.getMobAttribute("piglin", "speed", 0.30));
    }
    
    /**
     * 强化幻翼
     */
    public static void enhancePhantom(EntityWrapper entity) {
        if (!ConfigManager.isMobEnabled("phantom")) {
            return;
        }
        
        // 幻翼伤害根据体型变化，这里设置基础伤害
        // 具体实现在平台适配器中
    }
    
    /**
     * 强化守卫者
     */
    public static void enhanceGuardian(EntityWrapper entity) {
        if (!ConfigManager.isMobEnabled("guardian")) {
            return;
        }
        
        // 设置属性
        entity.setMaxHealth(ConfigManager.getMobAttribute("guardian", "health", 30.0));
        entity.setAttackDamage(ConfigManager.getMobAttribute("guardian", "damage", 6.0));
        entity.setMovementSpeed(ConfigManager.getMobAttribute("guardian", "speed", 0.5));
        entity.setFollowRange(ConfigManager.getMobAttribute("guardian", "follow_range", 16.0));
    }
    
    /**
     * 强化疣猪兽
     */
    public static void enhanceHoglin(EntityWrapper entity) {
        if (!ConfigManager.isMobEnabled("hoglin")) {
            return;
        }
        
        // 设置属性
        entity.setMaxHealth(ConfigManager.getMobAttribute("hoglin", "health", 40.0));
        entity.setAttackDamage(ConfigManager.getMobAttribute("hoglin", "damage", 11.0));
        entity.setMovementSpeed(ConfigManager.getMobAttribute("hoglin", "speed", 0.3));
        entity.setKnockbackResistance(ConfigManager.getMobAttribute("hoglin", "knockback_resistance", 0.6));
    }
    
    /**
     * 铁傀儡攻击玩家
     */
    public static void handleIronGolemBehavior(EntityWrapper entity) {
        if (!ConfigManager.isMobEnabled("iron_golem")) {
            return;
        }
        
        // 铁傀儡攻击最近的玩家
        // 具体实现在平台适配器中
    }
    
    /**
     * 检查是否应该生成强化装备
     */
    public static boolean shouldGenerateEnhancedArmor(String mobType) {
        return ConfigManager.getMobConfig(mobType).has("enhanced_armor") && 
               ConfigManager.getMobConfig(mobType).get("enhanced_armor").getAsBoolean();
    }
    
    /**
     * 检查是否应该生成强化附魔
     */
    public static boolean shouldGenerateEnhancedEnchantments(String mobType) {
        return ConfigManager.getMobConfig(mobType).has("enhanced_enchantments") && 
               ConfigManager.getMobConfig(mobType).get("enhanced_enchantments").getAsBoolean();
    }
    
    /**
     * 获取生成倍数
     */
    public static int getSpawnMultiplier() {
        return ConfigManager.getWorldConfig().has("spawn_multiplier") ? 
               ConfigManager.getWorldConfig().get("spawn_multiplier").getAsInt() : 8;
    }
    
    /**
     * 检查是否启用雷电密度
     */
    public static boolean isThunderDensityEnabled() {
        return ConfigManager.getWorldConfig().has("thunder_density") && 
               ConfigManager.getWorldConfig().get("thunder_density").getAsBoolean();
    }
    
    /**
     * 获取骷髅马生成概率
     */
    public static double getSkeletonHorseChance() {
        return ConfigManager.getWorldConfig().has("skeleton_horse_chance") ? 
               ConfigManager.getWorldConfig().get("skeleton_horse_chance").getAsDouble() : 0.01;
    }
}
