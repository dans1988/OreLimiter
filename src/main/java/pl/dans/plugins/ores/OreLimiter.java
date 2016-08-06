package pl.dans.plugins.ores;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import pl.dans.plugins.ores.commands.OreLimiterCommand;
import pl.dans.plugins.ores.listeners.WorldInitListener;

public class OreLimiter extends JavaPlugin {

    public static final Material[] SUPPORTED_ORES = {
        Material.GOLD_ORE,
        Material.DIAMOND_ORE,
        Material.IRON_ORE,
        Material.LAPIS_ORE,
        Material.COAL_ORE,
        Material.REDSTONE_ORE,
        Material.QUARTZ_ORE
    };

    private final Map<Material, Integer> oreRates;
    private final Map<Material, Material> replacements;

    private Boolean running;
    private OreLimiterMode oreLimiterMode;

    public OreLimiter() {
        this.oreRates = Maps.newHashMap();
        this.replacements = Maps.newHashMap();
    }

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "{0}onEnable", ChatColor.RED);

        saveDefaultConfig();

        FileConfiguration configuration = getConfig();

        getServer().getPluginManager().registerEvents(new WorldInitListener(this), this);
        getCommand("orelimiter").setExecutor(new OreLimiterCommand(this));

        oreRates.put(Material.GOLD_ORE, configuration.getInt("goldOre.rate"));
        oreRates.put(Material.DIAMOND_ORE, configuration.getInt("diamondOre.rate"));
        oreRates.put(Material.IRON_ORE, configuration.getInt("ironOre.rate"));
        oreRates.put(Material.LAPIS_ORE, configuration.getInt("lapisOre.rate"));
        oreRates.put(Material.COAL_ORE, configuration.getInt("coalOre.rate"));
        oreRates.put(Material.REDSTONE_ORE, configuration.getInt("redstoneOre.rate"));
        oreRates.put(Material.QUARTZ_ORE, configuration.getInt("quartzOre.rate"));

        for (Material material : oreRates.keySet()) {
            if (oreRates.get(material) < 0) {
                oreRates.put(material, 0);
            } else if (oreRates.get(material) > 100) {
                oreRates.put(material, 100);
            }
        }

        replacements.put(Material.GOLD_ORE, Material.matchMaterial(configuration.getString("goldOre.replacement")));
        replacements.put(Material.DIAMOND_ORE, Material.matchMaterial(configuration.getString("diamondOre.replacement")));
        replacements.put(Material.IRON_ORE, Material.matchMaterial(configuration.getString("ironOre.replacement")));
        replacements.put(Material.LAPIS_ORE, Material.matchMaterial(configuration.getString("lapisOre.replacement")));
        replacements.put(Material.COAL_ORE, Material.matchMaterial(configuration.getString("coalOre.replacement")));
        replacements.put(Material.REDSTONE_ORE, Material.matchMaterial(configuration.getString("redstoneOre.replacement")));
        replacements.put(Material.QUARTZ_ORE, Material.matchMaterial(configuration.getString("quartzOre.replacement")));

        for (Material material : replacements.keySet()) {
            if (replacements.get(material) == null) {
                replacements.put(material, Material.STONE);
            }
        }
        
        

        running = configuration.getBoolean("enabled");
        
        oreLimiterMode = OreLimiterMode.valueOf(configuration.getString("mode"));

    }

    @Override
    public void onDisable() {
        super.onDisable(); //To change body of generated methods, choose Tools | Templates.
    }

    public Map<Material, Integer> getOreRates() {
        return oreRates;
    }

    public Map<Material, Material> getReplacements() {
        return replacements;
    }

    public String getMessageStart() {
        return ChatColor.RED + "[" + ChatColor.LIGHT_PURPLE + "OreLimiter"
                + ChatColor.RED + "] " + ChatColor.YELLOW;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public void setRate(Material material, Integer rate) {
        oreRates.put(material, rate);
    }
    
    public void setReplacement(Material material, Material replacement) {
        replacements.put(material, replacement);
    }

    public OreLimiterMode getOreLimiterMode() {
        return oreLimiterMode;
    }

    public void setOreLimiterMode(OreLimiterMode oreLimiterMode) {
        this.oreLimiterMode = oreLimiterMode;
    }


    
}
