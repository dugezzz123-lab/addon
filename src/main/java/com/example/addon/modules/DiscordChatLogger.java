package com.example.addon.modules;



import com.example.addon.ExampleAddon;

import meteordevelopment.meteorclient.events.game.ReceiveMessageEvent;

import meteordevelopment.meteorclient.systems.modules.Module;

import meteordevelopment.orbit.EventHandler;



import java.io.OutputStream;

import java.net.HttpURLConnection;

import java.net.URL;

import java.nio.charset.StandardCharsets;



public class DiscordChatLogger extends Module {



    // Dán cố định Discord Webhook của bạn vào đây

    private static final String WEBHOOK_URL = "https://discord.com/api/webhooks/1561645069545509014/0X0YEjiSuBvu8Ev4xV20rLPD478Frm7a6ivIK3s-bcl9EXHlHXmXAdy_FgTsJgTwMiVA";



    public DiscordChatLogger() {

        super(ExampleAddon.CATEGORY, "auto-chat-logger", "Tự động gửi chat về Discord.");

        

        // Tự động bật module ngay khi khởi động game mà không cần mở GUI bật thủ công

        if (!isActive()) {

            toggle();

        }

    }



    @EventHandler

    private void onReceiveMessage(ReceiveMessageEvent event) {

        if (WEBHOOK_URL.isEmpty() || WEBHOOK_URL.contains("YOUR_WEBHOOK_URL_HERE")) return;



        String messageText = event.getMessage().getString();



        // Gửi request ở Thread riêng để tránh giật lag game

        new Thread(() -> sendToDiscord(WEBHOOK_URL, messageText)).start();

    }



    private void sendToDiscord(String webhookUrl, String message) {

        try {

            URL url = new URL(webhookUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);



            String safeMessage = message.replace("\\", "\\\\").replace("\"", "\\\"");

            String jsonPayload = "{\"content\": \"" + safeMessage + "\"}";



            try (OutputStream os = connection.getOutputStream()) {

                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);

                os.write(input, 0, input.length);

            }



            connection.getResponseCode();

            connection.disconnect();

        } catch (Exception ignored) {

            // Lỗi mạng hoặc URL sai sẽ tự bỏ qua

        }

    }

}
