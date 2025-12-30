package com.venifretes.dto.request;

import com.venifretes.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para atualizar role de usuário")
public class UpdateUserRoleRequest {

    @NotNull(message = "Role é obrigatória")
    @Schema(description = "Nova role do usuário", example = "ADMIN")
    private Role role;
}
