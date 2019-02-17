package me.flail.sparkytools.Tools;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import me.flail.sparkytools.SparkyTools;
import me.flail.sparkytools.Utils;
import me.flail.sparkytools.Utils.ToolType;

public class ToolEditor implements Listener {

	protected SparkyTools plugin = SparkyTools.getPlugin(SparkyTools.class);

	private Utils utils = plugin.utils;

	@EventHandler
	public void toolEditor(AsyncPlayerChatEvent event) {

		if ((event.getPlayer() != null) && !event.isCancelled()) {

			Player player = event.getPlayer();

			if (plugin.toolSessions.get(player).equals(Boolean.TRUE)) {

				String message = event.getMessage().toLowerCase();

				plugin.scheduler.cancelTasks(plugin);

				if (message.equals("exit") || message.equals("leave") || message.equals("quit")
						|| message.equals("stop")) {
					plugin.toolSessions.replace(player, Boolean.FALSE);

					plugin.scheduler.cancelTasks(plugin);

					event.setCancelled(true);
					player.sendMessage(utils.chat("$prefix &eExited the Tool Editor."));

				} else {

					if (message.startsWith("$add ") || message.startsWith("add ")) {

						ToolType type = ToolType.analyzeType(message);

						bindTool(player, type, message, true);

						event.setCancelled(true);

					} else if (message.startsWith("$command ")) {

						bindTool(player, ToolType.COMMAND, message, false);

						event.setCancelled(true);

					} else if (message.startsWith("$worldedit ")) {

						bindTool(player, ToolType.WORLD_EDIT, message, false);

						event.setCancelled(true);

					}

				}

			}

		}

	}

	public boolean bindTool(Player player, ToolType type, String message, boolean append) {

		ItemStack item = player.getInventory().getItemInMainHand();

		return new SparkyTool(player, item).setCommand(message, append);

	}

	public void removeTool(Player operator) {
		ItemStack pItem = operator.getInventory().getItemInMainHand();
	}

}
