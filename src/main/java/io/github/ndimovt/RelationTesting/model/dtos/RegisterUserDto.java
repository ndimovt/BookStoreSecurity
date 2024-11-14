package io.github.ndimovt.RelationTesting.model.dtos;

import lombok.Data;

import java.util.Date;
@Data
public class RegisterUserDto {
    private Long id;
    private String username;
    private String password;
    private String name;
    private Date createdAt;
    private Date updatedAt;

    public RegisterUserDto(Long id, String username, String password, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.createdAt = new Date();
    }
}
