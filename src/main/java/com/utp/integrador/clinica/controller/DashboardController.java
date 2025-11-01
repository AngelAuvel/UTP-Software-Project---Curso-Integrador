package com.utp.integrador.clinica.controller;

import com.utp.integrador.clinica.dto.DashboardKpisDto;
import com.utp.integrador.clinica.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@PreAuthorize("hasRole('ADMIN_LOGISTICA') or hasRole('JEFE_DEPARTAMENTO')")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/kpis")
    public DashboardKpisDto getKpis() {
        return dashboardService.getDashboardKpis();
    }
}
