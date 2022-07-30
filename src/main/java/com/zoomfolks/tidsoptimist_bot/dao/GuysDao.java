package com.zoomfolks.tidsoptimist_bot.dao;

import com.zoomfolks.tidsoptimist_bot.dao.entity.Guy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuysDao extends JpaRepository<Guy, Integer> {

    Optional<Guy> findByUsername(String username);

    List<Guy> findAllByUsername(String username);

}
