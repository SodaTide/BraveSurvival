package xyz.mocoder.bravesurvival.core.logic.item;

import xyz.mocoder.bravesurvival.core.config.ConfigManager;

/**
 * 物品逻辑类
 * 处理物品相关的游戏逻辑
 */
public class ItemLogic {
    
    /**
     * 获取盾牌耐久度
     */
    public static int getShieldDurability() {
        return ConfigManager.getItemsConfig().has("shield_durability") ? 
               ConfigManager.getItemsConfig().get("shield_durability").getAsInt() : 200;
    }
    
    /**
     * 检查是否修改盾牌耐久度
     */
    public static boolean shouldModifyShieldDurability() {
        return ConfigManager.getItemsConfig().has("shield_durability");
    }
}
