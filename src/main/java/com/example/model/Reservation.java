package com.example.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idR;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_id", nullable = false)
    private Salle salleR;

    @Column(nullable = false)
    private LocalDateTime debutR;

    @Column(nullable = false)
    private LocalDateTime finR;

    // Getters et setters

    public Long getIdR() { return idR; }
    public void setIdR(Long idR) { this.idR = idR; }

    public Salle getSalleR() { return salleR; }
    public void setSalleR(Salle salleR) { this.salleR = salleR; }

    public LocalDateTime getDebutR() { return debutR; }
    public void setDebutR(LocalDateTime debutR) { this.debutR = debutR; }

    public LocalDateTime getFinR() { return finR; }
    public void setFinR(LocalDateTime finR) { this.finR = finR; }
}
