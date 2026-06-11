package xyz.mocoder.bravesurvival.fabric.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import xyz.mocoder.bravesurvival.core.config.ConfigManager;

/**
 * Fabric事件适配器实现
 * 主要逻辑通过Mixin注入，这里只注册配置加载事件
 */
public class FabricEventAdapter {
    
    /**
     * 注册所有事件
     */
    public static void registerEvents() {
        // 配置加载在BraveSurvivalMod中处理
        // 主要的怪物强化逻辑通过Mixin注入到各个实体类中
    }
}
