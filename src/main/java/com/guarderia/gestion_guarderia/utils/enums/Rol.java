package com.guarderia.gestion_guarderia.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;



import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.guarderia.gestion_guarderia.utils.enums.Permission.*;

@RequiredArgsConstructor
public enum Rol {
PARVULARIA(Set.of(Permission.values())),
    ASISTENTE_PARVULO(Set.of(
            ACTIVIDAD_READ,
            ASISTENCIA_READ,
            ASISTENCIA_REGISTRAR,
            ASISTENCIA_UPDATE,
            ASISTENCIA_DELETE,
            FOTO_UPLOAD,
            FOTO_READ,
            FOTO_DELETE,
            FOTO_DOWNLOAD,
            PARVULARIA_READ,
            ASISTENTE_PARVULO_READ,
            ASISTENTE_PARVULO_UPDATE,
            PARVULO_READ,
            APODERADO_READ
    )),
    APODERADO(Set.of(
            ACTIVIDAD_READ,
            ASISTENCIA_READ,
            FOTO_READ,
            PARVULO_READ,
            APODERADO_READ
    ) );

    @Getter
    private final Set<Permission> permissions;
   /* Se obtienen los permisos de cada rol.
   Se devuelve una lista de permisos con el prefijo "ROLE_" para que Spring Security pueda reconocerlo como un rol.

   */
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }



}
