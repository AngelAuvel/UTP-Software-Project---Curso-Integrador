package com.utp.integrador.clinica.service.impl;

import com.utp.integrador.clinica.dto.DashboardKpisDto;
import com.utp.integrador.clinica.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public DashboardKpisDto getDashboardKpis() {
        String sql = "SELECT * FROM v_dashboard_kpis";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            DashboardKpisDto dto = new DashboardKpisDto();
            dto.setSolicitudesMes(rs.getLong("solicitudes_mes"));
            dto.setSolicitudesCompletadasMes(rs.getLong("solicitudes_completadas_mes"));
            dto.setSolicitudesPendientes(rs.getLong("solicitudes_pendientes"));
            dto.setTiempoPromedioAtencionDias(rs.getDouble("tiempo_promedio_atencion_dias"));
            dto.setProductosStockCritico(rs.getLong("productos_stock_critico"));
            dto.setProductosStockBajo(rs.getLong("productos_stock_bajo"));
            dto.setCostoTotalMes(rs.getDouble("costo_total_mes"));
            dto.setTasaCumplimientoOntime(rs.getDouble("tasa_cumplimiento_ontime"));
            return dto;
        });
    }
}
