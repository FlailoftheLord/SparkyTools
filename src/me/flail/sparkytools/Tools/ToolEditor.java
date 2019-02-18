package me.flail.sparkytools.Tools;

import org.bukkit.Material;
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
						|| message.equals("stop") || message.startsWith("$command /exit")) {
					plugin.toolSessions.put(player, Boolean.FALSE);

					plugin.scheduler.cancelTasks(plugin);

					event.setCancelled(true);
					player.sendMessage(utils.chat("$prefix &eExited the Tool Editor."));

				} else {

					if (message.startsWith("$add ") || message.startsWith("add ")) {

						ToolType type = ToolType.analyzeType(message);

						bindTool(player, type, message, true);

						event.setCancelled(true);
					} else if (message.startsWith("$remove ") || message.startsWith("remove ")
							|| message.startsWith("delete ") || message.startsWith("del ")
							|| message.startsWith("$delete ")) {

						removeTool(player, message);
						event.setCancelled(true);
					} else if (message.startsWith("$command ")) {
						bindTool(player, ToolType.COMMAND, message, false);

						event.setCancelled(true);

					} else if (message.startsWith("$worldedit ")) {

						bindTool(player, ToolType.WORLD_EDIT, message = message.replace("$worldedit ", ""), false);

						event.setCancelled(true);
					}

				}

			}

		}

	}

	public boolean bindTool(Player player, ToolType type, String message, boolean append) {

		ItemStack item = player.getInventory().getItemInMainHand();
		if ((item != null) && (item.getType() != Material.AIR) && !item.getType().isBlock()) {
			return new SparkyTool(player, item).setCommand(message, append);
		} else {
			player.sendMessage(utils.chat(
					"$prefix &7Invalid item in hand. Hold an item, not a block &8(placeable item) &7and try again."));
			return false;
		}

	}

	public boolean removeTool(Player operator, String message) {
		ItemStack item = operator.getInventory().getItemInMainHand();

		if ((item != null) && (item.getType() != Material.AIR) && !item.getType().isBlock()) {
			message = message.replaceFirst("delete ", "").replace("$delete ", "").replace("$remove ", "")
					.replaceFirst("remove ", "").replaceFirst("del ", "");

			return new SparkyTool(operator, item).removeCommand(message);
		} else {
			operator.sendMessage(utils.chat(
					"$prefix &7Invalid item in hand. Hold an item, not a block &8(placeable item) &7and try again."));
			return false;
		}

	}

}
