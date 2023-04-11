package projet.jee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.jee.entity.Abonnement;
import projet.jee.repository.AbonnementRepository;

import java.util.List;
import java.util.Optional;
@Service
public class AbonnementServiceImpl implements AbonnementService{
    @Autowired
    private AbonnementRepository abonneRepository;
    @Override
    public Abonnement saveAbonne(Abonnement abonne) {
        return abonneRepository.save(abonne);
    }
    @Override
    public List<Abonnement> fetchAbonneList(){
        return abonneRepository.findAll();
    }

    @Override
    public Optional<Abonnement> fetchAbonneById(Long id) {
        return abonneRepository.findById(id);
    }
}
