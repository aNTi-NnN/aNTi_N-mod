package com.anti;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.util.InputUtil;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import java.security.Key;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl._3DFXTextureCompressionFXT1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.CommandNode;

public class Client implements ClientModInitializer {
	public static final String MOD_ID = "anti_n";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static KeyBinding OpenGUIcall;
	public static KeyBinding Auto;
	@Override
	public void onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		Auto = KeyBindingHelper.registerKeyBinding(
			new KeyBinding(
				"key.anti.auto",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_O,
				"category.anti.keys"
			)
		);
		OpenGUIcall = KeyBindingHelper.registerKeyBinding(
			new KeyBinding(
				"key.anti.open_calc",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_C,
				"category.anti.keys"
			)
		);

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
					dispatcher.register(
						ClientCommandManager.literal("cam")
						.then(ClientCommandManager.argument("Yaw", IntegerArgumentType.integer())
						.then(ClientCommandManager.argument("Pitch", IntegerArgumentType.integer())
						.executes(context -> {
							int Yaw = IntegerArgumentType.getInteger(context, "Yaw");
							int Pitch = IntegerArgumentType.getInteger(context, "Pitch");
							ClientPlayerEntity plr = MinecraftClient.getInstance().player;
							plr.setYaw(Yaw);
							plr.setPitch(Pitch);
							return 1;
						})
						)
						)
					);
					});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(
				ClientCommandManager.literal("calc")
				.then(ClientCommandManager.argument("fir", IntegerArgumentType.integer())
				.then(ClientCommandManager.argument("mod", StringArgumentType.string())
				.then(ClientCommandManager.argument("sec", IntegerArgumentType.integer())
				.executes(context -> {
					int fir = IntegerArgumentType.getInteger(context, "fir");
					String mod = StringArgumentType.getString(context, "mod");
					int sec = IntegerArgumentType.getInteger(context, "sec");
									double res = 0;
				switch (mod) {
					case "+":
						res = fir + sec;
						break;
					case "-":
						res = fir - sec;
						break;
					case "*":
						res = fir * sec;
						break;
					case "/":
						res = fir / sec;
						break;
					default:
						return 0;
				}
				context.getSource().sendFeedback(Text.literal(fir + mod + sec + "=" + res));
				return 1;
			})
			))));
		});
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (OpenGUIcall.wasPressed()) {
				client.setScreen(new com.anti.CalcScreen());
			}
		});
		ClientTickEvents.END_CLIENT_TICK.register( client -> {
			if (Auto.wasPressed()) {
				client.player.sendMessage(Text.literal("bind").formatted(Formatting.AQUA), false);
				ClientPlayerEntity player = MinecraftClient.getInstance().player;
				if (player != null) {
					player.setYaw(0);
					player.setPitch(0);
				}
			}
		});
		LOGGER.info("\n##################################\n---------------- aNTi-N\n---------------- Mod\n---------------- for fabric\n##################################");
	}
}