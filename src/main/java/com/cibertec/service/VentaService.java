package com.cibertec.service;



import com.cibertec.model.Venta;
import com.cibertec.model.DetalleVenta;
import com.cibertec.model.Libro;
import com.cibertec.repository.VentaRepository;
import com.cibertec.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Transactional // Esto asegura que si algo falla, no se guarde nada (Atomicidad)
    public Venta realizarVenta(Venta venta) {
        // 1. Procesar cada detalle para actualizar stock
        for (DetalleVenta detalle : venta.getDetalles()) {
            Libro libro = libroRepository.findById(detalle.getLibro().getIdLibro()).orElse(null);
            if (libro != null) {
                // Restamos la cantidad comprada del stock actual
                libro.setStock(libro.getStock() - detalle.getCantidad());
                libroRepository.save(libro);
            }
        }
        // 2. Guardar la venta (esto guardará también los detalles por el CascadeType.ALL)
        return ventaRepository.save(venta);
    }

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }
}