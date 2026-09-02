package com.sleepstageai.identity.user;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UserAccountRepository {
    private final EntityManager entityManager;

    public UserAccountRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void save(UserAccount userAccount){
        entityManager.persist(userAccount); //insert
    }

    public Optional<UserAccount> findByEmail(String email){
        return entityManager.createQuery(
                "SELECT account FROM UserAccount account WHERE account.email = :email", //JPQL
                        UserAccount.class
                ).setParameter("email", email).getResultStream().findFirst();
    }

    public boolean existsByEmail(String email){
        Long matchingAccountCount = entityManager.createQuery(
                "SELECT COUNT(account) FROM UserAccount account WHERE account.email = :email",
                        Long.class).setParameter("email", email).getSingleResult();
        return matchingAccountCount > 0;
    }


}
