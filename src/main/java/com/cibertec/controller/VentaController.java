package com.cibertec.controller;

import com.cibertec.model.Venta;
import com.cibertec.model.Usuario;
import com.cibertec.model.DetalleVenta;
import com.cibertec.model.Libro;
import com.cibertec.service.VentaService;
import com.cibertec.service.LibroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private LibroService libroService;

    // 1. AÑADIR AL CARRITO
    @GetMapping("/nueva")
    public String añadirAlCarrito(@RequestParam("idLibro") int idLibro, HttpSession session) {
        // Recuperamos la lista del carrito de la sesión o creamos una nueva
        List<Libro> carrito = (List<Libro>) session.getAttribute("itemsCarrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        // Buscamos el libro y lo añadimos
        Libro libro = libroService.buscarPorId(idLibro);
        if (libro != null) {
            carrito.add(libro);
        }

        session.setAttribute("itemsCarrito", carrito);
        return "redirect:/"; // Regresa al catálogo para seguir comprando
    }

    // 2. VER EL CARRITO
    @GetMapping("/carrito")
    public String verCarrito(HttpSession session, Model model) {
        List<Libro> carrito = (List<Libro>) session.getAttribute("itemsCarrito");
        
        double total = 0;
        if (carrito != null) {
            total = carrito.stream().mapToDouble(Libro::getPrecio).sum();
        }
        
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        return "admin/ventas/carrito"; // Crearemos esta vista
    }

    // 3. PROCESAR LA COMPRA (CHECKOUT)
    @GetMapping("/comprar")
    public String procesarCompra(HttpSession session, Model model) {
        // 1. OJO: Verificamos si el usuario existe en la sesión
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            // Si no está logueado, lo mandamos al login con un mensaje
            return "redirect:/login";
        }

        // 2. Recuperamos el carrito
        List<Libro> carrito = (List<Libro>) session.getAttribute("itemsCarrito");
        if (carrito == null || carrito.isEmpty()) {
            return "redirect:/"; // Si el carrito está vacío, al catálogo
        }

        // 3. Lógica de guardado Maestro-Detalle
        try {
            Venta venta = new Venta();
            venta.setUsuario(usuarioLogueado);
            venta.setFechaVenta(LocalDateTime.now(ZoneId.of("America/Lima")));
            
            List<DetalleVenta> detalles = new ArrayList<>();
            double total = 0;

            for (Libro libro : carrito) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setVenta(venta);
                detalle.setLibro(libro);
                detalle.setCantidad(1);
                detalle.setPrecioUnitario(libro.getPrecio());
                detalles.add(detalle);
                total += libro.getPrecio();
            }

            venta.setDetalles(detalles);
            venta.setTotalVenta(total);

            // Guardamos en la BD
            ventaService.realizarVenta(venta);

            // 4. Limpiamos el carrito de la sesión
            session.removeAttribute("itemsCarrito");

            return "redirect:/ventas/exito";
            
        } catch (Exception e) {
            model.addAttribute("error", "Hubo un problema al procesar tu compra.");
            return "admin/ventas/carrito";
        }
    }

    @GetMapping("/exito")
    public String vistaExito() {
        return "admin/ventas/exito"; // Ruta de la vista
    }

  
    
    
    @GetMapping("/quitar")
    public String quitarDelCarrito(@RequestParam("index") int index, HttpSession session) {
        List<Libro> carrito = (List<Libro>) session.getAttribute("itemsCarrito");
        
        if (carrito != null && index >= 0 && index < carrito.size()) {
            carrito.remove(index);
        }
        
        session.setAttribute("itemsCarrito", carrito);
        return "redirect:/ventas/carrito";
    }
}