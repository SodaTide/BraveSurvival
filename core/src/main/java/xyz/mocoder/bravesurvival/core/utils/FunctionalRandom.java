package xyz.mocoder.bravesurvival.core.utils;

import java.util.Random;

/**
 * 功能性随机数生成器
 * 用于雷雨天雷电生成位置的计算，使用Box-Muller算法生成正态分布
 */
public class FunctionalRandom {
    
    /**
     * Box-Muller算法生成正态分布随机数
     * 玩家周围一定距离不生成雷电
     * 
     * @param random 随机数生成器
     * @return 正态分布的随机数
     */
    public static double generateRandom(Random random) {
        double u = random.nextDouble();
        double v = random.nextDouble();
        double res = Math.sqrt(-2 * Math.log(u)) * Math.cos(2 * Math.PI * v);
        
        // 确保最小距离
        if (res >= 0) {
            res += 0.4D;
        } else {
            res -= 0.4D;
        }
        
        return res;
    }
    
    /**
     * 生成指定范围内的正态分布随机数
     * 
     * @param random 随机数生成器
     * @param mean 均值
     * @param standardDeviation 标准差
     * @return 正态分布的随机数
     */
    public static double generateGaussian(Random random, double mean, double standardDeviation) {
        double u = random.nextDouble();
        double v = random.nextDouble();
        double z = Math.sqrt(-2 * Math.log(u)) * Math.cos(2 * Math.PI * v);
        return mean + z * standardDeviation;
    }
    
    /**
     * 生成玩家附近的随机位置
     * 
     * @param random 随机数生成器
     * @param playerX 玩家X坐标
     * @param playerZ 玩家Z坐标
     * @param distance 距离
     * @return 随机位置数组 [x, z]
     */
    public static double[] generateNearbyPosition(Random random, double playerX, double playerZ, double distance) {
        double offsetX = generateRandom(random) * distance;
        double offsetZ = generateRandom(random) * distance;
        return new double[]{playerX + offsetX, playerZ + offsetZ};
    }
}
