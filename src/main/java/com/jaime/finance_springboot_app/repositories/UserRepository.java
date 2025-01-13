package com.jaime.finance_springboot_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jaime.finance_springboot_app.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(value = "ALTER SEQUENCE users_id_seq RESTART WITH 1", nativeQuery = true) 
    void resetAutoIncrement();

    //⬆️ Todo esto nos permite resetear el autoincremental de la tabla users
}
