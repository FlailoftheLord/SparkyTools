package me.flail.sparkytools.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.sparkytools.SparkyTools;
import me.flail.sparkytools.Tools.SparkyTool;

public class InteractEventWatcher implements Listener {

	private SparkyTools plugin = JavaPlugin.getPlugin(SparkyTools.class);

	@EventHandler(priority = EventPriority.MONITOR)
	public void playerUseItem(PlayerInteractEvent event) {

		Action action = event.getAction();

		if (plugin.toolSessions.get(event.getPlayer()) != Boolean.TRUE) {

			if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
				if ((event.getItem() != null) && (event.getItem().getType() != Material.AIR)) {

					new ToolUseListener(new SparkyTool(event.getPlayer(), event.getItem())).runToolCommand();

				}

			}

		}

	}

}
