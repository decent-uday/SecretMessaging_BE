package com.secret.io.Service.impl;

import com.secret.io.Service.SecretService;
import com.secret.io.Util;
import com.secret.io.dto.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SecretServiceImpl implements SecretService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public GenericResponse encryptMessage(Map message) {
        GenericResponse genericResponse = new GenericResponse();

        try {
            // Extract Message and Password
            String originalMessage = (String) message.get("Message");
            String password = (String) message.get("auth");

            if (originalMessage == null || password == null) {
                genericResponse.setStatus(HttpStatus.NOT_FOUND.toString());
                genericResponse.setMessage("Message or Password is missing");
                return genericResponse;
            }

            // Emoji Encryption (Simple logic for demonstration)
            String encryptedMessage = encryptToEmoji(originalMessage);

            // Store in Redis
            redisTemplate.opsForValue().set(password, encryptedMessage);

            // Prepare response
            genericResponse.setStatus(HttpStatus.OK.toString());
            genericResponse.setMessage("Message encrypted and stored successfully.");
            genericResponse.setData(Map.of("encrypted", encryptedMessage));
        } catch (Exception e) {
            genericResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            genericResponse.setMessage("Something went wrong: " + e.getMessage());
        }

        return genericResponse;
    }

    private String encryptToEmoji(String input) {
        // Basic character-to-emoji mapping
        Map<Character, String> emojiMap = Util.emojiMap;

        StringBuilder encrypted = new StringBuilder();
        for (char c : input.toLowerCase().toCharArray()) {
            encrypted.append(emojiMap.getOrDefault(c, "❓"));
        }

        return encrypted.toString();
    }

    public GenericResponse decryptMessage(Map<String, String> request) {
        GenericResponse genericResponse = new GenericResponse();

        try {
            String password = request.get("Password");

            if (password == null || password.isEmpty()) {
                genericResponse.setStatus("FAILURE");
                genericResponse.setMessage("Password is required");
                return genericResponse;
            }

            // Retrieve encrypted emoji message from Redis
            String encryptedMessage = redisTemplate.opsForValue().get(password);

            if (encryptedMessage == null) {
                genericResponse.setStatus("FAILURE");
                genericResponse.setMessage("No message found for the given password");
                return genericResponse;
            }

            // Decrypt back to text
            String decryptedMessage = decryptFromEmoji(encryptedMessage);

            // Prepare response
            genericResponse.setStatus("SUCCESS");
            genericResponse.setMessage("Message decrypted successfully");
            genericResponse.setData(Map.of("decrypted", decryptedMessage));
        } catch (Exception e) {
            genericResponse.setStatus("ERROR");
            genericResponse.setMessage("Something went wrong: " + e.getMessage());
        }

        return genericResponse;
    }

    private String decryptFromEmoji(String emojiString) {
        // Same map used in encryption
        Map<Character, String> emojiMap = Util.emojiMap;

        // Reverse the map: emoji to char
        Map<String, Character> reverseMap = emojiMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        // Convert emoji string to characters
        List<String> emojis = splitEmojis(emojiString);
        StringBuilder decrypted = new StringBuilder();

        for (String emoji : emojis) {
            decrypted.append(reverseMap.getOrDefault(emoji, '?'));
        }

        return decrypted.toString();
    }

    private List<String> splitEmojis(String emojiString) {
        List<String> emojis = new ArrayList<>();
        int offset = 0;
        while (offset < emojiString.length()) {
            int codePoint = emojiString.codePointAt(offset);
            int charCount = Character.charCount(codePoint);
            emojis.add(new String(emojiString.substring(offset, offset + charCount)));
            offset += charCount;
        }
        return emojis;
    }

}
