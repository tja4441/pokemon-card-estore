package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.StoreStatistic;
import com.estore.api.estoreapi.model.UserStatistic;
import com.estore.api.estoreapi.persistence.StatisticsDao;

@RestController
@RequestMapping("stats")
public class StatisticsController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private StatisticsDao statsDao;

    public StatisticsController(StatisticsDao statsDao) {
        this.statsDao = statsDao;
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserStatistic> createUserStats(@PathVariable int id, @RequestBody String username) {
        LOG.info("POST /stats/" + id);
        try {
            UserStatistic userStats = statsDao.createUserStats(id, username);
            if (userStats != null) {
                return new ResponseEntity<UserStatistic>(userStats, HttpStatus.CREATED);
            }
            else{ 
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<UserStatistic[]> getAllUserStats() {
        LOG.info("GET /stats");

        try {
            UserStatistic[] userStats = statsDao.getUserStats();
            if(userStats != null) {
                return new ResponseEntity<>(userStats, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/store")
    public ResponseEntity<StoreStatistic> getStoreStats() {
        LOG.info("GET /stats/store");

        try {
            StoreStatistic storeStats = statsDao.getStoreStatistic();
            if(storeStats != null) {
                return new ResponseEntity<>(storeStats, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserStatistic> updateUserStats(@PathVariable int id, @RequestBody ShoppingCart cart) {
        LOG.info("PUT /stats/" + id);

        try {
            UserStatistic s = statsDao.updateUserStatistic(id, cart);
            if (s != null){
            return new ResponseEntity<UserStatistic>(s,HttpStatus.OK);
            }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/sessionData/{id}")
    public ResponseEntity<UserStatistic> updateUserSessionData(@PathVariable int id, @RequestBody float sessionTime) {
        LOG.info("PUT /stats/sessionData/" + id);

        try {
            UserStatistic s = statsDao.updateUserSessionData(id, sessionTime);
            if (s != null){
            return new ResponseEntity<UserStatistic>(s,HttpStatus.OK);
            }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/store")
    public ResponseEntity<StoreStatistic> updateStoreStatistics(@RequestBody ShoppingCart cart) {
        LOG.info("PUT /stats/store");

        try {
            StoreStatistic s = statsDao.updateStoreStatistic(cart);
            if (s != null){
            return new ResponseEntity<StoreStatistic>(s,HttpStatus.OK);
            }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/store/sessionData/{sessionTime}")
    public ResponseEntity<StoreStatistic> updateStoreSessionData(@PathVariable float sessionTime) {
        LOG.info("PUT /stats/sessionData/store/" + sessionTime);

        try {
            StoreStatistic s = statsDao.updateStoreSessionData(sessionTime);
            if (s != null){
            return new ResponseEntity<StoreStatistic>(s,HttpStatus.OK);
            }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
