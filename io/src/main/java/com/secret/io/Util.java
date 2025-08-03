package com.secret.io;

import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class Util {

    public static final Map<Character, String> emojiMap = Map.ofEntries(
            Map.entry('a', "🍎"), Map.entry('b', "🍌"), Map.entry('c', "🐱"),
            Map.entry('d', "🐶"), Map.entry('e', "🐘"), Map.entry('f', "🐸"),
            Map.entry('g', "🦒"), Map.entry('h', "🏠"), Map.entry('i', "🍦"),
            Map.entry('j', "🤹"), Map.entry('k', "🎋"), Map.entry('l', "🦁"),
            Map.entry('m', "🌝"), Map.entry('n', "🎶"), Map.entry('o', "🐙"),
            Map.entry('p', "🥞"), Map.entry('q', "👸"), Map.entry('r', "🤖"),
            Map.entry('s', "🐍"), Map.entry('t', "🌴"), Map.entry('u', "☂️"),
            Map.entry('v', "🎻"), Map.entry('w', "🌊"), Map.entry('x', "❌"),
            Map.entry('y', "🛳️"), Map.entry('z', "⚡"),
            Map.entry(' ', "⬜")
    );
}
