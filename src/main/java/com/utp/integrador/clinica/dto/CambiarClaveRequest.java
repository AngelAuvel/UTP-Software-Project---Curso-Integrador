package com.utp.integrador.clinica.dto;

import lombok.Data;

@Data
public class CambiarClaveRequest {
    private String claveActual;
    private String nuevaClave;
}
