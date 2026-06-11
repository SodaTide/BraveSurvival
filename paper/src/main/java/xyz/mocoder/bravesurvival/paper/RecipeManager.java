package xyz.mocoder.bravesurvival.paper;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.NamespacedKey;

/**
 * 配方管理器
 * 负责修改游戏配方
 */
public class RecipeManager {
    
    private final BraveSurvivalPlugin plugin;
    
    public RecipeManager(BraveSurvivalPlugin plugin) {
        this.plugin = plugin;
    }
    
    /**
     * 注册所有配方修改
     */
    public void registerRecipes() {
        // 木板配方：1个木头 → 2个木板
        registerPlanksRecipe();
        
        // 烈焰棒配方：1个烈焰棒 → 1个烈焰粉
        registerBlazePowderRecipe();
        
        // 金萝卜配方：需要金锭而非金粒
        registerGoldenCarrotRecipe();
        
        // 闪烁西瓜配方：需要金锭而非金粒
        registerGlisteringMelonRecipe();
    }
    
    /**
     * 注册木板配方
     */
    private void registerPlanksRecipe() {
        // 移除原版木板配方
        // 注意：Paper API没有直接移除配方的方法，我们需要覆盖
        
        // 注册新的木板配方：1个木头 → 2个木板
        Material[] logs = {
            Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG,
            Material.JUNGLE_LOG, Material.ACACIA_LOG, Material.DARK_OAK_LOG,
            Material.CRIMSON_STEM, Material.WARPED_STEM,
            Material.STRIPPED_OAK_LOG, Material.STRIPPED_SPRUCE_LOG,
            Material.STRIPPED_BIRCH_LOG, Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_ACACIA_LOG, Material.STRIPPED_DARK_OAK_LOG,
            Material.STRIPPED_CRIMSON_STEM, Material.STRIPPED_WARPED_STEM
        };
        
        Material[] planks = {
            Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.BIRCH_PLANKS,
            Material.JUNGLE_PLANKS, Material.ACACIA_PLANKS, Material.DARK_OAK_PLANKS,
            Material.CRIMSON_PLANKS, Material.WARPED_PLANKS,
            Material.OAK_PLANKS, Material.SPRUCE_PLANKS,
            Material.BIRCH_PLANKS, Material.JUNGLE_PLANKS,
            Material.ACACIA_PLANKS, Material.DARK_OAK_PLANKS,
            Material.CRIMSON_PLANKS, Material.WARPED_PLANKS
        };
        
        for (int i = 0; i < logs.length; i++) {
            NamespacedKey key = new NamespacedKey(plugin, "planks_" + logs[i].name().toLowerCase());
            ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(planks[i], 2));
            recipe.addIngredient(logs[i]);
            Bukkit.addRecipe(recipe);
        }
    }
    
    /**
     * 注册烈焰粉配方
     */
    private void registerBlazePowderRecipe() {
        // 1个烈焰棒 → 1个烈焰粉
        NamespacedKey key = new NamespacedKey(plugin, "blaze_powder_from_rod");
        ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.BLAZE_POWDER, 1));
        recipe.addIngredient(Material.BLAZE_ROD);
        Bukkit.addRecipe(recipe);
    }
    
    /**
     * 注册金萝卜配方
     */
    private void registerGoldenCarrotRecipe() {
        // 金萝卜：需要金锭而非金粒
        NamespacedKey key = new NamespacedKey(plugin, "golden_carrot_ingot");
        ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.GOLDEN_CARROT));
        recipe.shape("GGG", "GCG", "GGG");
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('C', Material.CARROT);
        Bukkit.addRecipe(recipe);
    }
    
    /**
     * 注册闪烁西瓜配方
     */
    private void registerGlisteringMelonRecipe() {
        // 闪烁西瓜：需要金锭而非金粒
        NamespacedKey key = new NamespacedKey(plugin, "glistering_melon_ingot");
        ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.GLISTERING_MELON_SLICE));
        recipe.shape("GGG", "GMG", "GGG");
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('M', Material.MELON_SLICE);
        Bukkit.addRecipe(recipe);
    }
}
