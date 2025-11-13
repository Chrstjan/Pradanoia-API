package Pradanoia.payloads.utils;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String username;
    private String password;
}
