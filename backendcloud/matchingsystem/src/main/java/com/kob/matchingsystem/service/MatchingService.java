package com.kob.matchingsystem.service;

public interface MatchingService {
    String addPlayer(Integer userId, Integer rating, Integer boId);
    String removePlayer(Integer userId);

}
