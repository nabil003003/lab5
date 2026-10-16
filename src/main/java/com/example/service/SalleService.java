package com.example.service;

import com.example.model.Salle;
import java.time.LocalDateTime;
import java.util.List;

public interface SalleService {
    List<Salle> getAvailableRooms(LocalDateTime debut, LocalDateTime fin);
    List<Salle> searchRooms(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin, int page, int size);
    List<Salle> searchRooms(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin, String equipement, String tri, boolean croissant, int page, int size);
    long countRooms(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin);
    long countRooms(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin, String equipement);
}
