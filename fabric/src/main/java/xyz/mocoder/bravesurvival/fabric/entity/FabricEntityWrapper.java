package xyz.mocoder.bravesurvival.fabric.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import xyz.mocoder.bravesurvival.core.entity.EntityWrapper;

/**
 * Fabric实体包装器实现
 */
public class FabricEntityWrapper implements EntityWrapper {
    private final LivingEntity entity;
    
    public FabricEntityWrapper(LivingEntity entity) {
        this.entity = entity;
    }
    
    @Override
    public void setMaxHealth(double health) {
        entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(health);
    }
    
    @Override
    public void setAttackDamage(double damage) {
        entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
    }
    
    @Override
    public void setMovementSpeed(double speed) {
        entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
    }
    
    @Override
    public void setFollowRange(double range) {
        entity.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE).setBaseValue(range);
    }
    
    @Override
    public void setArmor(double armor) {
        entity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(armor);
    }
    
    @Override
    public void setKnockbackResistance(double resistance) {
        entity.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(resistance);
    }
    
    @Override
    public void addStatusEffect(String effect, int duration, int amplifier) {
        StatusEffectInstance statusEffect = switch (effect) {
            case "WEAKNESS" -> new StatusEffectInstance(StatusEffects.WEAKNESS, duration, amplifier, false, false);
            case "SLOWNESS" -> new StatusEffectInstance(StatusEffects.SLOWNESS, duration, amplifier, false, false);
            case "INVISIBILITY" -> new StatusEffectInstance(StatusEffects.INVISIBILITY, duration, amplifier, false, false);
            default -> null;
        };
        
        if (statusEffect != null) {
            entity.addStatusEffect(statusEffect);
        }
    }
    
    @Override
    public boolean isInDaylight() {
        // 检查实体是否在日光下
        return entity.world.isDay() && !entity.world.isRaining() && 
               entity.world.isSkyVisible(entity.getBlockPos());
    }
    
    @Override
    public void setBurnsInDaylight(boolean burns) {
        // 这个需要在Mixin中实现
        // 因为burnsInDaylight是ZombieEntity的方法
    }
    
    @Override
    public String getEntityType() {
        return entity.getType().toString();
    }
    
    @Override
    public Object getWorld() {
        return entity.world;
    }
    
    @Override
    public double getX() {
        return entity.getX();
    }
    
    @Override
    public double getY() {
        return entity.getY();
    }
    
    @Override
    public double getZ() {
        return entity.getZ();
    }
    
    @Override
    public void setPosition(double x, double y, double z) {
        entity.setPosition(x, y, z);
    }
    
    @Override
    public void spawnEntity(Object entity) {
        if (this.entity.world instanceof ServerWorld serverWorld && entity instanceof LivingEntity livingEntity) {
            serverWorld.spawnEntity(livingEntity);
        }
    }
    
    /**
     * 获取原始实体
     */
    public LivingEntity getEntity() {
        return entity;
    }
}
