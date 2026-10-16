package com.example.service;

import com.example.model.Salle;
import com.example.repository.SalleRepositoryImpl;
import com.example.repository.SalleRepository;
import com.example.service.SalleService;

import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class SalleServiceImpl implements SalleService {

    @Inject
    private SalleRepository salleRepositoryS;

    public void setEntityManager(EntityManager entityManager) {
        SalleRepositoryImpl repository = new SalleRepositoryImpl();
        repository.setEntityManagerS(entityManager);
        this.salleRepositoryS = repository;
    }

    @Override
    public List<Salle> getAvailableRooms(LocalDateTime debut, LocalDateTime fin) {
        // Ici on pourrait ajouter des règles métier avant d'appeler le repository
        return salleRepositoryS.findAvailableRooms(debut, fin);
    }

    @Override
    public List<Salle> searchRooms(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin, int page, int size) {
        return salleRepositoryS.searchRooms(nom, capaciteMin, debut, fin, page, size);
    }

    @Override
    public long countRooms(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin) {
        return salleRepositoryS.countRooms(nom, capaciteMin, debut, fin);
    }
    @Override
    public List<Salle> searchRooms(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin, String equipement, String tri, boolean croissant, int page, int size) {
        return salleRepositoryS.searchRooms(nom, capaciteMin, debut, fin, equipement, tri, croissant, page, size);
    }

    @Override
    public long countRooms(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin, String equipement) {
        return salleRepositoryS.countRooms(nom, capaciteMin, debut, fin, equipement);
    }
}
