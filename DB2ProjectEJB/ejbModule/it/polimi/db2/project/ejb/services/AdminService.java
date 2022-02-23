package it.polimi.db2.project.ejb.services;

import it.polimi.db2.project.ejb.entities.AdminEntity;
import it.polimi.db2.project.ejb.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class AdminService {
    @PersistenceContext(unitName = "DB2ProjectEJB")
    private EntityManager em;

    /**
     * Checks admin credentials against those saved in the database.
     *
     * @param username the username of the admin.
     * @param password the password of the admin.
     * @return the {@code AdminEntity} linked to the username and password if the admin is found and password matches, {@code null} otherwise.
     * @throws CredentialsException     when the connection with the database fails or when there are more than one admin registered with same credentials.
     * @throws NonUniqueResultException when there are more than one admin registered with same credentials.
     */
    public AdminEntity checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
        List<AdminEntity> uList;

        try {
            uList = em.createNamedQuery("AdminEntity.checkCredentials", AdminEntity.class)
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
}
