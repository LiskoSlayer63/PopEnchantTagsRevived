package com.shadowhawk.popenchanttags;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ForgeModPopEnchantTagsRevived.MOD_ID, name = ForgeModPopEnchantTagsRevived.MOD_NAME, version = ForgeModPopEnchantTagsRevived.MOD_VERSION, acceptedMinecraftVersions = "[1.12.2]", clientSideOnly = true)
@Mod.EventBusSubscriber(modid = ForgeModPopEnchantTagsRevived.MOD_ID)
public class ForgeModPopEnchantTagsRevived
{
	@Mod.Instance(ForgeModPopEnchantTagsRevived.MOD_ID)
	public static ForgeModPopEnchantTagsRevived instance;
    public PopEnchantTagsRenderer renderer = new PopEnchantTagsRenderer();
	
	public static final String MOD_ID = "popenchanttagsrevived";
	public static final String MOD_NAME = "Pop Enchant Tags Revived";
	public static final String MOD_VERSION = "1.1.1";
	
	public static KeyBinding toggleTags;
	public static KeyBinding toggleBooks;
    
    public ForgeModPopEnchantTagsRevived()
    {
    	if (instance != null) {
		    throw new RuntimeException("Double instantiation of " + MOD_NAME);
		} else {
		    instance = this;
		}
    }
    
    @Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{
		Logger.init(event.getModLog());
		Logger.enableDebug(ConfigPopEnchantTags.DEBUG);
		Logger.debug("========= P R E  I N I T =========");

    	toggleTags = new KeyBinding("key.popenchanttagsrevived.toggle_tags", 0, MOD_NAME);
    	toggleBooks = new KeyBinding("key.popenchanttagsrevived.toggle_books", 0, MOD_NAME);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) 
	{
		Logger.debug("========= I N I T =========");
		
		ClientRegistry.registerKeyBinding(toggleTags);
		ClientRegistry.registerKeyBinding(toggleBooks);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		Logger.debug("========= P O S T  I N I T =========");
	}
}
