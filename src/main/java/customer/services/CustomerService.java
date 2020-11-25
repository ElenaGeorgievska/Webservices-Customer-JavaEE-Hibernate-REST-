package main.java.customer.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import main.java.customer.entity.Address;
import main.java.customer.entity.Customer;



public class CustomerService {

	static SessionFactory factory;

	public static Session init() {

		Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(main.java.customer.entity.Address.class);
		cfg.addAnnotatedClass(main.java.customer.entity.Customer.class);
		cfg.addAnnotatedClass(main.java.customer.entity.Manufacturer.class);
		cfg.addAnnotatedClass(main.java.customer.entity.Product.class);
		cfg.addAnnotatedClass(main.java.customer.entity.ShoppingCart.class);
		cfg.addAnnotatedClass(main.java.customer.entity.Category.class);
		cfg.configure();

		factory = cfg.configure().buildSessionFactory();
		Session session = factory.openSession();
		return session;

	}

	// a. Create customer
	public static void createCustomer(Customer customer) {

		Session session = init();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();

			Address address = new Address(customer.getAddress().getCity(),customer.getAddress().getStreet(),customer.getAddress().getZip());
			session.save(address);
			
			customer.setAddress(address);
			session.save(customer);

			tx.commit();
			session.close();

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

	}
	
	//a. Update customer
	public static void updateCustomer(Customer customer) {
		Session session = init();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			Customer oldCustomer = session.get(Customer.class, customer.getId());
			oldCustomer.setFirstName(customer.getFirstName());
			oldCustomer.setLastName(customer.getLastName());
			oldCustomer.setAge(customer.getAge());
			
			Address oldAddress = session.get(Address.class, customer.getAddress().getId());
			
			oldAddress.setCity(customer.getAddress().getStreet());
			oldAddress.setStreet(customer.getAddress().getCity());
			oldAddress.setZip(customer.getAddress().getZip());
			
			session.save(oldCustomer);
			session.save(oldAddress);
			
			tx.commit();
			session.close();
			
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}
	}
	
	
	//a. Delete customer
	public static void deleteCustomer(Customer customer) {
		Session session = init();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			Customer dbCustomer = session.get(Customer.class, customer.getId());
			session.delete(dbCustomer);
			
			tx.commit();
			session.close();
			
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}
	
	}
	

}
