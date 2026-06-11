package xyz.mocoder.bravesurvival.paper.entity;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
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
    
    private AttributeInstance getAttribute(String key) {
        try {
            Attribute attr = Attribute.valueOf(key);
            return entity.getAttribute(attr);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    @Override
    public void setMaxHealth(double health) {
        AttributeInstance attr = getAttribute("GENERIC_MAX_HEALTH");
        if (attr != null) {
            attr.setBaseValue(health);
        }
    }
    
    @Override
    public void setAttackDamage(double damage) {
        AttributeInstance attr = getAttribute("GENERIC_ATTACK_DAMAGE");
        if (attr != null) {
            attr.setBaseValue(damage);
        }
    }
    
    @Override
    public void setMovementSpeed(double speed) {
        AttributeInstance attr = getAttribute("GENERIC_MOVEMENT_SPEED");
        if (attr != null) {
            attr.setBaseValue(speed);
        }
    }
    
    @Override
    public void setFollowRange(double range) {
        AttributeInstance attr = getAttribute("GENERIC_FOLLOW_RANGE");
        if (attr != null) {
            attr.setBaseValue(range);
        }
    }
    
    @Override
    public void setArmor(double armor) {
        AttributeInstance attr = getAttribute("GENERIC_ARMOR");
        if (attr != null) {
            attr.setBaseValue(armor);
        }
    }
    
    @Override
    public void setKnockbackResistance(double resistance) {
        AttributeInstance attr = getAttribute("GENERIC_KNOCKBACK_RESISTANCE");
        if (attr != null) {
            attr.setBaseValue(resistance);
        }
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
        return entity.getWorld().isDayTime() && 
               !entity.getWorld().hasStorm() && 
               entity.getLocation().getY() > entity.getWorld().getHighestBlockYAt(entity.getLocation());
    }
    
    @Override
    public void setBurnsInDaylight(boolean burns) {
        // 需要在事件监听器中实现
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
    
    public LivingEntity getEntity() {
        return entity;
    }
}
