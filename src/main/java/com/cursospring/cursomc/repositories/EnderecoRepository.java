package com.cursospring.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursospring.cursomc.domain.Endereco;


@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
