package ar.edu.teclab.prueba.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.teclab.prueba.dto.Ejemplo;
import ar.edu.teclab.prueba.dto.Comment;
import ar.edu.teclab.prueba.service.PruebaService;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*")
public class PruebaController {

	private static final Log LOG = LogFactory.getLog(PruebaController.class);

	@Autowired
	protected PruebaService pruebaService;

	@GetMapping("/ejemplo")
	public ResponseEntity<Ejemplo> getMessageStatus(@RequestParam(value ="nombre") String nombre) {
		try {
			Ejemplo ejemplo = new Ejemplo();
			ejemplo.setNombre(nombre);
			return ResponseEntity.ok(ejemplo);
		}catch (Exception e){
			LOG.error("Error", e);
		}
		return null;
	}
	
}


