package ar.edu.teclab.prueba.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.teclab.prueba.dto.Carrera;
import ar.edu.teclab.prueba.service.CarreraService;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*")
public class CarreraController {

	private static final Log LOG = LogFactory.getLog(CarreraController.class);

	@Autowired
	private CarreraService carreraService;

	@PostMapping(value = "/add")
    public ResponseEntity<Carrera> addCarrera(@RequestParam(value ="nombre") String nombre, @RequestParam(value ="cantMaterias") int cantMaterias) {
		Carrera carrera = new Carrera();
		carrera.setNombre(nombre);
		carrera.setCantMaterias(cantMaterias);
		carreraService.saveCarrera(carrera);
    return new ResponseEntity<Carrera>(carrera, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/update")
	public void updateCarrera(@RequestParam(value ="id") int id) {
		carreraService.updateById(id);
	}
	
	@DeleteMapping(value="/delete")
	public void deleteCarrera(@RequestParam(value ="id") int id) {
		carreraService.deleteById(id);
	}

}
	



