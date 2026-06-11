package xyz.mocoder.bravesurvival.core.events;

/**
 * 事件适配器接口
 * 用于统一不同平台的事件系统
 */
public interface EventAdapter {
    
    /**
     * 注册怪物生成事件
     */
    void onMobSpawn(MobSpawnCallback callback);
    
    /**
     * 注册玩家伤害事件
     */
    void onPlayerDamage(PlayerDamageCallback callback);
    
    /**
     * 注册方块破坏事件
     */
    void onBlockBreak(BlockBreakCallback callback);
    
    /**
     * 注册船移动事件
     */
    void onBoatMove(BoatMoveCallback callback);
    
    /**
     * 注册雷电生成事件
     */
    void onThunderGeneration(ThunderGenerationCallback callback);
    
    /**
     * 怪物生成回调
     */
    interface MobSpawnCallback {
        void onMobSpawn(Object entity, String mobType);
    }
    
    /**
     * 玩家伤害回调
     */
    interface PlayerDamageCallback {
        void onPlayerDamage(Object player, String damageType, double damage);
    }
    
    /**
     * 方块破坏回调
     */
    interface BlockBreakCallback {
        void onBlockBreak(Object player, Object block, String blockType);
    }
    
    /**
     * 船移动回调
     */
    interface BoatMoveCallback {
        void onBoatMove(Object boat, Object passenger);
    }
    
    /**
     * 雷电生成回调
     */
    interface ThunderGenerationCallback {
        void onThunderGeneration(Object world, double x, double z);
    }
}
