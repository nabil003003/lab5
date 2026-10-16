package com.example.repository;

import com.example.model.Salle;
import com.example.model.Reservation;
import com.example.repository.SalleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

@Stateless
public class SalleRepositoryImpl implements SalleRepository {

    @PersistenceContext(unitName = "SallePU")
    private EntityManager entityManagerS;

    public void setEntityManagerS(EntityManager entityManagerS) {
        this.entityManagerS = entityManagerS;
    }

    @Override
    public List<Salle> findAvailableRooms(LocalDateTime debut, LocalDateTime fin) {
        String jpql = "SELECT s FROM Salle s WHERE s.idS NOT IN (" +
                      "SELECT r.salleR.idS FROM Reservation r WHERE (:debut < r.finR AND :fin > r.debutR))";
        return entityManagerS.createQuery(jpql, Salle.class)
                .setParameter("debut", debut)
                .setParameter("fin", fin)
                .getResultList();
    }

    @Override
    public List<Salle> searchRooms(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin, int page, int size) {
        return searchRooms(nom, capaciteMin, debut, fin, null, "nom", true, page, size);
    }

    @Override
    public List<Salle> searchRooms(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin, String equipement, String tri, boolean croissant, int page, int size) {
        CriteriaBuilder cb = entityManagerS.getCriteriaBuilder();
        CriteriaQuery<Salle> cq = cb.createQuery(Salle.class);
        Root<Salle> salle = cq.from(Salle.class);
        List<Predicate> predicates = new ArrayList<>();

        if (nom != null && !nom.isEmpty()) {
            predicates.add(cb.like(cb.lower(salle.get("nomS")), "%" + nom.toLowerCase() + "%"));
        }
        if (equipement != null && !equipement.trim().isEmpty()) {
            predicates.add(cb.isMember(equipement.trim(), salle.get("equipementsS")));
        }
        if (capaciteMin != null) {
            predicates.add(cb.ge(salle.get("capaciteS"), capaciteMin));
        }
        if (debut != null && fin != null) {
            Subquery<Long> subquery = cq.subquery(Long.class);
            Root<Reservation> reservation = subquery.from(Reservation.class);
            subquery.select(reservation.get("salleR").get("idS"));
            subquery.where(cb.and(
                cb.lessThan(reservation.get("debutR"), fin),
                cb.greaterThan(reservation.get("finR"), debut)
            ));
            predicates.add(cb.not(salle.get("idS").in(subquery)));
        }

        cq.select(salle).where(cb.and(predicates.toArray(new Predicate[0])));
        String attributTri = "capacite".equals(tri) ? "capaciteS" : "nomS";
        Order ordre = croissant ? cb.asc(salle.get(attributTri)) : cb.desc(salle.get(attributTri));
        cq.orderBy(ordre, cb.asc(salle.get("idS")));
        TypedQuery<Salle> query = entityManagerS.createQuery(cq);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public long countRooms(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin) {
        return countRooms(nom, capaciteMin, debut, fin, null);
    }

    @Override
    public long countRooms(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin, String equipement) {
        CriteriaBuilder cb = entityManagerS.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Salle> salle = cq.from(Salle.class);
        List<Predicate> predicates = new ArrayList<>();

        if (nom != null && !nom.isEmpty()) {
            predicates.add(cb.like(cb.lower(salle.get("nomS")), "%" + nom.toLowerCase() + "%"));
        }
        if (equipement != null && !equipement.trim().isEmpty()) {
            predicates.add(cb.isMember(equipement.trim(), salle.get("equipementsS")));
        }
        if (capaciteMin != null) {
            predicates.add(cb.ge(salle.get("capaciteS"), capaciteMin));
        }
        if (debut != null && fin != null) {
            Subquery<Long> subquery = cq.subquery(Long.class);
            Root<Reservation> reservation = subquery.from(Reservation.class);
            subquery.select(reservation.get("salleR").get("idS"));
            subquery.where(cb.and(
                cb.lessThan(reservation.get("debutR"), fin),
                cb.greaterThan(reservation.get("finR"), debut)
            ));
            predicates.add(cb.not(salle.get("idS").in(subquery)));
        }

        cq.select(cb.count(salle)).where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManagerS.createQuery(cq).getSingleResult();
    }
}
