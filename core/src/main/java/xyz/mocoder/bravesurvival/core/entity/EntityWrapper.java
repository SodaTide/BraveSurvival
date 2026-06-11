package xyz.mocoder.bravesurvival.core.entity;

/**
 * 实体包装器接口
 * 用于统一不同平台的实体属性设置
 */
public interface EntityWrapper {
    
    /**
     * 设置最大生命值
     */
    void setMaxHealth(double health);
    
    /**
     * 设置攻击力
     */
    void setAttackDamage(double damage);
    
    /**
     * 设置移动速度
     */
    void setMovementSpeed(double speed);
    
    /**
     * 设置追踪范围
     */
    void setFollowRange(double range);
    
    /**
     * 设置护甲值
     */
    void setArmor(double armor);
    
    /**
     * 设置击退抗性
     */
    void setKnockbackResistance(double resistance);
    
    /**
     * 添加状态效果
     */
    void addStatusEffect(String effect, int duration, int amplifier);
    
    /**
     * 检查是否在日光下
     */
    boolean isInDaylight();
    
    /**
     * 设置是否在日光下燃烧
     */
    void setBurnsInDaylight(boolean burns);
    
    /**
     * 获取实体类型
     */
    String getEntityType();
    
    /**
     * 获取世界对象
     */
    Object getWorld();
    
    /**
     * 获取X坐标
     */
    double getX();
    
    /**
     * 获取Y坐标
     */
    double getY();
    
    /**
     * 获取Z坐标
     */
    double getZ();
    
    /**
     * 设置位置
     */
    void setPosition(double x, double y, double z);
    
    /**
     * 生成实体
     */
    void spawnEntity(Object entity);
}
