package main.java.customer.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import main.java.customer.entity.Customer;
import main.java.customer.entity.Manufacturer;
import main.java.customer.entity.Product;
import main.java.customer.entity.ShoppingCart;
import main.java.customer.services.ProductService;


public class ShoppingCartService {

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

    factory = cfg.configure()
        .buildSessionFactory();
    Session session = factory.openSession();
    return session;

  }

  public static void initService() {

    Session session = init();
    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      // Date date = new Date(System.currentTimeMillis());
      // user.setCreatedOn(date);
      //
      // session.save(user);

      tx.commit();
      session.close();

    } catch (Exception e) {
      tx.rollback();
      System.out.println(e);
    }

  }

  
  // d. Create shopping cart and add product to shopping cart
  public static Response createCart(Integer customerId, ShoppingCart cart) {

    Session session = init();
    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      Customer customer = session.get(Customer.class, customerId);  

      if (customer == null) {                               
        return Response.noContent().build();

      }

      cart.setCreatedOn(new Date(System.currentTimeMillis()));
      cart.setCustomer(customer);
      
      List<Product> products = getProducts(cart.getProducts()); 
      
      cart.setProducts(products);

      session.save(cart);

      tx.commit();
      session.close();

      return Response.ok(cart, MediaType.APPLICATION_JSON).build();  

    } catch (Exception e) {
      tx.rollback();
      System.out.println(e);
      return Response.notModified().build();   
    }

  }

  //Lista na produkti sto se veke iskreirani vo baza, t.e. veke postojat 
  private static List<Product> getProducts(List<Product> products) {   
    
    Session session = init();
    Transaction tx = null;

    List<Product> res = new ArrayList<Product>();
    
    try {

      session = factory.openSession();
      tx = session.beginTransaction();
      
      for (Product product : products) {                          
    	  
        Query query = session.createQuery("FROM main.java.customer.entity.Product p WHERE p.id=:id ");
        query.setParameter("id", product.getId());
        
        Product p = (Product) query.getResultList().get(0);
        
        res.add(p);  
        
      }
      
      
      tx.commit();
      session.close();
      
      return res;

    } catch (Exception e) {
      tx.rollback();
      System.out.println(e);
      return null;
    }
    
  }
  
  
  
     //e. Remove product from shopping cart- removeProductsFromSC(productid)
     public static void removeProductFromSC(Integer productID, ShoppingCart cartID) {
	    Session session = init();
	    Transaction tx = null;
		
		try {
			tx = session.beginTransaction();  
			
			Date createdOn = new Date(System.currentTimeMillis());
			
			ShoppingCart cart = GetCartByID(cartID.getId());
			
			
	    	Product product = GetProductByID(productID);
	    	
			
	    	List<Product> products = cart.getProducts(); //Get products from shopping cart into array
			
		
	    	
	    	for (Product cartProduct : products) {
				if(cartProduct.getId() == product.getId()) {
					products.remove(cartProduct);
					break;
				}
			}
				
			cart.setCreatedOn(createdOn);
			session.update(cart);
			
			
			tx.commit();
			session.close();
			
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}
		
	}
     
     // Get Product by Product ID
 	public static Product GetProductByID(Integer productId) {
 		Session session = init();
     	Transaction tx = null;
     	
 		try {
 			tx = session.beginTransaction();
 			
 			Query query = session.createQuery("SELECT p FROM main.java.customer.entity.Product p WHERE p.id=:id");
 			query.setParameter("id", productId);
 			
 			Product product = (Product) query.getResultList().get(0);
 			
 			tx.commit();
 			session.close();
 			
 			return product;
 			
 		} catch (Exception e) {
 			tx.rollback();
 			System.out.println(e);
 			return null;
 		}
 	}
 	
 	
 	// it is used in point e
 	//f. Get shopping cart by Cart ID
 	 	public static ShoppingCart GetCartByID(Integer cartID) {
 	 		 Session session = init();
 	     	Transaction tx = null;
 	     	
 	 		try {
 	 			tx = session.beginTransaction();
 	 			
 	 			Query query = session.createQuery("SELECT sc FROM main.java.customer.entity.ShoppingCart sc WHERE sc.id=:id");
 	 			query.setParameter("id", cartID);
 	 			
 	 			ShoppingCart cart = (ShoppingCart) query.getResultList().get(0);
 	 			
 	 			tx.commit();
 	 			session.close();
 	 			
 	 			return cart;
 	 			
 	 		} catch (Exception e) {
 	 			tx.rollback();
 	 			System.out.println(e);
 	 			return null;
 	 		}
 	 	}
 	
 	
 
 	
 
 	
	
	

}

