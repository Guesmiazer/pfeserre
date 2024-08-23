package com.example.serreagricole.service;

import com.example.serreagricole.entitie.Serre;
import com.example.serreagricole.entitie.SerreDTO;
import com.example.serreagricole.entitie.User;
import com.example.serreagricole.repository.SerreRepository;
import com.example.serreagricole.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SerreServiceImp {
        @Autowired
        private SerreRepository serreRepository;

        @Autowired
        private UserRepository userRepository;


        public void addSerre(Serre serre) {
            serreRepository.save(serre);
            List<Serre> serres = serreRepository.findBySerreId(serre.getSerreId());

            for (Serre serree : serres) {
                User u = serree.getUser();
                if (u != null) {
                    serre.setUser(u);
                    serreRepository.saveAll(serres);
                }
            }
        }
   public List<Integer> getSerrebyAdmin(){
            return serreRepository.findAllSerreId();
   }




//    public List<Serre> getAllSerres() {
//        return (List<Serre>) serreRepository.findAll();
//    }
    // fin
public Map<Integer, Serre> getLatestSerreDataByUser(int userId) {
    List<Integer> serreIds = serreRepository.findDistinctSerreIdsByUserId(userId);
    Map<Integer, Serre> latestSerreDataMap = new HashMap<>();

    for (int serreId : serreIds) {
        List<Serre> serreDataList = serreRepository.findLatestSerreDataByUserIdAndSerreId(userId, serreId);
        if (!serreDataList.isEmpty()) {
            latestSerreDataMap.put(serreId, serreDataList.get(0));  // Get the latest entry for each serreId
        }
    }

    return latestSerreDataMap;
}





public List<Serre> getAllSerres(int userId, int serreId) {
    return serreRepository.FindAllSerre(userId, serreId);
}

    public Serre getLastSerre(int userId, int serreId) {
        List<Serre> serres = serreRepository.FindAllSerre(userId, serreId);
        if (serres == null || serres.isEmpty()) {
            return null; // ou lancer une exception si approprié
        }
        return serres.get(serres.size() - 1); // Retourne le dernier élément de la liste
    }














    public List<Serre> getSerresByUtilisateur(User utilisateur) {
            return serreRepository.findByUser(utilisateur);
        }

//        public Serre getDonneesSerre(int userid, int serreId) {
//            User utilisateur = userRepository.findById(userid)
//                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
//
//            return serreRepository.findByIdAndUser(serreId, utilisateur)
//                    .orElseThrow(() -> new RuntimeException("Serre non trouvée"));
//        }
        public List<Map<String, Object>> getTemperatureSerre(int userId, int serreId) {
            return serreRepository.findTemperaturesAndDatesByUserAndSerreId(userId, serreId);
        }
        public List<Integer> findIdSerreByUser(int userId) {
                return serreRepository.findIdSerreByUser(userId);}


    public List<Map<String, Object>> getSerreDataGroupedBySerreId(int userId) {
        List<SerreDTO> serreDataList = serreRepository.findSerreDataGroupedBySerreId(userId);
        Map<Integer, List<Map<String, Object>>> groupedData = new HashMap<>();

        // Group data by serreId
        for (SerreDTO serreData : serreDataList) {
            if (!groupedData.containsKey(serreData.getSerreId())) {
                groupedData.put(serreData.getSerreId(), new ArrayList<>());
            }
            Map<String, Object> data = new HashMap<>();
            data.put("temperature", serreData.getTemperature());
            data.put("humidite", serreData.getHumidite());
            data.put("humiditeSol", serreData.getHumiditeSol());
            data.put("niveauEau", serreData.getNiveauEau());
            data.put("date", serreData.getDate());
            groupedData.get(serreData.getSerreId()).add(data);
        }

        // Convert grouped data to the desired format
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<Integer, List<Map<String, Object>>> entry : groupedData.entrySet()) {
            Map<String, Object> serreDataMap = new HashMap<>();
            serreDataMap.put("serreId", entry.getKey());
            serreDataMap.put("mesures", entry.getValue());
            result.add(serreDataMap);
        }

        return result;
    }
    public List<Float> getHumidite(int userid , int serreId){
            return serreRepository.findHumiditeByUser(userid,serreId);
        }
        public List<Float> getHumiditeSol(int userid,int serreId){
            return serreRepository.findHumiditeSolByUserId(userid,serreId);
        }

    public void addUserWithSerre(int serreId, int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            List<Serre> serres = serreRepository.findBySerreId(serreId);

            for (Serre serre : serres) {
                serre.setUser(user);
                serreRepository.saveAll(serres);

            }

        }  // Retourner toutes les serres associées à l'utilisateur après les avoir sauvegardées

    }
//        public float getHumiditeSerre(int userid, int serreId) {
//            Serre serre = getDonneesSerre(userid, serreId);
//            return serre.getHumidite();
//        }

//        public float getHumiditeSolSerre(int userid, int serreId) {
//            Serre serre = getDonneesSerre(userid, serreId);
//            return serre.getHumiditeSol();
//        }

    public List<String> getEtat(int userId, int serreId){
            return serreRepository.getEtat(userId,serreId);
    }
public Float getLatestTemperature(int userId, int serreId) {
    List<Float> temperatures = serreRepository.Temperature(userId, serreId);
    return temperatures.isEmpty() ? null : temperatures.get(0); // La liste est triée par date décroissante
}

    public String verifTemperature(int userId, int serreId) {
        float seuil = 70; // valeur seuil
        //Float latestTemperature = getLatestTemperature(userId, serreId);
        List<Serre> serres = serreRepository.findBySerreId(serreId);

        Runnable task = () -> {
            try {
                boolean probleme = false;
                for (int i = 0; i < 5; i++) { // Vérifie toutes les 5 secondes pour une durée totale de 25 secondes
                    Float latestTemperature = getLatestTemperature(userId, serreId);

                    if (latestTemperature == null) {
                        System.out.println("Impossible de récupérer la température.");
                        return;
                    }

                    if (latestTemperature > seuil ) {

                        for (Serre serre : serres) {
                            serre.setEtat("echec");
                            serreRepository.saveAll(serres);

                        }

                        probleme = true;
                        break;
                    }

                    Thread.sleep(5000); // Attendre 5 secondes
                }

                if (!probleme) {
                    for (Serre serre : serres) {
                        serre.setEtat("NULL");
                        serreRepository.saveAll(serres);

                    }

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(task).start();

        return "Vérification de la température en cours...";
    }




    public void checkTemperatureAndAlert(int userId, int serreId) {
        List<Float> temperatureData = serreRepository.recupererTemperature(userId, serreId);

        // Compteur pour suivre les secondes consécutives au-dessus de 50 degrés
        int consecutiveSecondsAboveThreshold = 0;

        // Parcourir les données de température
        for (Float temperature : temperatureData) {
            // Vérifier si la température est supérieure à 50 degrés
            if (temperature > 50) {
                consecutiveSecondsAboveThreshold++;
            } else {
                // Si la température est inférieure à 50 degrés, réinitialiser le compteur
                consecutiveSecondsAboveThreshold = 0;
            }

            // Vérifier si la température a dépassé 50 degrés pendant 5 secondes consécutives
            if (consecutiveSecondsAboveThreshold >= 5) {
                // Déclencher une alerte en définissant l'état de la serre sur "Problème au système de climatisation"
                Serre serre = serreRepository.findById(serreId).orElse(null);
                if (serre != null) {
                    serre.setEtat("Problème au système de climatisation");
                    serreRepository.save(serre);
                }
                // Sortir de la boucle une fois que l'alerte est déclenchée
                break;
            }
        }
        // Si la température est retombée en dessous de 50 degrés, réinitialiser l'état de la serre à `null`
        if (consecutiveSecondsAboveThreshold < 5) {
            Serre serre = serreRepository.findById(serreId).orElse(null);
            if (serre != null) {
                serre.setEtat(null);
                serreRepository.save(serre);
            }
        }
    }
}


