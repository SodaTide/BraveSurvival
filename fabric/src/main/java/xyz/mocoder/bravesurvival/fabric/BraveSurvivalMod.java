package xyz.mocoder.bravesurvival.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.mocoder.bravesurvival.core.config.ConfigManager;
import xyz.mocoder.bravesurvival.fabric.events.FabricEventAdapter;

/**
 * BraveSurvival Fabric模组主类
 */
public class BraveSurvivalMod implements ModInitializer {
    public static final String MOD_ID = "bravesurvival";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("正在初始化 BraveSurvival 模组...");
        
        // 初始化配置
        // 注意：Fabric模组中，数据文件夹需要在运行时获取
        // 这里我们使用一个临时方案，在服务器启动时初始化
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            // 获取服务器数据文件夹
            java.io.File dataFolder = new java.io.File(server.getRunDirectory(), "config/bravesurvival");
            ConfigManager.initialize(dataFolder);
            LOGGER.info("配置已加载");
        });
        
        // 注册事件适配器
        FabricEventAdapter.registerEvents();
        
        LOGGER.info("BraveSurvival 模组初始化完成！");
    }
}
