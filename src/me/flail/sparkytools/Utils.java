package me.flail.sparkytools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class Utils {

	private SparkyTools plugin;

	public Utils(SparkyTools plugin) {
		this.plugin = plugin;
	}

	/**
	 *
	 * @param s
	 * @return translated ChatColor form of the input 's'
	 */
	public String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&',
				s.replace("$prefix", "&7[&e&lSparkyTools&7]").replace("$version", plugin.version));
	}

	/**
	 *
	 * @author FlailoftheLord
	 *
	 */
	public enum ToolType {

		COMMAND, WORLD_EDIT, ATTRIBUTE, TEXT;

		public final static String toString(ToolType type) {
			switch (type) {

			case COMMAND:
				return "$Command ";
			case WORLD_EDIT:
				return "$WorldEdit ";
			case ATTRIBUTE:
				return "$Attribute ";
			case TEXT:
				return "$Text ";
			default:
				return "$Command ";

			}
		}

		public final static ToolType getFrom(String string) {

			switch (string.toLowerCase()) {

			case "command":
				return ToolType.COMMAND;
			case "cmd":
				return ToolType.COMMAND;
			case "we":
				return ToolType.WORLD_EDIT;
			case "worldedit":
				return ToolType.WORLD_EDIT;
			case "attrib":
				return ToolType.ATTRIBUTE;
			case "attribute":
				return ToolType.ATTRIBUTE;
			case "message":
				return ToolType.TEXT;
			case "text":
				return ToolType.TEXT;
			case "txt":
				return ToolType.TEXT;
			case "msg":
				return ToolType.TEXT;
			default:
				return ToolType.COMMAND;

			}

		}

		public final static ToolType analyzeType(String string) {

			string.toLowerCase();

			if (string.startsWith("$add ") || string.startsWith("$set ") || string.startsWith("/")) {

				String newString = string.replace("$add ", "").replace("$set ", "");
				if (newString.startsWith("//")) {
					return ToolType.WORLD_EDIT;
				} else {
					return ToolType.COMMAND;
				}

			} else {
				return ToolType.COMMAND;
			}

		}

	}

	public String numeral(int number) {

		switch (number) {

		case 1:
			return "I";
		case 2:
			return "II";
		case 3:
			return "III";
		case 4:
			return "IV";
		case 5:
			return "V";
		case 6:
			return "VI";
		case 7:
			return "VII";
		case 8:
			return "VIII";
		case 9:
			return "IX";
		case 10:
			return "X";
		case 11:
			return "XI";
		case 12:
			return "XII";
		case 13:
			return "XIII";
		case 14:
			return "XIV";
		case 15:
			return "XV";
		case 16:
			return "XVI";
		case 17:
			return "XVII";
		case 18:
			return "XVIII";
		case 19:
			return "XIX";
		case 20:
			return "XX";
		default:
			return "level." + number;

		}

	}

	public String colorAsText(String color) {

		switch (color.trim().toLowerCase()) {

		case "$0":
			return chat("&0Black");
		case "$1":
			return chat("&1Royal Blue");
		case "$2":
			return chat("&2Green");
		case "$3":
			return chat("&3Cyan");
		case "$4":
			return chat("&4Crimson");
		case "$5":
			return chat("&5Purple");
		case "$6":
			return chat("&6Gold");
		case "$7":
			return chat("&7Light Gray");
		case "$8":
			return chat("&8Gray");
		case "$9":
			return chat("&9Blue");
		case "$a":
			return chat("&aLime");
		case "$b":
			return chat("&bAqua");
		case "$c":
			return chat("&cRed");
		case "$d":
			return chat("&dPink");
		case "$e":
			return chat("&eYellow");
		case "$f":
			return chat("&fWhite");
		case "$k":
			return chat("&kMagic&r&fMagic");
		case "$l":
			return chat("&f&lBold");
		case "$o":
			return chat("&f&oItalic");
		case "$n":
			return chat("&f&nUnderline");
		case "$m":
			return chat("&f&mStrikethrough");
		default:
			return chat("&rDefault");

		}

	}

	public List<String> addEnchant(List<String> lore, ToolType type) {

		List<String> newLore = new ArrayList<>();

		int size = lore.size();

		if ((size > 0) && (size <= 20)) {

			switch (type) {

			case COMMAND:
				lore = this.removeType(lore, "Generic Command");
				newLore.add(this.chat("&7Generic Command " + this.numeral(size)));

				break;
			case WORLD_EDIT:
				lore = removeType(lore, "WorldEdit Command");
				newLore.add(chat("&7WorldEdit Command " + numeral(size)));

				break;
			case ATTRIBUTE:
				lore = removeType(lore, "Player Attribute");
				newLore.add(chat("&7Player Attribute " + numeral(size)));

				break;
			case TEXT:
				lore = removeType(lore, "Plain Text");
				newLore.add(chat("&7Plain Text " + numeral(size)));

				break;
			default:
				lore = removeType(lore, "Command");
				newLore.add(chat("&7Command " + numeral(size)));

			}

		} else {

			switch (type) {

			case COMMAND:
				lore = removeType(lore, "Generic Command");
				lore = removeType(lore, "enchant.G");
				newLore.add(chat("&7enchant.GenericCommand." + numeral(size)));
				break;
			case WORLD_EDIT:
				lore = removeType(lore, "WorldEdit Command");
				lore = removeType(lore, "enchant.W");
				newLore.add(chat("&7enchant.WorldEditCommand." + numeral(size)));
				break;
			case ATTRIBUTE:
				lore = removeType(lore, "Player Attribute");
				lore = removeType(lore, "enchant.Player");
				newLore.add(chat("&7enchant.PlayerAttribute." + numeral(size)));
				break;
			case TEXT:
				lore = removeType(lore, "Plain Text");
				lore = removeType(lore, "enchant.P");
				newLore.add(chat("&7enchant.PlainText." + numeral(size)));
				break;
			default:
				lore = removeType(lore, "Command");
				lore = removeType(lore, "enchant.C");
				newLore.add(chat("&7enchant.Command." + numeral(size)));

			}

		}

		newLore.add("");

		for (String l : lore) {
			if ((l != " ") || !l.isEmpty()) {
				newLore.add(l);
			}
		}

		return newLore;

	}

	private List<String> removeType(List<String> lore, String type) {

		List<String> newLore = new ArrayList<>();

		if (lore != null) {

			for (String l : lore) {

				String line = ChatColor.stripColor(l.toLowerCase());

				if (!line.startsWith(type.toLowerCase())) {

					if (!line.isEmpty() && !line.trim().equals("") && !line.trim().equals(" ")) {
						newLore.add(l);
					}

				}

			}

		}

		return newLore;

	}

}
