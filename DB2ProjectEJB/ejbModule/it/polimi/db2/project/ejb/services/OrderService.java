package it.polimi.db2.project.ejb.services;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import it.polimi.db2.project.ejb.entities.OrderEntity;
import it.polimi.db2.project.ejb.entities.ServicePackageEntity;
import it.polimi.db2.project.ejb.entities.UserEntity;

@Stateless
public class OrderService {
	@PersistenceContext(unitName = "DB2ProjectEJB")
	private EntityManager em;
	
	@EJB
    private UserService userService;

	public OrderEntity createOrder(OrderEntity order) throws SQLException {
		try {
			em.persist(order);
			em.flush();
			return order;
		} catch (ConstraintViolationException ignored) {
			return null;
		}
	}
	
	public Optional<OrderEntity> findOrderByID(int order_id) {
        return em.createNamedQuery("OrderEntity.findOrderById", OrderEntity.class)
            .setParameter("order_id", order_id)
            .getResultStream().findFirst();
    }
	
	public OrderEntity updateOrder (OrderEntity order, boolean isvalid){
        OrderEntity orderEntity = em.find(OrderEntity.class, order.getId());
        orderEntity.setIsvalid(isvalid);
        em.merge(orderEntity);
        em.flush();
        return orderEntity;
    }
	
	public List<OrderEntity> findFailedOrdersByUserId(int user_id){
        UserEntity user = userService.findUserById(user_id);
        return em.createNamedQuery("OrderEntity.findFailedOrdersByUserId", OrderEntity.class)
                .setParameter("user", user)
                .getResultList();
    }
	
	public List<OrderEntity> findOrderScheduledByUserId(int user_id){
        UserEntity user = userService.findUserById(user_id);
        return em.createNamedQuery("Order.findOrderScheduledByUserId", OrderEntity.class)
            .setParameter("user", user)
            .getResultList();
    }

}
