package xyz.mocoder.bravesurvival.fabric.mixin.zombie;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.mocoder.bravesurvival.fabric.entity.FabricEntityWrapper;
import xyz.mocoder.bravesurvival.core.logic.mob.MobEnhancer;

/**
 * 僵尸实体Mixin
 * 用于强化僵尸属性
 */
@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends HostileEntity {
    
    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }
    
    /**
     * 注入僵尸属性创建方法
     * 修改僵尸的基础属性
     */
    @Inject(method = "createZombieAttributes", at = @At("HEAD"), cancellable = true)
    private static void enhanceZombieAttributes(CallbackInfoReturnable cir) {
        // 使用核心逻辑强化僵尸
        // 注意：这里我们需要返回修改后的属性构建器
        // 由于Mixin的限制，我们返回一个新的构建器
        cir.setReturnValue(
            HostileEntity.createHostileAttributes()
                .add(net.minecraft.entity.attribute.EntityAttributes.GENERIC_MAX_HEALTH, 
                     MobEnhancer.getMobAttribute("zombie", "health", 20.0))
                .add(net.minecraft.entity.attribute.EntityAttributes.GENERIC_ATTACK_DAMAGE, 
                     MobEnhancer.getMobAttribute("zombie", "damage", 8.0))
                .add(net.minecraft.entity.attribute.EntityAttributes.GENERIC_MOVEMENT_SPEED, 
                     MobEnhancer.getMobAttribute("zombie", "speed", 0.35))
                .add(net.minecraft.entity.attribute.EntityAttributes.GENERIC_FOLLOW_RANGE, 
                     MobEnhancer.getMobAttribute("zombie", "follow_range", 55.0))
                .add(net.minecraft.entity.attribute.EntityAttributes.GENERIC_ARMOR, 
                     MobEnhancer.getMobAttribute("zombie", "armor", 3.0))
        );
    }
    
    /**
     * 注入僵尸tick方法
     * 确保僵尸不在日光下燃烧
     */
    @Inject(method = "burnsInDaylight", at = @At("HEAD"), cancellable = true)
    private void disableDaylightBurning(CallbackInfoReturnable cir) {
        // 检查配置是否禁止僵尸在日光下燃烧
        if (!MobEnhancer.getMobConfig("zombie").has("burn_in_daylight") || 
            !MobEnhancer.getMobConfig("zombie").get("burn_in_daylight").getAsBoolean()) {
            cir.setReturnValue(false);
        }
    }
}
