package hu.bme.mit.smartmobility.accountshielddevlab.repository;

import hu.bme.mit.smartmobility.accountshielddevlab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
