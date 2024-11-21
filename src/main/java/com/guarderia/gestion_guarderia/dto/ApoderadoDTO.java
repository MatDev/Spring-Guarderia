package com.guarderia.gestion_guarderia.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApoderadoDTO extends UsuarioDTO{

    private List<ParvuloDTO> parvulos;
}
