package com.gestionHopital.serv_utilisateur.gestionBatiment.bureau.service;

import com.gestionHopital.serv_utilisateur.gestionBatiment.bureau.model.Bureau;
import com.gestionHopital.serv_utilisateur.gestionBatiment.bureau.repository.BureauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BureauService {
    @Autowired
    private BureauRepository bureauRepository;

    public Bureau create(Bureau bureau){
        return bureauRepository.save(bureau);
    }

    public Bureau update(Bureau existing,Bureau update){
        if(existing.getNumero() != update.getNumero()){
            existing.setNumero(update.getNumero());
        }
        if(existing.getServiceF() != update.getServiceF()){
            existing.setServiceF(update.getServiceF());
        }
        return bureauRepository.save(existing);
    }

    public Bureau getBureau(Long id){
        Bureau bureau = bureauRepository.findById(id).get();
        Bureau bureau1 = new Bureau();
        bureau1.setId(bureau.getId());bureau1.setNumero(bureau.getNumero());
        bureau1.setService(bureau.getServiceF().getNom());
        return bureau1;
    }

    public List<Bureau> getBureaux(){
        List<Bureau> bureauList = bureauRepository.findAll();
        List<Bureau> bureauDTOList = new ArrayList<>();
        for(Bureau b : bureauList){
            Bureau bureauDTO = getBureau(b.getId());
            bureauDTOList.add(bureauDTO);
        }
        return bureauDTOList;
    }



    public void delete(Bureau bureau){
        bureauRepository.delete(bureau);
    }

    public List<Bureau> findAll(){
        return bureauRepository.findAll();
    }

    public Bureau findById(Long id){
        return bureauRepository.findById(id).get();
    }

}
