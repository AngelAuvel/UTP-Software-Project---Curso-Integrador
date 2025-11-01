package com.utp.integrador.clinica.dto;

import lombok.Data;

@Data
public class DashboardKpisDto {
    private long solicitudesMes;
    private long solicitudesCompletadasMes;
    private long solicitudesPendientes;
    private Double tiempoPromedioAtencionDias;
    private long productosStockCritico;
    private long productosStockBajo;
    private Double costoTotalMes;
    private Double tasaCumplimientoOntime;
}
