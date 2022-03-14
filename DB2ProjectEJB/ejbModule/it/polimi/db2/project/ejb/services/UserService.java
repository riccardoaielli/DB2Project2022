package it.polimi.db2.project.ejb.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.project.ejb.entities.UserEntity;
import it.polimi.db2.project.ejb.exceptions.CredentialsException;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "DB2ProjectEJB")
    private EntityManager em;

    public UserEntity findUserById(int userId) {
        return em.find(UserEntity.class, userId);
    }

    public UserEntity findUserByUsername(String username) {
        return em.createNamedQuery("UserEntity.findByUsername", UserEntity.class)
                .setParameter("username", username)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public UserEntity findUserByEmail(String email) {
        return em.createNamedQuery("UserEntity.findByEmail", UserEntity.class)
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Checks user credentials against those saved in the database.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     * @return the {@code UserEntity} linked to the username and password if the user is found and password matches, {@code null} otherwise.
     * @throws CredentialsException     when the connection with the database fails or when there are more than one user registered with same credentials.
     * @throws NonUniqueResultException when there are more than one user registered with same credentials.
     */
    public UserEntity checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
        List<UserEntity> uList;

        try {
            uList = em.createNamedQuery("UserEntity.checkCredentials", UserEntity.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentals");
        }

        if (uList.isEmpty()) {
            return null;
        } else if (uList.size() == 1) {
            return uList.get(0);
        }

        throw new NonUniqueResultException("More than one user registered with same credentials.");
    }


    public UserEntity addNewUser(String username, String password, String email) throws CredentialsException {
        if (findUserByUsername(username) != null) {
            throw new CredentialsException("Username already in use!");
        }

        if (findUserByEmail(email) != null) {
            throw new CredentialsException("Email already in use!");
        }

        UserEntity newUser = new UserEntity(username, password, email, false);
        em.persist(newUser);

        return newUser;
    }


//    public void blockUser(int userId) {
//        UserEntity user = em.find(UserEntity.class, userId);
//        user.setIsBlocked((byte) 1);
//
//        em.merge(user);
//    }

}