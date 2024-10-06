package com.example.demo.services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.model.Contrasena;
import com.example.demo.repository.ContrasenaRepository;

@Service
public class ContrasenaService {

    @Autowired 
    private ContrasenaRepository repository;
    
   

    // Lógica existente de CRUD

    public Contrasena guardarContrasena(Contrasena contrasena) {
        Contrasena contra = repository.save(contrasena);
        return contra;
    }

    public String asignarComision(int n_sales) {
        return repository.asignarComisionRepository(n_sales);
    }

    public ResponseEntity<Contrasena> leerUsuario(Long id) {
        Optional<Contrasena> user = repository.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Contrasena>> obtenerUsuarios() {
        List<Contrasena> users = repository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<Contrasena> actualizarUsuario(Long id, Contrasena update) {
        Optional<Contrasena> existentUser = repository.findById(id);
        if (existentUser.isPresent()) {
            Contrasena user = existentUser.get();
            user.setPassword(update.getPassword());
            user.setUsername(update.getUsername());
            Contrasena updateUser = repository.save(user);
            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Contrasena> eliminarUsuario(Long id) {
        Optional<Contrasena> existentUser = repository.findById(id);
        if (existentUser.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    //Ejecutar código PL/SQL
    
    @Autowired
    private JdbcTemplate jdbcTemplate;  // Usa JdbcTemplate para ejecutar PL/SQL

    // Método para llamar a la función PL/SQL 'n_ejemplo'
    public String obtenerComision(int n_sales) {
        String sql = "{? = call n_ejemplo(?)}";  // Llamada a la función en PL/SQL

        return jdbcTemplate.execute((Connection connection) -> {
            CallableStatement callableStatement = connection.prepareCall(sql);
            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);  // El tipo de retorno es VARCHAR2
            callableStatement.setInt(2, n_sales);  // Parámetro de entrada: n_sales

            callableStatement.execute();  // Ejecutar la función PL/SQL
            return callableStatement.getString(1);  // Retornar el resultado de la función
        });
    }
    

    // Método para llamar a la función PL/SQL 'asignar_ranking'
    public String obtenerRanking(String c_grade) {
        String sql = "{? = call asignar_ranking(?)}";  // Llamada a la función PL/SQL

        return jdbcTemplate.execute((Connection connection) -> {
            CallableStatement callableStatement = connection.prepareCall(sql);
            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);  // Tipo de retorno: VARCHAR2
            callableStatement.setString(2, c_grade);  // Parámetro de entrada: c_grade

            callableStatement.execute();  // Ejecutar la función PL/SQL
            return callableStatement.getString(1);  // Retornar el resultado de la función
        });
    }
    
}
