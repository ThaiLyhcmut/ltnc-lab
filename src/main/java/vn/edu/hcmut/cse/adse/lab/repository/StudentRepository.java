package vn.edu.hcmut.cse.adse.lab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmut.cse.adse.lab.entity.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {
    Page<Student> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);
    Optional<Student> findByEmail(String email);
}
