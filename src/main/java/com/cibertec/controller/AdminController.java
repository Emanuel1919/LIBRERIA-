package com.cibertec.controller;

import java.io.InputStream;
import java.sql.Connection;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.model.Usuario;
import com.cibertec.service.LibroService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private DataSource dataSource;

    // ================= DASHBOARD =================
    @GetMapping("/dashboard")
    public String mostrarDashboard(HttpSession session, Model model) {

        Usuario user = (Usuario) session.getAttribute("usuarioLogueado");

        if (user == null || !String.valueOf(user.getRol()).equals("ROLE_ADMIN")) {
            return "redirect:/login";
        }

        model.addAttribute("totalLibros", libroService.listarTodos().size());
        return "admin/dashboard";
    }

    // ================= VISTA REPORTES =================
    @GetMapping("/reportes")
    public String verReportes(HttpSession session) {

        Usuario user = (Usuario) session.getAttribute("usuarioLogueado");

        if (user == null || !String.valueOf(user.getRol()).equals("ROLE_ADMIN")) {
            return "redirect:/login";
        }

        return "admin/reportes/reportes";
    }

    // ================= REPORTE VENTAS PDF =================
    @GetMapping("/reportes/ventas/pdf")
    public void reporteVentasPDF(HttpServletResponse response) throws Exception {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=reporte_ventas.pdf");

        InputStream reportStream = getClass()
                .getResourceAsStream("/reportes/ventas_mes.jrxml");

        // 1️⃣ Compilar el jrxml
        JasperReport jasperReport =
                JasperCompileManager.compileReport(reportStream);

        // 2️⃣ Obtener conexión a la BD
        Connection conexion = dataSource.getConnection();

        // 3️⃣ Llenar reporte usando la conexión (NO Bean)
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        null,
                        conexion
                );

        // 4️⃣ Exportar a PDF
        JasperExportManager.exportReportToPdfStream(
                jasperPrint,
                response.getOutputStream()
        );

        conexion.close();
    }

    // ================= REPORTE LIBROS PDF =================
    @GetMapping("/reportes/libros/pdf")
    public void reporteLibrosPDF(HttpServletResponse response) throws Exception {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=reporte_libros.pdf");

        InputStream reporteStream = getClass()
                .getResourceAsStream("/reportes/REPORTE_DE_STOCK.jrxml");

        if (reporteStream == null) {
            throw new RuntimeException("No se encontró el archivo libros_stock.jrxml");
        }

        JasperReport jasperReport =
                JasperCompileManager.compileReport(reporteStream);

        Connection conexion = dataSource.getConnection();

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        null,
                        conexion
                );

        JasperExportManager.exportReportToPdfStream(
                jasperPrint,
                response.getOutputStream()
        );

        conexion.close();
    }

}