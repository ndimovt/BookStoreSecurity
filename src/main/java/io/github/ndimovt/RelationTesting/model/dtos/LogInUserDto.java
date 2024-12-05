package io.github.ndimovt.RelationTesting.model.dtos;

import lombok.Data;

/**
 * The class LogInUserDto
 */
@Data
public class LogInUserDto {
    private String username;
    private String password;
    private String role;
}
