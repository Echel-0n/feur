package projet.jee.service;

import projet.jee.entity.Abonnement;

import java.util.List;
import java.util.Optional;

public interface AbonnementService {
    public Abonnement saveAbonne(Abonnement abonne);

    public List<Abonnement> fetchAbonneList();
    public Optional<Abonnement> fetchAbonneById(Long id);
}
