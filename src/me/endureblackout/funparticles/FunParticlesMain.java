package me.endureblackout.funparticles;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.endureblackout.funparticles.utils.ParticleEffect;

public class FunParticlesMain extends JavaPlugin {
	
	public Map<UUID, ParticleEffect> players = new HashMap<UUID, ParticleEffect>();
	
	@SuppressWarnings("deprecation")
	public void onEnable() {
		getCommand("fp").setExecutor(new MenuHandler(this));
		
		Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new BukkitRunnable() {
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					for(Entry<UUID, ParticleEffect> k : players.entrySet()) {
						if(k.getKey().equals(p.getUniqueId())) {
							ParticleEffect effect = k.getValue();
							effect.display(0f, 0f, 0f, 0.01f, 15, p.getLocation(), 1.0);
						}
					}
				}
			}			
		}, 0, 2);
	}
}
