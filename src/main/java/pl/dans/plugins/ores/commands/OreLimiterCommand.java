package pl.dans.plugins.ores.commands;

import com.google.common.collect.Lists;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.dans.plugins.ores.OreLimiter;

public class OreLimiterCommand implements CommandExecutor {
    
    private final OreLimiter oreLimiter;

    public OreLimiterCommand(OreLimiter oreLimiter) {
        this.oreLimiter = oreLimiter;
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arg) {
        if (arg.length == 0) {
            return false;
        }
        
        String subcommand = arg[0];
        if (subcommand.compareToIgnoreCase("enable") == 0) {
            
            if (!oreLimiter.getRunning()) {
                oreLimiter.setRunning(true);
                Bukkit.broadcastMessage(oreLimiter.getMessageStart() + "Enabled.");
            } else {
                sender.sendMessage(ChatColor.RED + "OreLimiter is already activated.");
            }
        } else if (subcommand.compareToIgnoreCase("disable") == 0) {
            if (oreLimiter.getRunning()) {
                oreLimiter.setRunning(false);
                Bukkit.broadcastMessage(oreLimiter.getMessageStart() + "Disabled.");
            } else {
                sender.sendMessage(ChatColor.RED + "OreLimiter is not enabled.");
            }
        } else if (subcommand.compareToIgnoreCase("rates") == 0) {
            for (Material material : oreLimiter.getOreRates().keySet()) {
                sender.sendMessage(ChatColor.DARK_PURPLE + material.name() + ": " + ChatColor.YELLOW + oreLimiter.getOreRates().get(material) + "%");
            }
                
        } else if (subcommand.compareToIgnoreCase("replacements") == 0) {
            for (Material material : oreLimiter.getReplacements().keySet()) {
                sender.sendMessage(ChatColor.DARK_PURPLE + material.name() + ": " + ChatColor.YELLOW + oreLimiter.getReplacements().get(material).name());
            }
                
        } else if (subcommand.compareToIgnoreCase("setrate") == 0) {
            
            if (arg.length < 3) {
                sender.sendMessage(ChatColor.RED + "Example usage: /orelimiter setrate GOLD_ORE 75");
                return true;
            }
            
            Material material = Material.matchMaterial(arg[1]);
            List<Material> allowedMaterials = Lists.newArrayList(OreLimiter.SUPPORTED_ORES);
            if (material == null || !allowedMaterials.contains(material)) {
                
                String msg = ChatColor.RED + "Supported ores: ";
                for (Material allowedMaterial : allowedMaterials) {
                    msg += allowedMaterial.name() + ", ";
                }
                msg = msg.substring(0, msg.length() - 2);
                sender.sendMessage(msg);
                return true;
            }
            
            Integer rate;
            try {
                rate = Integer.parseInt(arg[2]);
            } catch (NumberFormatException ex) {
                sender.sendMessage(ChatColor.RED + "The rate argument must be an integer!");
                return true;
            }
    
            oreLimiter.setRate(material, rate);
            Bukkit.broadcastMessage(oreLimiter.getMessageStart() + material.name() + " rate set was set to " + rate + "%");
            
        }  else if (subcommand.compareToIgnoreCase("setreplacement") == 0) {
            
            if (arg.length < 3) {
                sender.sendMessage(ChatColor.RED + "Example usage: /orelimiter setreplacement GOLD_ORE STONE");
                return true;
            }
            
            Material material = Material.matchMaterial(arg[1]);
            List<Material> allowedMaterials = Lists.newArrayList(OreLimiter.SUPPORTED_ORES);
            if (material == null || !allowedMaterials.contains(material)) {
                
                String msg = ChatColor.RED + "Supported ores: ";
                for (Material allowedMaterial : allowedMaterials) {
                    msg += allowedMaterial.name() + ", ";
                }
                msg = msg.substring(0, msg.length() - 2);
                sender.sendMessage(msg);
                return true;
            }
            
            Material replacement = Material.matchMaterial(arg[2]);
            
            if (replacement == null) {
                sender.sendMessage(ChatColor.RED + "Wrong replacement type. Try STONE or GLASS.");
                return true;
            }
    
            oreLimiter.setReplacement(material, replacement);
            Bukkit.broadcastMessage(oreLimiter.getMessageStart() + "Replacement of " + material.name() + " was set to " + replacement.name());
            
        } 
        
        
        return true;
    }
    
}
