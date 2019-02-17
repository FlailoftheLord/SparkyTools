package me.flail.sparkytools.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractEventWatcher implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void playerUseItem(PlayerInteractEvent event) {

		Action action = event.getAction();

		if (action.equals(Action.RIGHT_CLICK_AIR) | action.equals(Action.RIGHT_CLICK_AIR)) {
			if ((event.getItem() != null) && (event.getItem().getType() != Material.AIR)) {

				new ToolUseListener(event.getPlayer(), event.getItem()).runToolCommand();

			}

		}

	}

}
