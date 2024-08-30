package app.DAOS;

import app.entities.Package;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import org.hibernate.exception.ConstraintViolationException;

import javax.lang.model.UnknownEntityException;
import java.util.Set;
import java.util.stream.Collectors;

public class PackageDAO implements IDAO<Package> {
    EntityManagerFactory emf;

    public PackageDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Set<Package> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Package> query = em.createQuery("SELECT e FROM Package e", Package.class);
            Set<Package> result = query.getResultList().stream().collect(Collectors.toSet());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Package create(Package p) { //Persist a new package
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return p;
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation " + e.getMessage());
            return null;
        }
    }

    @Override
    public Package update(Package p) {
        try (EntityManager em = emf.createEntityManager()) {
            Package found = em.find(Package.class, p.getId());
            if (found == null) {
                throw new EntityNotFoundException();
            }
            em.getTransaction().begin();
            Package merged = em.merge(p);
            em.getTransaction().commit();
            return merged;
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation " + e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(Package p) {
        try (EntityManager em = emf.createEntityManager()) {
            Package found = em.find(Package.class, p.getId());
            if (found == null) {
                throw new EntityNotFoundException();
            }
            em.getTransaction().begin();
            em.remove(found);
            em.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation " + e.getMessage());
        }
    }

    public Package findByTrackingNumber(String trackingNumber) {
        Package found;
        try (EntityManager em = emf.createEntityManager()) {
            found = em.find(Package.class, trackingNumber);
            if (found == null) {
                throw new EntityNotFoundException();
            }
            found.getTrackingNumber();
            found.getSenderName();
            found.getReceiverName();
            found.getDeliveryStatus();
        } catch (UnknownEntityException e) {
            System.out.println("Unknown entity " + e.getMessage());
            return null;
        }
        return found;
    }

    public void updateDeliveryStatus(Package p, Package.DeliveryStatus newStatus) {
        try (EntityManager em = emf.createEntityManager()) {
            Package found = em.find(Package.class, p.getId());
            if (found == null) {
                throw new EntityNotFoundException();
            }
            em.getTransaction().begin();
            found.setDeliveryStatus(newStatus);
            em.getTransaction().commit();
        } catch (EntityNotFoundException e) {
            System.out.println("Entity not found " + e.getMessage());
        }
    }

}
