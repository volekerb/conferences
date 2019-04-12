package com.conference.magichat;

import lombok.Data;

import java.util.List;

@Data
public class Spell {
    private String id;
    private String name;
    private List<String> words;
}
