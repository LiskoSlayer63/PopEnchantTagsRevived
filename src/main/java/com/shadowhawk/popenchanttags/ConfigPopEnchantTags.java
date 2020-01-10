package com.shadowhawk.popenchanttags;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

@Config(modid = ForgeModPopEnchantTagsRevived.MOD_ID)
public class ConfigPopEnchantTags
{
	@LangKey("gui.popenchanttagsrevived.config.enabled")
	public static boolean enabled = true;
	
	@LangKey("gui.popenchanttagsrevived.config.show_books")
	public static boolean showBooks = true;

	
	@Comment({
	  "Enable debugging mode.",
	  "(for development use only)"
	})
	@Name("Enable Debug")
	@RequiresMcRestart
	public static boolean DEBUG = false;
	
	
	public static void sync()
	{
		ConfigManager.sync(ForgeModPopEnchantTagsRevived.MOD_ID, Config.Type.INSTANCE);
		
		Logger.debug("Configuration changed!");
	}
}