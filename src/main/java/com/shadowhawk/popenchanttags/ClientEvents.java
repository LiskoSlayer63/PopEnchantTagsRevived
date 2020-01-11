package com.shadowhawk.popenchanttags;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = PopEnchantTagsRevived.MOD_ID)
public class ClientEvents 
{
	@SubscribeEvent
	public static void onConfigChanged(final OnConfigChangedEvent event) 
	{
		if (event.getModID().equals(PopEnchantTagsRevived.MOD_ID))
			PopEnchantTagsConfig.sync();
	}
	
	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) 
	{
		if (event.isCanceled() || !Minecraft.isGuiEnabled() || !event.getType().equals(ElementType.ALL)) return;

		PopEnchantTagsRevived instance = PopEnchantTagsRevived.instance;
		ScaledResolution resolution = event.getResolution();

		instance.renderer.render(resolution.getScaledWidth(), resolution.getScaledHeight());
	}
	
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) 
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		
		if (event.isCanceled() || minecraft.isGamePaused()) return;

		PopEnchantTagsRevived instance = PopEnchantTagsRevived.instance;

		instance.renderer.tick(minecraft);
	}
	
	@SubscribeEvent
	public static void onKeyInput(KeyInputEvent event)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		
		if (event.isCanceled() || minecraft.isGamePaused() || !Minecraft.isGuiEnabled() || !minecraft.inGameHasFocus) return;
		
		boolean sync = false;
		
		if (PopEnchantTagsRevived.toggleTags.isPressed())
		{
			PopEnchantTagsConfig.enabled = !PopEnchantTagsConfig.enabled;
			sync = true;
		}
		
		if(PopEnchantTagsRevived.toggleBooks.isPressed())
		{
			PopEnchantTagsConfig.showBooks = !PopEnchantTagsConfig.showBooks;
			sync = true;
		}
		
		if (sync)
			PopEnchantTagsConfig.sync();
	}
}
