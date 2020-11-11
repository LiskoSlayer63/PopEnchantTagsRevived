package com.shadowhawk.popenchanttags.events;

import com.shadowhawk.popenchanttags.PopEnchantTags;
import com.shadowhawk.popenchanttags.config.ConfigHelper;
import com.shadowhawk.popenchanttags.config.PopEnchantTagsConfig;
import com.shadowhawk.popenchanttags.utils.Logger;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.event.TickEvent.ClientTickEvent;;


@EventBusSubscriber(modid = PopEnchantTags.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public class ClientEvents 
{
	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent event) 
	{
		if (event.isCanceled() || !Minecraft.isGuiEnabled() || !event.getType().equals(ElementType.ALL)) return;

		Minecraft minecraft = Minecraft.getInstance();
		PopEnchantTags instance = PopEnchantTags.instance;
		
		instance.renderer.render(event.getMatrixStack(), minecraft.getMainWindow().getScaledWidth(), minecraft.getMainWindow().getScaledHeight());
		
		Logger.debug("Width: " + minecraft.getMainWindow().getScaledWidth() + ", Height: " + minecraft.getMainWindow().getScaledHeight() );
	}

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent event) 
	{
		Minecraft minecraft = Minecraft.getInstance();
		
		if (event.isCanceled() || minecraft.isGamePaused()) return;

		PopEnchantTags instance = PopEnchantTags.instance;

		instance.renderer.tick(minecraft);
		
		if (!Minecraft.isGuiEnabled() || !minecraft.isGameFocused()) return;
		
		boolean sync = false;
		
		if (PopEnchantTags.toggleTags.isPressed())
		{
			PopEnchantTagsConfig.enabled = !PopEnchantTagsConfig.enabled;
			sync = true;
		}
		
		if(PopEnchantTags.toggleBooks.isPressed())
		{
			PopEnchantTagsConfig.showBooks = !PopEnchantTagsConfig.showBooks;
			sync = true;
		}
		
		if (sync)
			ConfigHelper.save();
	}
}
