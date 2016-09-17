package me.endureblackout.funparticles;

import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.endureblackout.funparticles.utils.IconMenu;
import me.endureblackout.funparticles.utils.IconMenu.OptionClickEvent;
import me.endureblackout.funparticles.utils.ParticleEffect;

public class MenuHandler implements CommandExecutor, Listener {
	
	IconMenu menu;
	
	ItemStack redStone = new ItemStack(Material.REDSTONE);
	ItemMeta redMeta = redStone.getItemMeta();
	
	ItemStack redWool = new ItemStack(Material.WOOL, 1, (byte) 14);
	ItemMeta woolMeta = redWool.getItemMeta();
	
	ItemStack flame = new ItemStack(Material.BLAZE_POWDER);
	ItemMeta flameMeta = flame.getItemMeta();
	
	ItemStack none = new ItemStack(Material.REDSTONE_BLOCK);
	ItemMeta noneMeta = none.getItemMeta();
	
	FunParticlesMain plugin;
	
	public MenuHandler(FunParticlesMain instance) {
		this.plugin = instance;
		
		redMeta.setDisplayName(ChatColor.RED + "RedStone Particle");
		redStone.setItemMeta(redMeta);
		
		woolMeta.setDisplayName(ChatColor.DARK_RED + "Hearts Particle");
		redWool.setItemMeta(woolMeta);
		
		flameMeta.setDisplayName(ChatColor.YELLOW + "Flame Particle");
		flame.setItemMeta(flameMeta);
		
		noneMeta.setDisplayName(ChatColor.RED + "No particle");
		none.setItemMeta(noneMeta);
		
		menu = new IconMenu(ChatColor.RED + "Particles Menu", 54, this::itemClick, instance);
		
		menu.setOption(19, redStone);
		menu.setOption(22, redWool);
		menu.setOption(25, flame);
		menu.setOption(40, none);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("fp") && p.hasPermission("fp.use") || 
					cmd.getName().equalsIgnoreCase("funparticles") && p.hasPermission("fp.use")) {
				
				menu.open(p);
			}
		}
		
		return true;
	}
	
	public void itemClick(OptionClickEvent e) {
		Player p = e.getPlayer();
		e.willClose();
		
		if(e.getItem() == redStone) {
			plugin.players.put(p.getUniqueId(), ParticleEffect.REDSTONE);
			p.sendMessage(ChatColor.GREEN + "You have choosen " + ParticleEffect.REDSTONE.name());
		}
		
		if(e.getItem() == redWool) {
			plugin.players.put(p.getUniqueId(), ParticleEffect.HEART);
			p.sendMessage(ChatColor.GREEN + "You have choosen " + ParticleEffect.HEART.name());
		}
		
		if(e.getItem() == flame) {
			plugin.players.put(p.getUniqueId(), ParticleEffect.FLAME);
			p.sendMessage(ChatColor.GREEN + "You have choosen " + ParticleEffect.FLAME.name());
		}
		
		if(e.getItem() == none) {
			for(Entry<UUID, ParticleEffect> k : plugin.players.entrySet()) {
				if(p.getUniqueId().equals(k.getKey())) {
					plugin.players.remove(p.getUniqueId());
					p.sendMessage(ChatColor.GREEN + "Particles turned off");
				}
			}
		}
	}
}
