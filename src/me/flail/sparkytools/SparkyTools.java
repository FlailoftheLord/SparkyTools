package me.flail.sparkytools;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import me.flail.sparkytools.Listeners.InteractEventWatcher;
import me.flail.sparkytools.Tools.ToolEditor;

public class SparkyTools extends JavaPlugin implements Listener {

	public ConsoleCommandSender console = Bukkit.getConsoleSender();

	public Utils utils = new Utils(this);

	public Server server = this.getServer();

	public BukkitScheduler scheduler = server.getScheduler();

	public PluginManager pluginManager = server.getPluginManager();

	public String version = getDescription().getVersion();

	public Map<Player, Boolean> toolSessions = new HashMap<>();

	@Override
	public void onEnable() {

		saveDefaultConfig();

		for (Player p : Bukkit.getOnlinePlayers()) {
			toolSessions.put(p, Boolean.FALSE);
		}

		registerCommands();
		registerEvents();

		console.sendMessage(utils.chat("&6Sparky&eTools &7v" + version));

	}

	@Override
	public void onDisable() {
		try {
			server.wait(3000);
			scheduler.getPendingTasks().clear();
			scheduler.cancelTasks(this);
			server.notifyAll();
		} catch (Throwable t) {
			console.sendMessage(utils.chat("$prefix &cThread interrupted, canceling tasks immediately..."));
			scheduler.cancelTasks(this);
		}

		console.sendMessage(utils.chat("$prefix &aSuccessfully shut down SparkyTools, have a good day!"));

	}

	final void registerCommands() {
		this.getCommand("sparkytools").setExecutor(new ToolCommand());
	}

	final void registerEvents() {
		pluginManager.registerEvents(this, this);
		pluginManager.registerEvents(new ToolEditor(), this);
		pluginManager.registerEvents(new InteractEventWatcher(), this);
	}

	@EventHandler
	public void playerJoin(PlayerJoinEvent event) {
		toolSessions.put(event.getPlayer(), Boolean.FALSE);
	}

	@EventHandler
	public void playerQuit(PlayerQuitEvent event) {
		toolSessions.remove(event.getPlayer());
	}

	@EventHandler
	public void toolEditSession(PlayerCommandPreprocessEvent event) {

		Player operator = event.getPlayer();

		String message = event.getMessage().toLowerCase();

		if (message.startsWith("/tool") || message.startsWith("/st")) {
			event.setMessage(message.replaceAll("/tool", "/sparkytools").replaceAll("/st", "/sparkytools"));

		}

		if (toolSessions.get(operator).equals(Boolean.TRUE)) {

			if (message.startsWith("//")) {
				message = message.replace("//", "$WorldEdit //");
			} else {
				message = message.replace("/", "$Command /");
			}

			operator.chat(message);

			event.setCancelled(true);

		}

	}

}
