package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

public class ExampleMod implements ModInitializer {
	public static final String MOD_ID = "anti_n";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("anti")
			.then(CommandManager.argument("fir", IntegerArgumentType.integer())
			.then(CommandManager.argument("mod", StringArgumentType.string())
			.then(CommandManager.argument("sec", IntegerArgumentType.integer())
			.executes(context -> {
				ServerCommandSource source = context.getSource();
				int fir = IntegerArgumentType.getInteger(context, "fir");
				int sec = IntegerArgumentType.getInteger(context, "sec");
				String mod = StringArgumentType.getString(context, "mod");
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
						source.sendMessage(Text.literal("error"));
						return 0;
				}
				source.sendMessage(Text.literal(fir + mod + sec + "=" + res));
				return 1;
			})))));});
		LOGGER.info("[aNTi_N] Кто прочитал тот лох");
	}
}