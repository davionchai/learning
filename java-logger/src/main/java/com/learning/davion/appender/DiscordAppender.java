package com.learning.davion.appender;

import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

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
            if (response.statusCode() != 204) {
                addError("message called failed");
            }
        }
    }

    private String buildMessage(ILoggingEvent eventObject) {
        Object[] args = eventObject.getArgumentArray();
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> description = new HashMap<>();
        description.put("message", eventObject.getFormattedMessage());

        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof String) {
                    try {
                        JsonNode data = objectMapper.readTree((String) arg);
                        if (data.has("data")) {
                            Iterator<Entry<String, JsonNode>> fields = data.fields();

                            while (fields.hasNext()) {
                                Entry<String, JsonNode> field = fields.next();
                                description.put(field.getKey(), field.getValue());
                            }
                        }
                    } catch (JsonProcessingException err) {
                        addWarn("Argument is not valid JSON: " + arg);
                    }
                }
            }
        }

        try {
            Map<String, Object> embed = new HashMap<>();
            embed.put("description", objectMapper.writeValueAsString(description));
            embed.put("title", String.format("Calling from %s", eventObject.getThreadName()));

            List<Map<String, Object>> embeds = new ArrayList<>();
            embeds.add(embed);

            Map<String, Object> logEntry = new HashMap<>();
            logEntry.put("content", String.format("Log level: %s", eventObject.getLevel()));
            logEntry.put("username", String.format("App name: %s", eventObject.getLoggerName()));
            logEntry.put("embeds", embeds);

            ObjectMapper logEntryMapper = new ObjectMapper();
            return logEntryMapper.writeValueAsString(logEntry);
        } catch (JsonProcessingException err) {
            addError("%s", err);
            return "{}";
        }

    }

    private boolean checkHealth() {
        String healthCheckJson = "";
        try {
            healthCheckJson = new ObjectMapper()
                    .writeValueAsString(Map.of("content", "/ping: checking Discord availability..."));
        } catch (JsonProcessingException err) {
            addError("%s", err);
        }

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
