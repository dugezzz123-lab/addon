package com.example.addon.modules;

import com.example.addon.AddonTemplate;
import meteordevelopment.meteorclient.events.game.ReceiveMessageEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.network.Http;
import meteordevelopment.orbit.EventHandler;

public class DiscordChatLogger extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<String> webhookUrl = sgGeneral.add(new StringSetting.Builder()
        .name("webhook-url")
        .description("URL của Discord Webhook để gửi nội dung chat.")
        .defaultValue("")
        .build()
    );

    public DiscordChatLogger() {
        super(AddonTemplate.CATEGORY, "discord-chat-logger", "Tự động ghi lại và gửi tin nhắn chat trong game qua Discord Webhook.");
    }

    @EventHandler
    private void onReceiveMessage(ReceiveMessageEvent event) {
        if (webhookUrl.get().isEmpty()) return;
        String message = event.getMessage().getString();

        // Gửi qua Discord Webhook bằng luồng riêng để tránh giật lag game
        new Thread(() -> {
            try {
                Http.post(webhookUrl.get())
                    .json(new Payload(message))
                    .send();
            } catch (Exception e) {
                // Xử lý ngoại lệ kết nối mạng nếu có
            }
        }).start();
    }

    private static class Payload {
        public String content;
        public Payload(String content) {
            this.content = "[King SMP Log] ```" + content + "```";
        }
    }
}
