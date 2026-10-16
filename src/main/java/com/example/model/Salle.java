package com.example.model;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "salles")
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idS;

    @Column(nullable = false, unique = true)
    private String nomS;

    @Column(nullable = false)
    private int capaciteS;

    @ElementCollection
    @CollectionTable(name = "salle_equipements", joinColumns = @JoinColumn(name = "salle_id"))
    @Column(name = "equipement")
    private Set<String> equipementsS = new HashSet<>();

    @OneToMany(mappedBy = "salleR", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reservation> reservationsS;

    // Getters et setters

    public Long getIdS() { return idS; }
    public void setIdS(Long idS) { this.idS = idS; }

    public String getNomS() { return nomS; }
    public void setNomS(String nomS) { this.nomS = nomS; }

    public int getCapaciteS() { return capaciteS; }
    public void setCapaciteS(int capaciteS) { this.capaciteS = capaciteS; }

    public Set<String> getEquipementsS() { return equipementsS; }
    public void setEquipementsS(Set<String> equipementsS) { this.equipementsS = equipementsS; }

    public Set<Reservation> getReservationsS() { return reservationsS; }
    public void setReservationsS(Set<Reservation> reservationsS) { this.reservationsS = reservationsS; }
}
