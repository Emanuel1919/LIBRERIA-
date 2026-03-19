package com.cibertec.controller;

import java.io.InputStream;
import java.sql.Connection;

import javax.sql.DataSource;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.jasperreports.engine.*;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/ventas")
    public void reporteVentas(HttpServletResponse response) throws Exception {

        InputStream reporteStream = getClass()
                .getResourceAsStream("/reportes/ventas_mes.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(reporteStream);

        Connection conexion = dataSource.getConnection();

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                conexion
        );

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=ventas_mes.pdf");

        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        conexion.close();
    }
}