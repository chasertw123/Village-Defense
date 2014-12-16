package me.chasertw123.villagedefense.game.tools;

public class ToolSet {

    private ToolType toolType;
    private int maxTier;
    private Tool[] tools;

    public ToolSet(ToolType toolType, Tool... tools) {
        this.toolType = toolType;
        this.tools = tools;
        this.maxTier = tools.length;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public int getMaxTier() {
        return maxTier;
    }

    public Tool getTool(int tier) {
        return tools[--tier];
    }

}
