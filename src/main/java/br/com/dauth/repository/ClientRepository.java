package br.com.dauth.repository;

import br.com.dauth.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    Client findByClientId(String clientId);

}
