package xyz.mocoder.bravesurvival.core.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 配置管理器
 * 负责加载和管理游戏配置
 */
public class ConfigManager {
    private static final Logger LOGGER = LoggerFactory.getLogger("BraveSurvival");
    private static final Gson GSON = new Gson();
    private static JsonObject config;
    private static File configFile;

    /**
     * 初始化配置管理器
     * @param dataFolder 数据文件夹
     */
    public static void initialize(File dataFolder) {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        
        configFile = new File(dataFolder, "config.json");
        
        if (!configFile.exists()) {
            createDefaultConfig();
        }
        
        loadConfig();
    }

    /**
     * 创建默认配置
     */
    private static void createDefaultConfig() {
        JsonObject defaultConfig = new JsonObject();
        
        // 怪物配置
        JsonObject mobs = new JsonObject();
        
        // 僵尸配置
        JsonObject zombie = new JsonObject();
        zombie.addProperty("enabled", true);
        zombie.addProperty("health", 20.0);
        zombie.addProperty("damage", 8.0);
        zombie.addProperty("speed", 0.35);
        zombie.addProperty("follow_range", 55.0);
        zombie.addProperty("armor", 3.0);
        zombie.addProperty("burn_in_daylight", false);
        zombie.addProperty("enhanced_armor", true);
        zombie.addProperty("enhanced_enchantments", true);
        mobs.add("zombie", zombie);
        
        // 苦力怕配置
        JsonObject creeper = new JsonObject();
        creeper.addProperty("enabled", true);
        creeper.addProperty("always_charged", true);
        creeper.addProperty("instant_fuse", true);
        creeper.addProperty("invisible", true);
        mobs.add("creeper", creeper);
        
        // 末影人配置
        JsonObject enderman = new JsonObject();
        enderman.addProperty("enabled", true);
        enderman.addProperty("health", 40.0);
        enderman.addProperty("damage", 14.0);
        enderman.addProperty("speed", 0.75);
        enderman.addProperty("follow_range", 64.0);
        enderman.addProperty("spawn_endermites", true);
        enderman.addProperty("destroy_blocks", true);
        mobs.add("enderman", enderman);
        
        // 骷髅配置
        JsonObject skeleton = new JsonObject();
        skeleton.addProperty("enabled", true);
        skeleton.addProperty("damage", 10.0);
        skeleton.addProperty("burn_in_daylight", false);
        skeleton.addProperty("enhanced_armor", true);
        skeleton.addProperty("enhanced_enchantments", true);
        mobs.add("skeleton", skeleton);
        
        // 猪灵配置
        JsonObject piglin = new JsonObject();
        piglin.addProperty("enabled", true);
        piglin.addProperty("health", 16.0);
        piglin.addProperty("damage", 9.0);
        piglin.addProperty("speed", 0.30);
        piglin.addProperty("arrow_speed", 3.2);
        mobs.add("piglin", piglin);
        
        // 幻翼配置
        JsonObject phantom = new JsonObject();
        phantom.addProperty("enabled", true);
        phantom.addProperty("base_damage", 9.0);
        phantom.addProperty("burn_in_daylight", false);
        mobs.add("phantom", phantom);
        
        // 守卫者配置
        JsonObject guardian = new JsonObject();
        guardian.addProperty("enabled", true);
        guardian.addProperty("health", 30.0);
        guardian.addProperty("damage", 6.0);
        guardian.addProperty("speed", 0.5);
        guardian.addProperty("follow_range", 16.0);
        mobs.add("guardian", guardian);
        
        // 疣猪兽配置
        JsonObject hoglin = new JsonObject();
        hoglin.addProperty("enabled", true);
        hoglin.addProperty("health", 40.0);
        hoglin.addProperty("damage", 11.0);
        hoglin.addProperty("speed", 0.3);
        hoglin.addProperty("knockback_resistance", 0.6);
        mobs.add("hoglin", hoglin);
        
        // 铁傀儡配置
        JsonObject ironGolem = new JsonObject();
        ironGolem.addProperty("enabled", true);
        ironGolem.addProperty("attack_players", true);
        mobs.add("iron_golem", ironGolem);
        
        defaultConfig.add("mobs", mobs);
        
        // 玩家配置
        JsonObject player = new JsonObject();
        player.addProperty("fall_damage_debuff", true);
        player.addProperty("weakness_duration", 10);
        player.addProperty("slowness_duration", 10);
        defaultConfig.add("player", player);
        
        // 世界配置
        JsonObject world = new JsonObject();
        world.addProperty("spawn_multiplier", 8);
        world.addProperty("thunder_density", true);
        world.addProperty("skeleton_horse_chance", 0.01);
        defaultConfig.add("world", world);
        
        // 方块配置
        JsonObject blocks = new JsonObject();
        blocks.addProperty("silverfish_chance", 0.0625); // 1/16
        blocks.add("silverfish_blocks", GSON.toJsonTree(new String[]{
            "minecraft:stone", "minecraft:sandstone", "minecraft:cobblestone",
            "minecraft:andesite", "minecraft:granite", "minecraft:basalt", "minecraft:blackstone"
        }));
        defaultConfig.add("blocks", blocks);
        
        // 物品配置
        JsonObject items = new JsonObject();
        items.addProperty("shield_durability", 200);
        defaultConfig.add("items", items);
        
        // 船配置
        JsonObject boat = new JsonObject();
        boat.addProperty("sink_after_ticks", 600);
        boat.addProperty("sink_speed_multiplier", 0.05);
        defaultConfig.add("boat", boat);
        
        // 配方配置
        JsonObject recipes = new JsonObject();
        recipes.addProperty("planks_per_log", 2);
        defaultConfig.add("recipes", recipes);
        
        // 保存配置
        try (Writer writer = new FileWriter(configFile)) {
            GSON.toJson(defaultConfig, writer);
            LOGGER.info("已创建默认配置文件: {}", configFile.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("无法创建配置文件", e);
        }
    }

    /**
     * 加载配置
     */
    private static void loadConfig() {
        try (Reader reader = new FileReader(configFile)) {
            config = GSON.fromJson(reader, JsonObject.class);
            LOGGER.info("已加载配置文件");
        } catch (IOException e) {
            LOGGER.error("无法加载配置文件", e);
            config = new JsonObject();
        }
    }

    /**
     * 重新加载配置
     */
    public static void reloadConfig() {
        loadConfig();
    }

    /**
     * 获取配置值
     */
    public static JsonObject getConfig() {
        return config;
    }

    /**
     * 获取怪物配置
     */
    public static JsonObject getMobConfig(String mobType) {
        if (config != null && config.has("mobs")) {
            JsonObject mobs = config.getAsJsonObject("mobs");
            if (mobs.has(mobType)) {
                return mobs.getAsJsonObject(mobType);
            }
        }
        return new JsonObject();
    }

    /**
     * 检查怪物是否启用
     */
    public static boolean isMobEnabled(String mobType) {
        JsonObject mobConfig = getMobConfig(mobType);
        return mobConfig.has("enabled") && mobConfig.get("enabled").getAsBoolean();
    }

    /**
     * 获取怪物属性值
     */
    public static double getMobAttribute(String mobType, String attribute, double defaultValue) {
        JsonObject mobConfig = getMobConfig(mobType);
        if (mobConfig.has(attribute)) {
            return mobConfig.get(attribute).getAsDouble();
        }
        return defaultValue;
    }

    /**
     * 获取玩家配置
     */
    public static JsonObject getPlayerConfig() {
        if (config != null && config.has("player")) {
            return config.getAsJsonObject("player");
        }
        return new JsonObject();
    }

    /**
     * 获取世界配置
     */
    public static JsonObject getWorldConfig() {
        if (config != null && config.has("world")) {
            return config.getAsJsonObject("world");
        }
        return new JsonObject();
    }

    /**
     * 获取方块配置
     */
    public static JsonObject getBlocksConfig() {
        if (config != null && config.has("blocks")) {
            return config.getAsJsonObject("blocks");
        }
        return new JsonObject();
    }

    /**
     * 获取物品配置
     */
    public static JsonObject getItemsConfig() {
        if (config != null && config.has("items")) {
            return config.getAsJsonObject("items");
        }
        return new JsonObject();
    }

    /**
     * 获取船配置
     */
    public static JsonObject getBoatConfig() {
        if (config != null && config.has("boat")) {
            return config.getAsJsonObject("boat");
        }
        return new JsonObject();
    }

    /**
     * 获取配方配置
     */
    public static JsonObject getRecipesConfig() {
        if (config != null && config.has("recipes")) {
            return config.getAsJsonObject("recipes");
        }
        return new JsonObject();
    }
}
