package xyz.mocoder.bravesurvival.fabric.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.server.world.ServerWorld;
import xyz.mocoder.bravesurvival.core.events.EventAdapter;
import xyz.mocoder.bravesurvival.fabric.entity.FabricEntityWrapper;
import xyz.mocoder.bravesurvival.core.logic.mob.MobEnhancer;

import java.util.ArrayList;
import java.util.List;

/**
 * Fabric事件适配器实现
 */
public class FabricEventAdapter {
    
    /**
     * 注册所有事件
     */
    public static void registerEvents() {
        // 注册服务器tick事件，用于处理怪物强化
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                // 处理怪物强化
                for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, 
                        entity -> entity instanceof ZombieEntity || 
                                  entity instanceof CreeperEntity ||
                                  entity instanceof EndermanEntity ||
                                  entity instanceof SkeletonEntity ||
                                  entity instanceof PiglinEntity ||
                                  entity instanceof PhantomEntity ||
                                  entity instanceof GuardianEntity ||
                                  entity instanceof HoglinEntity ||
                                  entity instanceof IronGolemEntity)) {
                    
                    FabricEntityWrapper wrapper = new FabricEntityWrapper(entity);
                    
                    if (entity instanceof ZombieEntity) {
                        MobEnhancer.enhanceZombie(wrapper);
                    } else if (entity instanceof CreeperEntity) {
                        MobEnhancer.enhanceCreeper(wrapper);
                    } else if (entity instanceof EndermanEntity) {
                        MobEnhancer.enhanceEnderman(wrapper);
                    } else if (entity instanceof SkeletonEntity) {
                        MobEnhancer.enhanceSkeleton(wrapper);
                    } else if (entity instanceof PiglinEntity) {
                        MobEnhancer.enhancePiglin(wrapper);
                    } else if (entity instanceof PhantomEntity) {
                        MobEnhancer.enhancePhantom(wrapper);
                    } else if (entity instanceof GuardianEntity) {
                        MobEnhancer.enhanceGuardian(wrapper);
                    } else if (entity instanceof HoglinEntity) {
                        MobEnhancer.enhanceHoglin(wrapper);
                    } else if (entity instanceof IronGolemEntity) {
                        MobEnhancer.handleIronGolemBehavior(wrapper);
                    }
                }
            }
        });
        
        // 注册方块破坏事件
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            // 处理方块破坏逻辑
            // 这里需要检查是否应该生成蠹虫
        });
    }
}
