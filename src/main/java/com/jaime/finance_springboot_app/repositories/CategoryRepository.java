package com.jaime.finance_springboot_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jaime.finance_springboot_app.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    //Lo que hace esta query es buscar una categoría por su nombre, 
    //sin importar si está en mayúsculas o minúsculas (se usa PSQL no SQL)
    @Query("SELECT c FROM Category c WHERE UPPER(c.name) = UPPER(:name)")
    Category findByName(@Param("name") String name);
    // En sql seria: SELECT * FROM categories category WHERE UPPER(category.name) = UPPER(:name);
}