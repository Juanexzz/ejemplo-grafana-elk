package co.edu.udem.ejemplografana.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EjemploController {
    Logger logger = LoggerFactory.getLogger(EjemploController.class);

    @GetMapping("/saludo")
    public String saludar(){
        logger.info("Saludando");
        return "Hola";
    }

    @GetMapping("/despedida")
    public String despedida(){
        logger.error("Despidiendo");
        return "Adiós";
    }
}

