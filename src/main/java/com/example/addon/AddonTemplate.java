package com.example.addon;

import com.example.addon.commands.CommandExample;
import com.example.addon.hud.HudExample;
import com.example.addon.modules.ModuleExample;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class AddonTemplate extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Example");
    public static final HudGroup HUD_GROUP = new HudGroup("Example");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Meteor Addon Template");

        // --- DANG KY CAC MODULES ---
        
        // 1. Module Discord Webhook Logger
        Modules.get().add(new DiscordChatLogger());

        // 2. Module ESP / Soi Spawner
        Modules.get().add(new KingSpawnerESP());

        // 3. Module Lọc & Drop Item Spawner (Whitelist / Blacklist)
        Modules.get().add(new AutoSpawnerLootFiltered());

        // 4. Module Loop Spam Dispenser (Tự động xả & mở lại Spawner)
        Modules.get().add(new AutoSpawnerLoop());

        // 5. Các module phụ trợ khác (nếu dùng)
        Modules.get().add(new AutoDrop());
        Modules.get().add(new AutoOrder());

        // --- HUD ---
        Hud.get().register(HudExample.INFO);
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "com.example.addon";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("MeteorDevelopment", "meteor-addon-template");
    }
}
