package ru.gb.spring_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.spring_test.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
