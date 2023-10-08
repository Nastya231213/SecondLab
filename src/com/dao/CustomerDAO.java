package com.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.models.Customers;

public class CustomerDAO<Customers> extends JpaDAO<Customers> implements GenericDAO<Customers> {
	public CustomerDAO(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Customers create(Customers newCustomer) {
		return super.create(newCustomer);
	}

	@Override
	public Customers update(Customers updatedCustomer) {
		return super.update(updatedCustomer);
	}

	@Override
	public Customers get(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Object id) {
		// TODO Auto-generated method stub

	}

	public Customers getCustomerByEmail(String email) {
		List<Customers> listOfCustomers = (List<Customers>) super.findWithNamedQuery("Customers.findByEmail",
				"emailParam", email);
		if (listOfCustomers.size() == 0) {
			return null;
		} else {
			return (Customers) listOfCustomers.get(0);
		}

	}

	@Override
	public List<Customers> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

}