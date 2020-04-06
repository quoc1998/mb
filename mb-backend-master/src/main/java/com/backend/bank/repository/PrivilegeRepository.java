package com.backend.bank.repository;

import com.backend.bank.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege,Integer> {
    Optional<Privilege> findByName(String name);
    Optional<Privilege> findByPrivilegeIdAndLocal(int id,String local);
    List<Privilege> findByLocal(String local);
    List<Privilege> findByLocalAndGroupRole(String local, String groupRole);
    List<Privilege> findByGroupRole(String groupRole);
    List<Privilege> findByPrivilegeId(int id);

}
