package com.arjen0203.codex.domain.user.dto;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RemoveUserDto {
    private UUID id;
}
