package ControladorTest;

import Modelo.Celular;
import Modelo.ICelularRepository;
import java.util.List;

public class CelularServiceImpl {

    private ICelularRepository celularRepository;

    public CelularServiceImpl(ICelularRepository celularRepository) {
        this.celularRepository = celularRepository;
    }

    public boolean addCelular(Celular celular, int cantidad) {
        // lógica simple de guardado
        return celularRepository.save(celular);
    }

    public List<Celular> getAllCelulares() {
        return celularRepository.findAll();
    }

    public boolean updateCelular(Celular celular, String id) {
        return celularRepository.update(celular);
    }

    public boolean deleteCelular(String id) {
        return celularRepository.delete(id);
    }

    public List<Celular> searchCelulares(String nombre, String marca, String ram, String almacenamiento,
                                         String color, String sim, Integer cantidad) {
        // simplificado, solo retorna todo
        return celularRepository.findAll();
    }
}
