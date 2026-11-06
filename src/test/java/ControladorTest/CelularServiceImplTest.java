package ControladorTest;

import Modelo.Celular;
import Modelo.ICelularRepository;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class CelularServiceImplTest {
    @Mock
    private ICelularRepository celularRepository;

    private CelularServiceImpl celularService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        celularService = new CelularServiceImpl(celularRepository);
    }

    @Test
  
    void testAddCelular() {
        Celular celular = new Celular("iPhone 13", "Apple", 999.99,
                                    "4GB", "128GB", "Midnight", "Dual SIM");
        when(celularRepository.save(any(Celular.class))).thenReturn(true);

        boolean resultado = celularService.addCelular(celular, 10);

        assertTrue(resultado);
        verify(celularRepository).save(celular);
}


    @Test
    void testGetAllCelulares() {
        List<Celular> celularesMock = Arrays.asList(
            new Celular("1", "Galaxy S21", "Samsung", 899.99, 
                       "8GB", "128GB", "Phantom Gray", "Single SIM", 5),
            new Celular("2", "Pixel 6", "Google", 599.99, 
                       "8GB", "128GB", "Stormy Black", "Dual SIM", 8)
        );
        when(celularRepository.findAll()).thenReturn(celularesMock);

        List<Celular> resultado = celularService.getAllCelulares();

        assertEquals(2, resultado.size());
        assertEquals("Galaxy S21", resultado.get(0).getNombre());
        verify(celularRepository).findAll();
    }

    @Test
    void testUpdateCelular() {
        String id = "1";
        Celular celularActualizado = new Celular(id, "iPhone 13 Pro", "Apple", 
                                               1099.99, "6GB", "256GB", "Silver", "Dual SIM", 3);

        when(celularRepository.update(celularActualizado)).thenReturn(true);

        boolean resultado = celularService.updateCelular(celularActualizado, id);

        assertTrue(resultado);
        verify(celularRepository).update(celularActualizado);
    }

    @Test
    void testDeleteCelular() {
        String id = "1";
        when(celularRepository.delete(id)).thenReturn(true);

        boolean resultado = celularService.deleteCelular(id);

        assertTrue(resultado);
        verify(celularRepository).delete(id);
    }

    @Test
    void testSearchCelulares() {
        List<Celular> celularesMock = Arrays.asList(
            new Celular("1", "Galaxy S21", "Samsung", 899.99, 
                       "8GB", "128GB", "Phantom Gray", "Single SIM", 5),
            new Celular("2", "Galaxy S20", "Samsung", 699.99, 
                       "8GB", "128GB", "Cloud Blue", "Single SIM", 3)
        );

        when(celularRepository.findAll()).thenReturn(celularesMock);

        // Busca por marca "Samsung"
        List<Celular> resultado = celularService.searchCelulares(null, "Samsung", null, null, null, null, null);

        assertEquals(2, resultado.size());
        verify(celularRepository).findAll();
    }
}
