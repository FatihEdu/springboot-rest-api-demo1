package com.example.fth.fth_demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties (
    //@DefaultValue("false")
    //boolean debug
) {}
