package xyz.mocoder.bravesurvival.paper;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.mocoder.bravesurvival.core.config.ConfigManager;
import xyz.mocoder.bravesurvival.core.logic.mob.MobEnhancer;
import xyz.mocoder.bravesurvival.core.logic.player.PlayerLogic;
import xyz.mocoder.bravesurvival.core.logic.world.WorldLogic;
import xyz.mocoder.bravesurvival.paper.entity.PaperEntityWrapper;

/**
 * BraveSurvival Paper插件主类
 */
public class BraveSurvivalPlugin extends JavaPlugin implements Listener {
    
    @Override
    public void onEnable() {
        getLogger().info("正在启用 BraveSurvival 插件...");
        
        // 初始化配置
        ConfigManager.initialize(getDataFolder());
        
        // 注册事件监听器
        getServer().getPluginManager().registerEvents(this, this);
        
        // 注册命令
        getCommand("bravesurvival").setExecutor((sender, command, label, args) -> {
            if (args.length > 0 && args[0].equals("reload")) {
                ConfigManager.reloadConfig();
                sender.sendMessage("§a配置已重新加载！");
                return true;
            }
            sender.sendMessage("§eBraveSurvival 插件 v" + getDescription().getVersion());
            sender.sendMessage("§e使用 /bravesurvival reload 重新加载配置");
            return true;
        });
        
        getLogger().info("BraveSurvival 插件启用成功！");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("BraveSurvival 插件已禁用！");
    }
    
    /**
     * 监听怪物生成事件
     */
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        PaperEntityWrapper wrapper = new PaperEntityWrapper(entity);
        
        // 根据实体类型应用强化
        if (entity instanceof Zombie) {
            MobEnhancer.enhanceZombie(wrapper);
        } else if (entity instanceof Creeper) {
            MobEnhancer.enhanceCreeper(wrapper);
        } else if (entity instanceof Enderman) {
            MobEnhancer.enhanceEnderman(wrapper);
        } else if (entity instanceof Skeleton) {
            MobEnhancer.enhanceSkeleton(wrapper);
        } else if (entity instanceof Piglin) {
            MobEnhancer.enhancePiglin(wrapper);
        } else if (entity instanceof Phantom) {
            MobEnhancer.enhancePhantom(wrapper);
        } else if (entity instanceof Guardian) {
            MobEnhancer.enhanceGuardian(wrapper);
        } else if (entity instanceof Hoglin) {
            MobEnhancer.enhanceHoglin(wrapper);
        } else if (entity instanceof IronGolem) {
            MobEnhancer.handleIronGolemBehavior(wrapper);
        }
    }
    
    /**
     * 监听玩家伤害事件
     */
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            // 检查是否是摔落伤害
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                // 延迟应用debuff，确保伤害已应用
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (player.isOnline()) {
                            PaperEntityWrapper wrapper = new PaperEntityWrapper(player);
                            PlayerLogic.handleFallDamageDebuff(wrapper);
                        }
                    }
                }.runTaskLater(this, 1L);
            }
        }
    }
    
    /**
     * 监听方块破坏事件
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // 检查是否应该生成蠹虫
        String blockType = event.getBlock().getType().toString();
        if (WorldLogic.shouldSpawnSilverfish(blockType)) {
            // 检查概率
            if (Math.random() < WorldLogic.getSilverfishChance()) {
                // 生成蠹虫
                event.getBlock().getWorld().spawnEntity(
                    event.getBlock().getLocation().add(0.5, 0, 0.5),
                    EntityType.SILVERFISH
                );
            }
        }
    }
    
    /**
     * 监听船移动事件
     */
    @EventHandler
    public void onVehicleMove(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof Boat boat) {
            // 检查是否有玩家乘客
            if (boat.getPassengers().size() > 0 && boat.getPassengers().get(0) instanceof Player) {
                // 这里需要实现船下沉逻辑
                // 由于Paper API的限制，这可能需要更复杂的实现
            }
        }
    }
}
