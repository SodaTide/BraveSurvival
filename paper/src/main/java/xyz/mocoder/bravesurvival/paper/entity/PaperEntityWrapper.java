package xyz.mocoder.bravesurvival.paper.entity;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.mocoder.bravesurvival.core.entity.EntityWrapper;

/**
 * Paper实体包装器实现
 */
public class PaperEntityWrapper implements EntityWrapper {
    private final LivingEntity entity;
    
    public PaperEntityWrapper(LivingEntity entity) {
        this.entity = entity;
    }
    
    @Override
    public void setMaxHealth(double health) {
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
    }
    
    @Override
    public void setAttackDamage(double damage) {
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
    }
    
    @Override
    public void setMovementSpeed(double speed) {
        entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
    }
    
    @Override
    public void setFollowRange(double range) {
        entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(range);
    }
    
    @Override
    public void setArmor(double armor) {
        entity.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(armor);
    }
    
    @Override
    public void setKnockbackResistance(double resistance) {
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(resistance);
    }
    
    @Override
    public void addStatusEffect(String effect, int duration, int amplifier) {
        PotionEffectType effectType = switch (effect) {
            case "WEAKNESS" -> PotionEffectType.WEAKNESS;
            case "SLOWNESS" -> PotionEffectType.SLOWNESS;
            case "INVISIBILITY" -> PotionEffectType.INVISIBILITY;
            default -> null;
        };
        
        if (effectType != null) {
            entity.addPotionEffect(new PotionEffect(effectType, duration, amplifier, false, false));
        }
    }
    
    @Override
    public boolean isInDaylight() {
        // 检查实体是否在日光下
        return entity.getWorld().isDayTime() && 
               !entity.getWorld().hasStorm() && 
               entity.getLocation().getY() > entity.getWorld().getHighestBlockYAt(entity.getLocation());
    }
    
    @Override
    public void setBurnsInDaylight(boolean burns) {
        // 这个需要在事件监听器中实现
        // Paper API没有直接的方法来设置这个
    }
    
    @Override
    public String getEntityType() {
        return entity.getType().toString();
    }
    
    @Override
    public Object getWorld() {
        return entity.getWorld();
    }
    
    @Override
    public double getX() {
        return entity.getLocation().getX();
    }
    
    @Override
    public double getY() {
        return entity.getLocation().getY();
    }
    
    @Override
    public double getZ() {
        return entity.getLocation().getZ();
    }
    
    @Override
    public void setPosition(double x, double y, double z) {
        entity.teleport(entity.getLocation().clone().add(x - getX(), y - getY(), z - getZ()));
    }
    
    @Override
    public void spawnEntity(Object entity) {
        if (entity instanceof LivingEntity livingEntity) {
            this.entity.getWorld().spawnEntity(this.entity.getLocation(), livingEntity.getType());
        }
    }
    
    /**
     * 获取原始实体
     */
    public LivingEntity getEntity() {
        return entity;
    }
}
