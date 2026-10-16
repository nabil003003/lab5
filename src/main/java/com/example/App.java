package com.example;

import com.example.model.Salle;
import com.example.service.SalleService;
import com.example.service.SalleServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class App {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SallePU");
        EntityManager em = emf.createEntityManager();

        SalleService salleService = new SalleServiceImpl();
        // Injection manuelle pour ce test
        ((SalleServiceImpl) salleService).setEntityManager(em);

        LocalDateTime debut = LocalDateTime.of(2024, 6, 1, 9, 0);
        LocalDateTime fin = LocalDateTime.of(2024, 6, 1, 11, 0);

        List<Salle> sallesDisponibles = salleService.getAvailableRooms(debut, fin);
        System.out.println("Salles disponibles entre " + debut + " et " + fin + ":");
        for (Salle salle : sallesDisponibles) {
            System.out.println("- " + salle.getNomS() + " (Capacité: " + salle.getCapaciteS() + ")");
        }

        em.close();
        emf.close();
    }
}
