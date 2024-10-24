package com.learning.davion.appender;

import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class DiscordAppender extends AppenderBase<ILoggingEvent> {

    private String webhookUrl;
    private final HttpClient client = HttpClient.newHttpClient();
    private final List<String> headers = new ArrayList<>();
    private boolean isDiscordUp = false;

    @Override
    public void start() {
        this.headers.add("Content-Type");
        this.headers.add("application/json; charset=utf-8");
        this.headers.add("Accept");
        this.headers.add("application/json");
        this.webhookUrl = System.getenv().getOrDefault("DISCORD_WEBHOOK_URL", "webhook_here");

        if (checkDiscordHealth()) {
            isDiscordUp = true;
            super.start();
        } else {
            addError("Failed to connect to Discord webhook during health check!");
            return;
        }
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (!isDiscordUp) {
            addWarn("Discord is not available. Log will not be sent.");
            return;
        }

        String message = buildMessage(eventObject);
        sendToDiscord(message);
    }

    private boolean checkDiscordHealth() {
        String healthCheckJson = new JSONObject()
                                    .put("content", "/ping: checking Discord availability...")
                                    .toString();

        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(this.webhookUrl))
                                .headers(this.headers.toArray(String[]::new))
                                .POST(HttpRequest.BodyPublishers.ofString(healthCheckJson))
                                .timeout(Duration.ofSeconds(10))
                                .build();
        
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                addError(String.format("failed connecting to discord, got status code [%s]", response.statusCode()));
            }
            System.err.println("checkpoint hit");
            return true;
        } catch (IOException | InterruptedException e) {
            addError(String.format("Exception occurred during Discord health check %s", e));
            return false;
        }
    }

    private String buildMessage(ILoggingEvent eventObject) {
        // Build your log message with the logging level and message
        return String.format("**%s**: %s", eventObject.getLevel(), eventObject.getFormattedMessage());
    }

    private void sendToDiscord(String message) {
        System.err.println(message);
    //     RequestBody body = RequestBody.create(
    //         MediaType.get("application/json; charset=utf-8"),
    //         "{\"content\":\"" + message + "\"}"
    //     );

    //     Request request = new Request.Builder()
    //         .url(webhookUrl)
    //         .post(body)
    //         .build();

    //     try (Response response = client.newCall(request).execute()) {
    //         if (!response.isSuccessful()) {
    //             addError("Failed to send log to Discord: " + response);
    //         }
    //     } catch (IOException e) {
    //         addError("Exception occurred while sending log to Discord", e);
    //     }
    }
}
