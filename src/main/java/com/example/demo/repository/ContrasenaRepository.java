package com.example.demo.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Contrasena;

@Repository
public interface ContrasenaRepository extends JpaRepository<Contrasena, Long> {
	 @Query(value = "SELECT asignar_comision(:n_sales) FROM dual", nativeQuery = true)
	 String asignarComisionRepository(@Param("n_sales") int n_sales);
}
