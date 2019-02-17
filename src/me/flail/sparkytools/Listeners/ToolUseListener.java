package me.flail.sparkytools.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ToolUseListener {

	Player player;
	ItemStack item;

	public ToolUseListener(Player player, ItemStack item) {
		this.player = player;
		this.item = item;
	}

	public boolean runToolCommand() {

		return true;
	}

}
