package me.flail.sparkytools;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToolCommand implements CommandExecutor {

	protected SparkyTools plugin = SparkyTools.getPlugin(SparkyTools.class);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		String cmd = label.toLowerCase();

		switch (cmd) {

		case "sparkytools":

			if (args.length == 0) {

				sender.sendMessage(
						plugin.utils.chat("$prefix &7running version " + plugin.version + "&2 by FlailoftheLord."));

				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (player.hasPermission("sparkytools.use")) {
						sender.sendMessage(plugin.utils.chat("&dto get started type &7/$command create/bind")
								.replace("$command", cmd));
					}
				}
				return true;
			}

			else if (args.length == 1) {

				String arg = args[0].toLowerCase();

				if (sender instanceof Player) {

					Player operator = (Player) sender;

					switch (arg) {

					case "bind":
						openEditorSession(operator);
						break;

					case "create":
						openEditorSession(operator);
						break;

					case "add":
						openEditorSession(operator);
						break;

					case "none":

					case "unbind":

					case "delete":

					case "remove":

					case "editor":
						openEditorSession(operator);
						break;

					default:
						return true;

					}

				}

			}

			return true;

		default:
			return true;

		}

	}

	public int[] openEditorSession(Player operator) {

		operator.sendMessage(plugin.utils.chat("$prefix &7creating editor session..."));

		plugin.toolSessions.replace(operator, Boolean.TRUE);
		int delay = 60;

		int msg1 = plugin.scheduler.scheduleSyncDelayedTask(plugin, () -> {
			operator.sendMessage(plugin.utils.chat("$prefix &aOpened Tool Editor, to exit just say &e&oExit"));
		}, delay);
		int msg2 = plugin.scheduler.scheduleSyncDelayedTask(plugin, () -> {
			operator.sendMessage(
					plugin.utils.chat("&7To start out, make sure you're holding an &a&oitem &7not a block..."));
		}, delay + 50);
		int msg3 = plugin.scheduler.scheduleSyncDelayedTask(plugin, () -> {
			operator.sendMessage(plugin.utils
					.chat("&7then type the command you want to assign to the item. Be sure to include the &8/"));
		}, delay * 4);
		int msg4 = plugin.scheduler.scheduleSyncDelayedTask(plugin, () -> {
			operator.sendMessage(plugin.utils.chat(
					"&7Just follow the instructions! &aAnd remember, to exit the editor, either leave the server, or type &e&oExit&7."));
		}, delay * 7);

		int[] taskIds = { msg1, msg2, msg3, msg4 };
		return taskIds;

	}

}
