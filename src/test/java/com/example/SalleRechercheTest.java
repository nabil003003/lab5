package com.example;

import com.example.model.Reservation;
import com.example.model.Salle;
import com.example.service.SalleServiceImpl;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class SalleRechercheTest extends TestCase {
    public void testEquipementsTriEtPagination() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SallePU",
                Collections.singletonMap("javax.persistence.jdbc.url", "jdbc:h2:mem:recherche_test"));
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Salle alpha = creerSalle("Alpha", 30, "wifi");
            Salle beta = creerSalle("Beta", 10, "wifi");
            Salle gamma = creerSalle("Gamma", 20, "projecteur");
            em.persist(alpha);
            em.persist(beta);
            em.persist(gamma);

            LocalDateTime debut = LocalDateTime.of(2026, 10, 16, 9, 0);
            LocalDateTime fin = debut.plusHours(2);
            Reservation reservation = new Reservation();
            reservation.setSalleR(alpha);
            reservation.setDebutR(debut);
            reservation.setFinR(fin);
            em.persist(reservation);
            em.getTransaction().commit();
            em.clear();

            SalleServiceImpl service = new SalleServiceImpl();
            service.setEntityManager(em);

            List<Salle> resultats = service.searchRooms(null, null, null, null, "wifi", "capacite", true, 1, 10);
            assertEquals(2, resultats.size());
            assertEquals("Beta", resultats.get(0).getNomS());
            assertEquals("Alpha", resultats.get(1).getNomS());
            assertEquals(2L, service.countRooms(null, null, null, null, "wifi"));

            resultats = service.searchRooms(null, null, null, null, "wifi", "capacite", false, 1, 1);
            assertEquals(1, resultats.size());
            assertEquals("Alpha", resultats.get(0).getNomS());
            resultats = service.searchRooms(null, null, null, null, "wifi", "capacite", false, 2, 1);
            assertEquals("Beta", resultats.get(0).getNomS());

            resultats = service.searchRooms(null, null, null, null, null, "nom", false, 1, 10);
            assertEquals("Gamma", resultats.get(0).getNomS());
            assertEquals("Alpha", resultats.get(2).getNomS());

            resultats = service.searchRooms("be", 5, debut, fin, "wifi", "nom", true, 1, 10);
            assertEquals(1, resultats.size());
            assertEquals("Beta", resultats.get(0).getNomS());
            assertEquals(1L, service.countRooms(null, null, debut, fin, "wifi"));
            assertEquals(0L, service.countRooms(null, 40, null, null, "wifi"));
            assertEquals(0L, service.countRooms(null, null, null, null, "tableau"));
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
            emf.close();
        }
    }

    private Salle creerSalle(String nom, int capacite, String equipement) {
        Salle salle = new Salle();
        salle.setNomS(nom);
        salle.setCapaciteS(capacite);
        salle.getEquipementsS().add(equipement);
        return salle;
    }
}
