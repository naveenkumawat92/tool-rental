package com.Tools.toolrental.DTO;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class config {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }}
