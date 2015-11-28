package pl.dans.plugins.ores;

import com.google.common.collect.Lists;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class OreTraverser {

    private final List<OreLocation> vein;

    public OreTraverser() {
        this.vein = Lists.newArrayList();
    }

    public void traverseVein(OreLocation ore, Material material, World world, boolean replace, Material replacement) {
        
        Block block = world.getBlockAt(ore.getX(), ore.getY(), ore.getZ());

        if (material.equals(block.getType()) && !vein.contains(ore)) {
            vein.add(ore);
            if (replace) {
                block.setType(replacement);
            }

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        OreLocation newLocation = new OreLocation(ore.getX() + x, ore.getY() + y, ore.getZ() + z);

                        traverseVein(newLocation, material, world, replace, replacement);

                    }
                }
            }
        }

    }

    public List<OreLocation> getVein() {
        return vein;
    }

}
