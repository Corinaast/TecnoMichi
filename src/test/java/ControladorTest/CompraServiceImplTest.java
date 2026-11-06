/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControladorTest;

import Modelo.Celular;
import Modelo.Compra;
import Modelo.ICelularRepository;
import Modelo.ICompraRepository;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import ControladorTest.CompraServiceImpl;

/**
 *
 * @author caaco
 */
public class CompraServiceImplTest {
    @Mock
    private ICelularRepository celularRepository;

    @Mock
    private ICompraRepository compraRepository;

    @InjectMocks
    private CompraServiceImpl compraService;  // Cambiar a la clase correcta

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessPurchase() {
        String username = "testUser";
        Map<Celular, Integer> productos = new HashMap<>();

        // Crear un celular con el constructor correcto (ajusta según tu clase Celular)
        // Por ejemplo, si tu Celular tiene constructor con (String id, String nombre, String marca, double precio, ...)
        Celular celular = new Celular("1", "iPhone", "Apple", 1000.0,
                                     "4GB", "128GB", "Black", "Dual SIM", 5);
        productos.put(celular, 1);

        when(celularRepository.findById("1")).thenReturn(celular);
        when(compraRepository.save(any(Compra.class))).thenReturn(true);

        boolean result = compraService.processPurchase(username, productos);

        assertTrue(result);
        verify(compraRepository).save(any(Compra.class));
    }
}
