package me.flail.sparkytools.Listeners;

import me.flail.sparkytools.Tools.SparkyTool;

public class ToolUseListener {

	private SparkyTool tool = null;

	public ToolUseListener(SparkyTool tool) {
		this.tool = tool;
	}

	public boolean runToolCommand() {

		boolean yup = false;

		for (String cmd : tool.getCommands()) {
			tool.player().chat(cmd);
			yup = true;
		}

		return yup;
	}

}
