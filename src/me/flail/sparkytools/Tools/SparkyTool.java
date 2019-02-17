package me.flail.sparkytools.Tools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.sparkytools.SparkyTools;
import me.flail.sparkytools.Utils;
import me.flail.sparkytools.Utils.ToolType;

public class SparkyTool extends ItemStack {

	private Player owner;
	private ItemStack item;

	private SparkyTools plugin = JavaPlugin.getPlugin(SparkyTools.class);

	Utils utils = new Utils(plugin);

	public SparkyTool(Player owner, ItemStack item) {
		this.owner = owner;
		this.item = item;
	}

	public boolean setCommand(String message, boolean append) {

		ToolType type = ToolType.analyzeType(message);

		ItemStack pItem = item;

		String toolTypeName = ToolType.toString(type);

		if (!pItem.getType().isBlock()) {

			ItemStack newItem = new ItemStack(pItem.getType());
			ItemMeta newItemMeta = newItem.getItemMeta();

			if (append) {
				if (pItem.hasItemMeta()) {
					newItemMeta = pItem.getItemMeta();

					message = message.replace("$add ", "");

					if (message.startsWith("add ")) {
						message = message.replaceFirst("add ", "");
					}

				}

			}

			List<String> newLore = new ArrayList<>();

			if (newItemMeta.hasLore()) {
				newLore = newItemMeta.getLore();
			}

			newLore.add(message.toLowerCase().replace(toolTypeName.toLowerCase(), ""));

			owner.sendMessage(utils.chat("$prefix &7Bound " + toolTypeName.replace("$", "") + "tool: &8"
					+ message.toLowerCase().replace(toolTypeName.toLowerCase(), "") + " &7to "
					+ pItem.getType().toString()));

			newLore = utils.addEnchant(newLore, type);

			newItemMeta.setDisplayName(utils.chat("&eSparkyTools &fTool." + toolTypeName.replace("$", "")));
			newItemMeta.setLore(newLore);

			newItemMeta.setUnbreakable(true);

			newItemMeta.addEnchant(Enchantment.VANISHING_CURSE, 5, true);
			newItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			newItemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

			newItem.setItemMeta(newItemMeta);

			item = newItem;

			owner.getInventory().setItemInMainHand(newItem);

			return true;
		} else {
			owner.sendMessage(utils.chat("&7You can't bind to a block, or any item that can be placed."));

		}

		return false;
	}

	public boolean removeCommand(String command) {

		if (command.isEmpty()) {
			return false;
		}

		ItemMeta meta = item.getItemMeta();

		return true;
	}

}
