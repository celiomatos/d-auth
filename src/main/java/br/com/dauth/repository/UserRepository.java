package br.com.dauth.repository;

import br.com.dauth.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findByUsername(String username);

    @Modifying
    @Query("update User t set t.password = ?1 where t.id = ?2")
    void updatePassword(String password, Integer userId);
}
