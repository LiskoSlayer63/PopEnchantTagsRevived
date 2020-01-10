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

@Mod.EventBusSubscriber(modid = ForgeModPopEnchantTagsRevived.MOD_ID)
public class ClientEvents 
{
	@SubscribeEvent
	public static void onConfigChanged(final OnConfigChangedEvent event) 
	{
		if (event.getModID().equals(ForgeModPopEnchantTagsRevived.MOD_ID))
			ConfigPopEnchantTags.sync();
	}
	
	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) 
	{
		if (event.isCanceled() || !event.getType().equals(ElementType.ALL)) return;

		ForgeModPopEnchantTagsRevived instance = ForgeModPopEnchantTagsRevived.instance;
		ScaledResolution resolution = event.getResolution();

    	instance.renderer.render(resolution.getScaledWidth(), resolution.getScaledHeight());
	}
	
	@SubscribeEvent
	public static void onTick(TickEvent.ClientTickEvent event) 
	{
		if (event.isCanceled()) return;

		Minecraft minecraft = Minecraft.getMinecraft();
		ForgeModPopEnchantTagsRevived instance = ForgeModPopEnchantTagsRevived.instance;

    	instance.renderer.tick(minecraft);
	}
	
	@SubscribeEvent
	public static void onKeyInput(KeyInputEvent event)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		
		if (event.isCanceled() || minecraft.isGamePaused() || !Minecraft.isGuiEnabled()) return;
		
        if (ForgeModPopEnchantTagsRevived.toggleTags.isPressed())
        	ConfigPopEnchantTags.enabled = !ConfigPopEnchantTags.enabled;
        
        if(ForgeModPopEnchantTagsRevived.toggleBooks.isPressed())
        	ConfigPopEnchantTags.showBooks = !ConfigPopEnchantTags.showBooks;
        
        ConfigPopEnchantTags.sync();
	}
}
