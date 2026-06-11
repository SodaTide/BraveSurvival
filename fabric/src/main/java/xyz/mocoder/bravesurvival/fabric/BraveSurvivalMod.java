package xyz.mocoder.bravesurvival.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.mocoder.bravesurvival.core.config.ConfigManager;
import xyz.mocoder.bravesurvival.fabric.events.FabricEventAdapter;

import java.io.File;
import java.nio.file.Path;

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
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            Path runDir = server.getRunDirectory();
            File dataFolder = runDir.resolve("config").resolve("bravesurvival").toFile();
            ConfigManager.initialize(dataFolder);
            LOGGER.info("配置已加载");
        });
        
        // 注册事件适配器
        FabricEventAdapter.registerEvents();
        
        LOGGER.info("BraveSurvival 模组初始化完成！");
    }
}
