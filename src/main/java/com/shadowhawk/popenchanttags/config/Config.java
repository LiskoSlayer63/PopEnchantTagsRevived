package com.shadowhawk.popenchanttags.config;

import com.shadowhawk.popenchanttags.utils.Logger;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import static com.shadowhawk.popenchanttags.PopEnchantTags.MOD_ID;

/**
 * For configuration settings that change the behaviour of code on the LOGICAL CLIENT.
 * This can be moved to an inner class of ExampleModConfig, but is separate because of personal preference and to keep the code organised
 *
 * @author Cadiboo
 */
final class Config 
{
	final protected ForgeConfigSpec.BooleanValue enabled;
    final protected ForgeConfigSpec.BooleanValue showBooks;

	Config(final ForgeConfigSpec.Builder builder)
	{
		builder.push("general");
		
		enabled = builder
				.comment("Enables the Pop Enchant Tags")
        		.translation(MOD_ID + ".config.enabled")
        		.define("enabled", true);
        showBooks = builder
				.comment("Show tags also on books")
        		.translation(MOD_ID + ".config.show_books")
        		.define("showBooks", true);
        
		builder.pop();
	}
	
	protected void save(ModConfig config)
	{
		if (config == null) return;

		config.getConfigData().set("general.enabled", PopEnchantTagsConfig.enabled);
		config.getConfigData().set("general.showBooks", PopEnchantTagsConfig.showBooks);
    	
		config.save();
		
		Logger.debug("Configuration saved!");
	}
	
	protected void load()
	{
		PopEnchantTagsConfig.enabled = enabled.get();
		PopEnchantTagsConfig.showBooks = showBooks.get();

		Logger.debug("Configuration loaded!");
	}
}
