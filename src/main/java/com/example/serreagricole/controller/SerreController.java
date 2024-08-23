package com.example.serreagricole.controller;

import com.example.serreagricole.entitie.*;
import com.example.serreagricole.service.SerreServiceImp;
import com.example.serreagricole.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/serre")
public class SerreController {
    @Autowired
    private UserServiceImp userService;

    @Autowired
    private SerreServiceImp serreService;


  @PostMapping("/login")
  @ResponseBody
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
      User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

      if (user != null) {
          // Si l'authentification réussit, retournez l'ID et le rôle de l'utilisateur
          LoginResponse response = new LoginResponse(user.getId(), user.getRole());
          return new ResponseEntity<>(response, HttpStatus.OK);
      } else {
          // Si l'authentification échoue, retournez un code d'erreur
          return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }
  }

//    @PostMapping("/checkTemperatureAndAlert/{serreId}/{userId}")
//    public ResponseEntity<List<Serre>> checkTemperatureAndAlert(@PathVariable int serreId, @PathVariable int userId) {
//        serreService.checkTemperatureAndAlert(serreId,userId);
//        Serre serre=serre.getEtat();
//        return new ResponseEntity<>(serre HttpStatus.OK);
//
//    }


//cette methode permet de recuperer le dernier ligne de la liste
    @GetMapping("/AllSerre/{userId}/{serreId}")
    public ResponseEntity<Serre> getLastSerre(@PathVariable("userId") int userId, @PathVariable("serreId") int serreId) {
        Serre lastSerre = serreService.getLastSerre(userId, serreId);
        if (lastSerre == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lastSerre);
    }

//cette methode permet de verifier la temperature et modifier l'attribue etat
    @PostMapping("/verifTemp/{userId}/{serreId}")
    public String verifiTemperature(@PathVariable("userId") int userId, @PathVariable("serreId") int serreId){
        serreService.verifTemperature(userId, serreId);

        // Attendre un peu pour laisser le temps au thread de faire son travail
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Récupérer l'état mis à jour de la serre
        List<String> etat = serreService.getEtat(userId,serreId);
        return etat.toString();
    }

    @GetMapping("/latest/{userId}")
    public Map<Integer, Serre> getLatestSerreDataByUser(@PathVariable int userId) {
        return serreService.getLatestSerreDataByUser(userId);
    }



    @PostMapping("/addUser")
    @ResponseBody
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PostMapping("/addSerre")
    @ResponseBody
    public void addSerre(@RequestBody Serre serre){
       serreService.addSerre(serre);
    }

    @PostMapping("/addUserToSerre/{idSerre}/{userId}")
    @ResponseBody
    public void addUserSerre(@PathVariable("idSerre") int idSerre , @PathVariable("userId") int userId){
        serreService.addUserWithSerre(idSerre,userId);
    }


    @GetMapping("/AllUser")
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }


    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable int userId) {
       return userService.getUserById(userId);
    }

    @GetMapping("/serre/findIdSerreByUser/{userId}")
    public List<Integer> findIdSerreByUser(@PathVariable int userId) {
        return serreService.findIdSerreByUser(userId);
    }

    @GetMapping("serre/findAllSerre")
    public List<Integer> getSerrebyadmin(){
      return serreService.getSerrebyAdmin();
    }

    @GetMapping("/serres/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getSerreDataGroupedBySerreId(@PathVariable int userId) {
        List<Map<String, Object>> serreData = serreService.getSerreDataGroupedBySerreId(userId);
        return new ResponseEntity<>(serreData, HttpStatus.OK);
    }

    // Endpoint pour ajouter une serre à un utilisateur
//    @PostMapping("/addSerre/{userId}")
//    public ResponseEntity<User> addSerreToUser(@PathVariable int userId, @RequestBody Serre serre) {
//        User user = userService.addSerreToUser(userId, serre);
//        return ResponseEntity.ok(user);
//
//    }


//cette methode permet d'afficher tous les element de la liste selon userId et serreId
    @GetMapping("/temperature/{userId}/{serreId}")
    public List<Map<String, Object>> findTemperaturesAndDatesByUserAndSerreId(@PathVariable int userId, @PathVariable int serreId) {
        return serreService.getTemperatureSerre(userId, serreId);
  }


    @GetMapping("/humidite/{userId}/{serreId}")
    public ResponseEntity<List<Float>> getHumiditeSerre(@PathVariable int userId, @PathVariable int serreId) {
        List<Float>humidite=serreService.getHumidite(userId,serreId);
        return ResponseEntity.ok(humidite);
    }

    @GetMapping("/humiditeSol/{userId}/{serreId}")
    public ResponseEntity<List<Float>> getHumiditeSolSerre(@PathVariable int userId, @PathVariable int serreId) {
        List<Float>humiditeSol=serreService.getHumiditeSol(userId,serreId);
        return ResponseEntity.ok(humiditeSol);
    }
}

