package xyz.mocoder.bravesurvival.paper;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

/**
 * 实体行为管理器
 * 负责处理各种实体的特殊行为
 */
public class EntityBehaviorManager implements Listener {
    
    private final BraveSurvivalPlugin plugin;
    private final Random random = new Random();
    
    public EntityBehaviorManager(BraveSurvivalPlugin plugin) {
        this.plugin = plugin;
    }
    
    /**
     * 监听实体生成事件 - 流浪商人替换
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onCreatureSpawnForWanderingTrader(CreatureSpawnEvent event) {
        if (event.isCancelled()) return;
        
        // 流浪商人替换为掠夺者小队
        if (event.getEntity() instanceof WanderingTrader trader) {
            if (plugin.getConfigManager().getMobConfig("wandering_trader") != null && 
                plugin.getConfigManager().getMobConfig("wandering_trader").has("replace_with_pillagers")) {
                
                // 移除流浪商人
                trader.remove();
                
                // 生成掠夺者小队
                Location loc = trader.getLocation();
                for (int i = 0; i < 3; i++) {
                    Pillager pillager = loc.getWorld().spawn(loc, Pillager.class);
                    pillager.getEquipment().setItemInMainHand(new ItemStack(Material.CROSSBOW));
                }
            }
        }
    }
    
    /**
     * 监听实体生成事件 - 掠夺者行为
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onCreatureSpawnForPillager(CreatureSpawnEvent event) {
        if (event.isCancelled()) return;
        
        // 掠夺者装备和行为
        if (event.getEntity() instanceof Pillager pillager) {
            if (plugin.getConfigManager().getMobConfig("pillager") != null) {
                // 快速装填5、多重射击
                if (plugin.getConfigManager().getMobConfig("pillager").has("quick_charge_5") && 
                    plugin.getConfigManager().getMobConfig("pillager").get("quick_charge_5").getAsBoolean()) {
                    ItemStack crossbow = new ItemStack(Material.CROSSBOW);
                    // 注意：Paper API可能不支持直接设置附魔
                    pillager.getEquipment().setItemInMainHand(crossbow);
                }
                
                // 劫掠兽伴随
                if (plugin.getConfigManager().getMobConfig("pillager").has("ravager_with_pillager") && 
                    plugin.getConfigManager().getMobConfig("pillager").get("ravager_with_pillager").getAsBoolean()) {
                    // 生成劫掠兽
                    pillager.getWorld().spawn(pillager.getLocation(), Ravager.class);
                }
            }
        }
    }
    
    /**
     * 监听实体生成事件 - 女巫行为
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onCreatureSpawnForWitch(CreatureSpawnEvent event) {
        if (event.isCancelled()) return;
        
        // 女巫与凋灵骷髅一起生成
        if (event.getEntity() instanceof Witch witch) {
            if (plugin.getConfigManager().getMobConfig("witch") != null && 
                plugin.getConfigManager().getMobConfig("witch").has("spawn_with_wither_skeletons")) {
                // 生成凋灵骷髅
                witch.getWorld().spawn(witch.getLocation(), WitherSkeleton.class);
            }
        }
    }
    
    /**
     * 监听实体攻击事件 - 女巫行为
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntityForWitch(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        
            // 女巫受伤时召唤药水
        if (event.getEntity() instanceof Witch witch && event.getDamager() instanceof Player) {
            if (plugin.getConfigManager().getMobConfig("witch") != null && 
                plugin.getConfigManager().getMobConfig("witch").has("splash_potions_on_damage")) {
                // 召唤喷溅药水
                AreaEffectCloud cloud = witch.getWorld().spawn(witch.getLocation(), AreaEffectCloud.class);
                cloud.setBasePotionData(new org.bukkit.potion.PotionData(org.bukkit.potion.PotionType.TURTLE_MASTER));
                cloud.setRadius(3);
                cloud.setDuration(200);
            }
        }
    }
    
    /**
     * 监听实体生成事件 - 幻术师行为
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onCreatureSpawnForIllusioner(CreatureSpawnEvent event) {
        if (event.isCancelled()) return;
        
        // 幻术师装备
        if (event.getEntity() instanceof Illusioner illusioner) {
            if (plugin.getConfigManager().getMobConfig("illusioner") != null) {
                // 力量弓
                if (plugin.getConfigManager().getMobConfig("illusioner").has("power_bow") && 
                    plugin.getConfigManager().getMobConfig("illusioner").get("power_bow").getAsBoolean()) {
                    illusioner.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
                }
            }
        }
    }
    
    /**
     * 监听实体攻击事件 - 末影人破坏方块
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntityForEndermanBlock(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        
        // 末影人破坏头部附近方块
        if (event.getDamager() instanceof Enderman enderman && event.getEntity() instanceof Player) {
            if (plugin.getConfigManager().getMobConfig("enderman").has("destroy_blocks") && 
                plugin.getConfigManager().getMobConfig("enderman").get("destroy_blocks").getAsBoolean()) {
                // 破坏头部附近方块
                Location headLoc = enderman.getLocation().add(0, 1, 0);
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            org.bukkit.block.Block block = headLoc.getBlock().getRelative(x, y, z);
                            if (block.getType() != Material.AIR && 
                                block.getType() != Material.BEDROCK && 
                                block.getType() != Material.OBSIDIAN) {
                                block.breakNaturally();
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 监听实体攻击事件 - 凋灵骷髅破坏方块
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntityForWitherSkeleton(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        
        // 凋灵骷髅给予凋灵效果并破坏方块
        if (event.getDamager() instanceof WitherSkeleton witherSkeleton && event.getEntity() instanceof Player player) {
            if (plugin.getConfigManager().getMobConfig("wither_skeleton") != null) {
                // 凋灵效果
                if (plugin.getConfigManager().getMobConfig("wither_skeleton").has("wither_effect") && 
                    plugin.getConfigManager().getMobConfig("wither_skeleton").get("wither_effect").getAsBoolean()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 1, false, false));
                }
                
                // 破坏骷髅附近方块
                if (plugin.getConfigManager().getMobConfig("wither_skeleton").has("destroy_blocks_near_skulls") && 
                    plugin.getConfigManager().getMobConfig("wither_skeleton").get("destroy_blocks_near_skulls").getAsBoolean()) {
                    Location skullLoc = witherSkeleton.getLocation().add(0, 1, 0);
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            for (int z = -1; z <= 1; z++) {
                                org.bukkit.block.Block block = skullLoc.getBlock().getRelative(x, y, z);
                                if (block.getType() != Material.AIR && 
                                    block.getType() != Material.BEDROCK && 
                                    block.getType() != Material.OBSIDIAN) {
                                    block.breakNaturally();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 监听实体伤害事件 - 着火火焰轨迹
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamageForFireTrail(EntityDamageEvent event) {
        if (event.isCancelled()) return;
        
        // 着火时留下火焰轨迹
        if (event.getEntity() instanceof Player player) {
            if (player.getFireTicks() > 0) {
                if (plugin.getConfigManager().getPlayerConfig().has("fire_trail_on_fire") && 
                    plugin.getConfigManager().getPlayerConfig().get("fire_trail_on_fire").getAsBoolean()) {
                    // 在玩家位置放置火焰
                    if (Math.random() < 0.1) { // 10%概率
                        Location loc = player.getLocation();
                        if (loc.getBlock().getType() == Material.AIR) {
                            loc.getBlock().setType(Material.FIRE);
                        }
                    }
                    
                    // 给予虚弱
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20, 0, false, false));
                }
            }
        }
    }
    
    /**
     * 监听玩家移动事件 - 桶漏水
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractForBucketLeak(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        
        // 桶漏水
        if (event.getItem() != null && 
            (event.getItem().getType() == Material.WATER_BUCKET || 
             event.getItem().getType() == Material.LAVA_BUCKET)) {
            if (plugin.getConfigManager().getItemsConfig().has("buckets_leak") && 
                plugin.getConfigManager().getItemsConfig().get("buckets_leak").getAsBoolean()) {
                if (Math.random() < 0.1) { // 10%概率
                    // 清空桶
                    event.getItem().setType(Material.BUCKET);
                    event.getPlayer().sendMessage("§c你的桶漏水了！");
                }
            }
        }
    }
    
    /**
     * 监听方块放置事件 - 下界炼药锅干涸
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlaceForCauldron(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        
        // 下界炼药锅干涸
        if (event.getBlock().getType() == Material.CAULDRON || 
            event.getBlock().getType() == Material.WATER_CAULDRON || 
            event.getBlock().getType() == Material.LAVA_CAULDRON) {
            if (event.getBlock().getWorld().getEnvironment() == World.Environment.NETHER) {
                if (plugin.getConfigManager().getBlocksConfig().has("cauldron_dries_in_nether") && 
                    plugin.getConfigManager().getBlocksConfig().get("cauldron_dries_in_nether").getAsBoolean()) {
                    // 延迟干涸
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (event.getBlock().getType() == Material.WATER_CAULDRON || 
                                event.getBlock().getType() == Material.LAVA_CAULDRON) {
                                event.getBlock().setType(Material.CAULDRON);
                            }
                        }
                    }.runTaskLater(plugin, 100L); // 5秒后
                }
            }
        }
    }
    
    /**
     * 监听实体爆炸事件 - 恶魂火球更大爆炸
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityExplodeForGhastFireball(EntityExplodeEvent event) {
        if (event.isCancelled()) return;
        
        // 恶魂火球更大爆炸
        if (event.getEntity() instanceof Fireball fireball) {
            if (plugin.getConfigManager().getMobConfig("ghast") != null && 
                plugin.getConfigManager().getMobConfig("ghast").has("larger_explosions") && 
                plugin.getConfigManager().getMobConfig("ghast").get("larger_explosions").getAsBoolean()) {
                // 增加爆炸范围
                event.setYield(event.getYield() * 2);
            }
        }
    }
    
    /**
     * 监听实体生成事件 - 岩浆气泡流更强大
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMoveForMagma(PlayerMoveEvent event) {
        if (event.isCancelled()) return;
        
        // 岩浆气泡流更强大
        if (plugin.getConfigManager().getBlocksConfig().has("magma_bubble_streams_more_powerful") && 
            plugin.getConfigManager().getBlocksConfig().get("magma_bubble_streams_more_powerful").getAsBoolean()) {
            if (event.getPlayer().getLocation().getBlock().getType() == Material.MAGMA_BLOCK) {
                // 增加伤害
                if (Math.random() < 0.1) { // 10%概率
                    event.getPlayer().damage(1.0);
                }
            }
        }
    }
}
