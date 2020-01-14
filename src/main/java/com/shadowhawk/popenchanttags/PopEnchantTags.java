package com.shadowhawk.popenchanttags;

import org.apache.logging.log4j.LogManager;
import org.lwjgl.glfw.GLFW;

import com.shadowhawk.popenchanttags.config.ConfigHelper;
import com.shadowhawk.popenchanttags.config.PopEnchantTagsConfig;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PopEnchantTags.MOD_ID)
public class PopEnchantTags
{
	public static PopEnchantTags instance;
	public PopEnchantTagsRenderer renderer = new PopEnchantTagsRenderer();
	
	public static final String MOD_ID = "popenchanttags";
	public static final String MOD_NAME = "Pop Enchant Tags Revived";
	public static final String MOD_VERSION = "1.2.0";
	
	public static KeyBinding toggleTags;
	public static KeyBinding toggleBooks;
	
	public PopEnchantTags()
	{
		if (instance != null)
			throw new RuntimeException("Double instantiation of " + MOD_NAME);
		instance = this;
			
		Logger.init(LogManager.getLogger(MOD_ID));
		Logger.enableDebug(PopEnchantTagsConfig.DEBUG);
	    
	    ModLoadingContext.get().registerConfig(Type.COMMON, ConfigHelper.SPEC);
	    
	    DistExecutor.runWhenOn(Dist.CLIENT, ()->()-> {
	    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
	    });
	}
	
	public void init(FMLClientSetupEvent event) 
	{
		Logger.debug("========= I N I T =========");

		toggleTags = new KeyBinding(I18n.format(MOD_ID + ".key.toggle_tags"), GLFW.GLFW_KEY_UNKNOWN, MOD_NAME);
		toggleBooks = new KeyBinding(I18n.format(MOD_ID + ".key.toggle_books"), GLFW.GLFW_KEY_UNKNOWN, MOD_NAME);

		ClientRegistry.registerKeyBinding(toggleTags);
		ClientRegistry.registerKeyBinding(toggleBooks);
	}
}
