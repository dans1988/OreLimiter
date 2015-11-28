package pl.dans.plugins.ores.listeners;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import pl.dans.plugins.ores.OreLimiter;
import pl.dans.plugins.ores.generator.OreLimiterPopulator;

public class WorldInitListener implements Listener {
    
    private final OreLimiter oreLimiter;

    public WorldInitListener(OreLimiter halloweenSpecial) {
        this.oreLimiter = halloweenSpecial;
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onWorldInit(final WorldInitEvent event) {
        
        Bukkit.getLogger().log(Level.INFO, "World init detected! OreLimiter");
        
        if (oreLimiter.getRunning()) {
            event.getWorld().getPopulators().add(new OreLimiterPopulator(oreLimiter));
        }
        
    }
    
    
}
