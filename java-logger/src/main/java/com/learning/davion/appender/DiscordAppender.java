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
import org.json.JSONArray;
import org.json.JSONException;

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
        this.webhookUrl = System.getenv().getOrDefault("DISCORD_WEBHOOK_URL",
                "your_webhook_token");

        this.isDiscordUp = checkHealth();
        if (this.isDiscordUp) {
            super.start();
        } else {
            addError("discord health check failed");
            return;
        }
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (this.isDiscordUp) {
            String message = this.buildMessage(eventObject);
            HttpResponse<String> response = this.callDiscordAPI(message);
            System.out.println(message);
            if (response.statusCode() != 204) {
                addError("message called failed");
            }
        }
    }

    private String buildMessage(ILoggingEvent eventObject) {
        Object[] args = eventObject.getArgumentArray();
        JSONObject description = new JSONObject()
                .put("message", eventObject.getFormattedMessage());
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof String) {
                    try {
                        JSONObject data = new JSONObject(arg);
                        System.out.println(arg);
                        System.out.println(data.toString()); // somehow the json is not converted back properly causing
                                                             // the parsing failed

                        if (data.has("data")) {
                            for (String key : JSONObject.getNames(data)) {
                                description.put(key, data.get(key));
                            }
                        }
                    } catch (JSONException e) {
                        addWarn("Argument is not valid JSON: " + arg);
                    }
                }
            }
        }

        JSONObject embed = new JSONObject()
                .put("description", description)
                .put("title", String.format("Calling from %s", eventObject.getThreadName()));

        JSONArray embeds = new JSONArray()
                .put(embed);

        JSONObject logEntry = new JSONObject()
                .put("content", String.format("Log level: %s", eventObject.getLevel()))
                .put("username", String.format("App name: %s", eventObject.getLoggerName()))
                .put("embeds", embeds);

        return logEntry.toString();
    }

    private boolean checkHealth() {
        String healthCheckJson = new JSONObject()
                .put("content", "/ping: checking Discord availability...")
                .toString();

        HttpResponse<String> response = this.callDiscordAPI(healthCheckJson);
        if (response.statusCode() != 204) {
            addError(String.format("failed connecting to discord, got status code [%s]", response.statusCode()));
            return false;
        } else {
            return true;
        }
    }

    private HttpResponse<String> callDiscordAPI(String requestBody) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.webhookUrl))
                .headers(this.headers.toArray(String[]::new))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .timeout(Duration.ofSeconds(10))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException e) {
            addError(String.format("Exception occurred during Discord health check %s", e));
            return null;
        }
    }
}
