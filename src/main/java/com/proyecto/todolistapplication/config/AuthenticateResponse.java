package com.proyecto.todolistapplication.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class AuthenticateResponse //DTO(Data Transfer Object)
{
    private String token;
}
