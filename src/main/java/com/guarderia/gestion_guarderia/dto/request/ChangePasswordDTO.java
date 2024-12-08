package com.guarderia.gestion_guarderia.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}
