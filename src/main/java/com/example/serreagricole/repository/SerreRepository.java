package com.example.serreagricole.repository;

import com.example.serreagricole.entitie.Serre;
import com.example.serreagricole.entitie.SerreDTO;
import com.example.serreagricole.entitie.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface SerreRepository extends JpaRepository<Serre,Integer> {
    List<Serre> findByUser(User user);

    @Query("SELECT s FROM Serre s WHERE s.user.id = :userId AND s.serreId = :serreId ORDER BY s.date DESC")
    List<Serre> findLatestSerreDataByUserIdAndSerreId(@Param("userId") int userId, @Param("serreId") int serreId);

    @Query("SELECT DISTINCT s.serreId FROM Serre s WHERE s.user.id = :userId")
    List<Integer> findDistinctSerreIdsByUserId(@Param("userId") int userId);

    @Query("SELECT s FROM Serre s WHERE s.user.id=:userId AND s.serreId=:serreId")
    List<Serre>FindAllSerre(int userId, int serreId);

    @Query("SELECT DISTINCT s.etat FROM Serre s where s.user.id=:userId AND s.serreId=:serreId")
    List<String> getEtat(int userId, int serreId);
    @Query("SELECT NEW map(s.temperature as temperature, s.date as date) FROM Serre s WHERE s.user.id = :userId AND s.serreId = :serreId")
    List<Map<String, Object>> findTemperaturesAndDatesByUserAndSerreId(@Param("userId") int userId, @Param("serreId") int serreId);

    @Query("SELECT s.temperature FROM Serre s WHERE s.user.id=:userId AND s.serreId=:serreId ORDER BY s.date DESC")
    List<Float> Temperature(int userId ,int serreId);
    @Query("SELECT s.temperature FROM Serre s WHERE s.user.id=:userId AND s.serreId=:serreId")
    List<Float> recupererTemperature(int userId ,int serreId);
    @Query("SELECT s.humidite, s.date FROM Serre s WHERE s.user.id = :userId AND s.serreId = :serreId")
    List<Float> findHumiditeByUser(int userId, int serreId);
    @Query("SELECT s.humiditeSol, s.date FROM Serre s WHERE s.user.id = :userId AND s.serreId = :serreId")
    List<Float> findHumiditeSolByUserId(int userId, int serreId);

    @Query("SELECT new com.example.serreagricole.entitie.SerreDTO(s.serreId, s.temperature, s.humidite, s.humiditeSol, s.niveauEau, s.date) " +
            "FROM Serre s WHERE s.user.id = :userId")
    List<SerreDTO> findSerreDataGroupedBySerreId(int userId);

    @Query("SELECT DISTINCT s.serreId FROM Serre s WHERE s.user.id = :userId")
    List<Integer> findIdSerreByUser(@Param("userId") int userId);

    @Query("SELECT DISTINCT s.serreId from Serre s")
    List<Integer> findAllSerreId();
    List<Serre >findBySerreId(int serreId);
}
