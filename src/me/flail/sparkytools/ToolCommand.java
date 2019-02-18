package me.flail.sparkytools;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.flail.sparkytools.Utils.ToolType;
import me.flail.sparkytools.Tools.ToolEditor;

public class ToolCommand implements CommandExecutor {

	protected SparkyTools plugin = SparkyTools.getPlugin(SparkyTools.class);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		String cmd = label.toLowerCase();

		String usageGuide = plugin.utils.chat(
				"&eTo get started type &7/$command <editor> &eWhich will open an editor where you can edit the tools. Or use the manual command: &7/$command <set:add:remove> <command-to-remove>")
				.replace("$command", cmd);

		switch (cmd) {

		case "sparkytools":
			command.setPermissionMessage(plugin.utils.chat("$prefix &cYou can't use this command!"));

			if (args.length == 0) {

				sender.sendMessage(
						plugin.utils.chat("$prefix &7running version " + plugin.version + "&2 by FlailoftheLord."));

				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (player.hasPermission("sparkytools.use")) {
						sender.sendMessage(usageGuide);
					}
				}
				return true;
			}

			else if (args.length == 1) {

				String arg = args[0].toLowerCase();

				if (sender instanceof Player) {

					Player operator = (Player) sender;

					switch (arg) {

					case "editor":
						openEditorSession(operator);
						break;

					case "edit":
						openEditorSession(operator);
						break;

					default:
						operator.sendMessage(usageGuide);
						break;

					}

				} else {
					plugin.console.sendMessage(
							plugin.utils.chat("&cYou can't use SparkyTool editor commands in the console!"));
				}

			} else if (args.length >= 2) {
				if (sender instanceof Player) {

					ToolEditor editor = new ToolEditor();

					String commandToEdit = "";

					for (int index = 1; index < args.length; index++) {
						commandToEdit = commandToEdit.concat(args[index] + " ");
					}

					switch (args[0].toLowerCase()) {

					case "add":

						return editor.bindTool((Player) sender, ToolType.analyzeType(commandToEdit), commandToEdit,
								true);
					case "remove":

						return editor.removeTool((Player) sender, commandToEdit);
					case "delete":

						return editor.removeTool((Player) sender, commandToEdit);
					case "set":

						return editor.bindTool((Player) sender, ToolType.analyzeType(commandToEdit), commandToEdit,
								false);
					}

				} else {
					plugin.console.sendMessage(
							plugin.utils.chat("&cYou can't use SparkyTool editor commands in the console!"));
				}

				return true;
			}

			return true;

		default:
			return true;

		}

	}

	public int[] openEditorSession(Player operator) {

		operator.sendMessage(plugin.utils.chat("$prefix &7creating editor session..."));

		plugin.toolSessions.put(operator, Boolean.TRUE);
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
