package com.shadowhawk.popenchanttags;

import com.mojang.blaze3d.platform.GlStateManager;
import com.shadowhawk.popenchanttags.config.PopEnchantTagsConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
//keep for 1.8.9 updates
//import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ForgeRegistries;

public class PopEnchantTagsRenderer 
{
	public static float remainingHighlightTicks;
	public static ItemStack highlightingItemStack;
	public static long ticks;
	
	public PopEnchantTagsRenderer()
	{
		remainingHighlightTicks = -1.0F;
		highlightingItemStack = null;
		ticks = 0L;
	}
	
	public boolean tagsVisible()
	{
		return PopEnchantTagsConfig.enabled;
	}
	
	public boolean getBooks()
	{
		return PopEnchantTagsConfig.showBooks;
	}
	
	public void render(int screenWidth, int screenHeight)
	{
		Minecraft mc = Minecraft.getInstance();

		if (mc.player != null && (PopEnchantTagsConfig.enabled || PopEnchantTagsConfig.showBooks))
		{
			ItemStack items = mc.player.inventory.getCurrentItem();

			if (items != null && items.hasTag())
			{
				int transparency = (int)(remainingHighlightTicks * 256.0F / 10.0F);

				if (transparency > 255)
				{
					transparency = 255;
				}

				if (transparency > 0)
				{
					String testString = "";
					String[] displayStrings = {"","","",""};
					TextFormatting color = TextFormatting.AQUA;
					//keep for 1.8.9 updates
					//EnumChatFormatting color = EnumChatFormatting.AQUA;
					boolean hidden = false;
					int lines = 1;
					int enchantCount = 0;
					int entriesPerLine = 0;

					if (items.getTag().contains("HideFlags"))
					{
						hidden = (items.getTag().getInt("HideFlags") & 1) != 0;
					}

					ListNBT tagList = null;
					if(PopEnchantTagsConfig.enabled)
					{
						tagList = items.getEnchantmentTagList();
					}
					if((items.getItem() instanceof EnchantedBookItem) && PopEnchantTagsConfig.showBooks)
					{
						tagList = EnchantedBookItem.getEnchantments(items);
						color = TextFormatting.YELLOW;
						//keep for 1.8.9 updates
						//color = EnumChatFormatting.YELLOW;
					}
					

					if (!hidden && tagList != null)
					{
						FontRenderer var13 = mc.fontRenderer;
						for (int fontRenderer = 0; fontRenderer < tagList.size(); ++fontRenderer)
						{
							if(var13.getStringWidth(testString) > screenWidth - 120)
							{
								lines++;
								testString = "";
							}
							String enchantmentId = tagList.getCompound(fontRenderer).getString("id");
							short enchantmentLevel = tagList.getCompound(fontRenderer).getShort("lvl");
							Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(enchantmentId));
							
							testString += testString != "" ? " " : "";
							testString += enchantment.getDisplayName(enchantmentLevel).getFormattedText();
						}
						entriesPerLine = (int)Math.ceil(tagList.size()/lines);
						
						for (int fontRenderer = 0; fontRenderer < tagList.size(); ++fontRenderer)
						{
							String enchantmentId = tagList.getCompound(fontRenderer).getString("id");
							short enchantmentLevel = tagList.getCompound(fontRenderer).getShort("lvl");
							
							Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(enchantmentId));
							
							int currentLine = 0;
							if(enchantCount == entriesPerLine)
							{
								if(displayStrings[3] != "" && var13.getStringWidth(displayStrings[3]) < screenWidth - 120)
								{
									currentLine = 3;
								} else if(displayStrings[2] != "" && var13.getStringWidth(displayStrings[2]) < screenWidth - 120)
								{
									currentLine = 2;
								} else if(displayStrings[1] != "" && var13.getStringWidth(displayStrings[1]) < screenWidth - 120)
								{
									currentLine = 1;
								} else
								{
									displayStrings[3] = displayStrings[2];
									displayStrings[2] = displayStrings[1];
									displayStrings[1] = displayStrings[0];
									displayStrings[0] = "";
									enchantCount = 0;
								}
							}
							displayStrings[currentLine] += displayStrings[currentLine] != "" ? " " : "";
							displayStrings[currentLine] += enchantment.getDisplayName(enchantmentLevel).applyTextStyle(color).getFormattedText();
							enchantCount++;
							currentLine = 0;
						}	

						int[] x = {0, 0, 0, 0};
						int[] y = {0, 0, 0, 0};
						int j = 0;
						//if(Minecraft.getMinecraft().theWorld.getWorldInfo().getGameType() == GameType.SURVIVAL || Minecraft.getMinecraft().theWorld.getWorldInfo().getGameType() == GameType.ADVENTURE)
						if(Minecraft.getInstance().playerController.isNotCreative())
						{
							j = 1;
						}
						for(int i = 0; i <= 3; i++){
							//displayStrings[i] = color + displayStrings[i];
							x[i] = (screenWidth - var13.getStringWidth(displayStrings[i])) / 2;
							y[i] = screenHeight - 59 - (14 * (i + j));
						}

						GlStateManager.pushMatrix();
						GlStateManager.enableBlend();
						GlStateManager.blendFuncSeparate(770, 771, 1, 0);
						for(int i = 0; i <= 3; i++){
							var13.drawStringWithShadow(displayStrings[i], (float)x[i], (float)y[i], 16777215 + (transparency << 24));
						}
						GlStateManager.disableBlend();
						GlStateManager.popMatrix();
					}
				}
			}
		}
	}
	
	public void tick(Minecraft minecraft)
	{
		if (minecraft.isGameFocused() && minecraft.player != null)
		{
			ItemStack items = minecraft.player.inventory.getCurrentItem();

			if (items == null)
			{
				remainingHighlightTicks = 0.0F;
			}
			else if (highlightingItemStack != null && items.getItem() == highlightingItemStack.getItem() && ItemStack.areItemStackTagsEqual(items, highlightingItemStack) && (items.isDamageable() || items.equals(highlightingItemStack, false)))
			{
				if (remainingHighlightTicks > 0.0F)
				{
					remainingHighlightTicks = (float)(40L - (System.currentTimeMillis() - ticks) / 50L);
				}
			}
			else
			{
				remainingHighlightTicks = 40.0F;
				ticks = System.currentTimeMillis();
			}

			highlightingItemStack = items;
		}
	}
	
}
