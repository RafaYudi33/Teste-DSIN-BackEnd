package org.rafs.tstedsin.Service;

import org.rafs.tstedsin.Model.BeautyService;
import org.rafs.tstedsin.Repository.BeautyServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeautyServicesService {

    private final BeautyServiceRepository beautyServiceRepository;

    public BeautyServicesService(BeautyServiceRepository beautyServiceRepository) {
        this.beautyServiceRepository = beautyServiceRepository;
    }

    public List<BeautyService> findAll(){
        return beautyServiceRepository.findAll();
    }
}
