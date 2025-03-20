package org.rafs.tstedsin.Controller;

import org.rafs.tstedsin.Model.BeautyService;
import org.rafs.tstedsin.Service.BeautyServicesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/beauty-services")
public class BeautyServicesController {

    private final BeautyServicesService beautyServicesService;

    public BeautyServicesController(BeautyServicesService beautyServicesService) {
        this.beautyServicesService = beautyServicesService;
    }

    @GetMapping
    public ResponseEntity<List<BeautyService>> findAll(){
        return ResponseEntity.ok(beautyServicesService.findAll());
    }
}
