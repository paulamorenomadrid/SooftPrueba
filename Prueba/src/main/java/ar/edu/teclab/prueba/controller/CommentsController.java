package ar.edu.teclab.prueba.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.xml.stream.events.Comment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.teclab.prueba.dto.Ticket;
import ar.edu.teclab.prueba.service.PruebaService;;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*")
public class CommentsController {

	private static final Log LOG = LogFactory.getLog(CommentsController.class);

	@Autowired
	protected PruebaService pruebaService;
	
	@GetMapping("/comments")
	@ResponseBody
	public ResponseEntity<String> getComments(@RequestParam(value ="id") String id) {
		try {
			URL url = new URL("https://teclab1593636133.zendesk.com/api/v2/tickets/" + id + "/comments");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			String userCredentials = "jorge.danni@teclab.edu.ar:Abril2019";
			String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
		    con.setRequestProperty("Accept", "application/json");
		    con.setRequestProperty ("Authorization", basicAuth);
			int responseCode = con.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				return ResponseEntity.ok(response.toString());
			} else {
				System.out.println("GET request not worked");
				return ResponseEntity.badRequest().body("Something went Wrong");
			}
		}catch (Exception e){
			LOG.error("Error", e);
		}
		return null;
	}
	
	@PutMapping(path = "/addComments", consumes = "application/json", produces = "application/json")
	public void addcomments(@RequestParam(value ="id") String id, @RequestBody Ticket ticket ) {
		try {
		URL url = new URL("https://teclab1593636133.zendesk.com/api/v2/tickets/"+ id);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		String userCredentials = "jorge.danni@teclab.edu.ar:Abril2019";
		String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
		String jsonString = "{\"ticket\": {\"comment\": {\"body\": \"" + ticket.getCommentBody() + "\",\"public\": true}}}";
		System.out.println(ticket.getCommentBody());
		con.setRequestMethod("PUT");
		con.setRequestProperty("Content-Type", "application/json");
	    con.setRequestProperty("Accept", "application/json");
	    con.setRequestProperty ("Authorization", basicAuth);
	    con.setDoOutput(true);
	    
	    try(OutputStream os = con.getOutputStream()) {
	        byte[] input = jsonString.getBytes("utf-8");
	        os.write(input, 0, input.length);			
	    }
	    
	    try(BufferedReader br = new BufferedReader(
	    		  new InputStreamReader(con.getInputStream(), "utf-8"))) {
	    		    StringBuilder response = new StringBuilder();
	    		    String responseLine = null;
	    		    while ((responseLine = br.readLine()) != null) {
	    		        response.append(responseLine.trim());
	    		    }
	    		    System.out.println(response.toString());
	    		}
	    int responseCode = con.getResponseCode();
	    System.out.println("GET Response Code :: " + responseCode);
	    
	    if (responseCode == HttpURLConnection.HTTP_OK) { // success
	    	System.out.println("PUT worked");
	    	
	    }else {
			System.out.println("PUT request not worked");
	    }
	    
		}catch (Exception e){
		LOG.error("Error", e);
		}	
	}
}



