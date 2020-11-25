package main.java.customer.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import main.java.customer.entity.Address;
import main.java.customer.entity.Category;
import main.java.customer.entity.Manufacturer;
import main.java.customer.entity.Product;
import main.java.customer.entity.ProductResponse;
import main.java.customer.entity.ProductResponse2;
import main.java.customer.entity.ShoppingCart;




public class ProductService {

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

  // b. Create product
  //1 way)
  public static void createProduct(Product product) {

    Session session = init();
    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      Address address = new Address(product.getManufacturer().getAddress().getCity(),
    		 product.getManufacturer().getAddress().getStreet(),
          product.getManufacturer().getAddress().getZip());

      session.save(address);
		

      //proveruvame vo baza dali imame manufacturer so toa ime
      Manufacturer m = checkManufacturer(product.getManufacturer().getName());
      

      if (m == null) {                 //ako ne postoi takov manufacturer so toa ime vo baza

        Manufacturer man = new Manufacturer(product.getManufacturer().getName(), address);
        session.save(man);              //togas ovde ni se kreira nov manufacturer
        product.setManufacturer(man);
      } else {
        product.setManufacturer(m);     //a ako posti takov manufacturer togas samo setiraj mi go so m imeto od gore
      }
      
      Category c = checkCategory(product.getCategory().getProductCategory());
      
      if (c == null) {                 

          Category category = new Category(product.getCategory().getProductCategory());
          session.save(category);              
          product.setCategory(category);
        } else {
          product.setCategory(c);     
        }

      session.save(product);

      tx.commit();
      session.close();

    } catch (Exception e) {
      tx.rollback();
      System.out.println(e);
    }

  }
  
  
  //Proverka dali manufacturer veke postoi
  private static Manufacturer checkManufacturer(String name) {

    Session session = init();
    Transaction tx = null;

    try {

      session = factory.openSession();
      tx = session.beginTransaction();

      // get manufacotrer
      Query query = session.createQuery("FROM main.java.customer.entity.Manufacturer m WHERE m.name=:name ");
      query.setParameter("name", name);

      Manufacturer man = (Manufacturer) query.getResultList().get(0);

      tx.commit();
      session.close();
      return man;

    } catch (Exception e) {
      tx.rollback();
      System.out.println(e);
      return null;
    }

  }
  
  //Proverka dali category veke postoi
  private static Category checkCategory(String productCategory) {

    Session session = init();
    Transaction tx = null;

    try {

      session = factory.openSession();
      tx = session.beginTransaction();

      // get category
      Query query = session.createQuery("FROM main.java.customer.entity.Category c WHERE c.productCategory=:productCategory ");
      query.setParameter("productCategory", productCategory);

      Category cat = (Category) query.getResultList().get(0);

      tx.commit();
      session.close();
      return cat;

    } catch (Exception e) {
      tx.rollback();
      System.out.println(e);
      return null;
    }

  }
  
  //2way)
  //Create Product
/*	public static void createProduct(Product product) {
		 Session session = init();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			Address address = new Address(product.getManufacturer().getAddress().getCity(),
					product.getManufacturer().getAddress().getStreet(), product.getManufacturer().getAddress().getZip());
			session.save(address);
			
			Manufacturer manufacturer = new Manufacturer(product.getManufacturer().getName(),address);
			session.save(manufacturer);
			
			Category category = new Category(product.getCategory().getProductCategory());
			session.save(category);
			
			
			
			
			Product createProduct = new Product(product.getName(), product.getPrice(), manufacturer,category, product.getOrigin());
			session.save(createProduct);
			
			tx.commit();
			session.close();
			
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}
	} */
  
  
  //b. Update product
  public static void updateProduct(Product product) {
	    Session session = init();
	    Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			Product oldProduct = session.get(Product.class, product.getId());
			
			oldProduct.setName(product.getName());
			oldProduct.setPrice(product.getPrice());
			oldProduct.setOrigin(product.getOrigin());
			
			Manufacturer oldManufacturer = session.get(Manufacturer.class, product.getManufacturer().getId());
			oldManufacturer.setName(product.getManufacturer().getName());
					
			
			Address oldAddress = session.get(Address.class, product.getManufacturer().getAddress().getId());
			
			oldAddress.setCity(product.getManufacturer().getAddress().getCity());
			oldAddress.setStreet(product.getManufacturer().getAddress().getStreet());				
			oldAddress.setZip(product.getManufacturer().getAddress().getZip());
					
			Category oldCategory = session.get(Category.class, product.getCategory().getId());
			oldCategory.setProductCategory(product.getCategory().getProductCategory());
			
			
			session.save(oldProduct);
			session.save(oldManufacturer);
			session.save(oldAddress);
			session.save(oldCategory);
			
			tx.commit();
			session.close();
			
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}
  }
  
  //b. Delete product
  public static void deleteProduct(Product product) {
	    Session session = init();
	    Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			Product dbProduct = session.get(Product.class, product.getId());
			session.delete(dbProduct);
			
			tx.commit();
			session.close();
			
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}
  }
  
  
  /*  //c. Get All Products / View all available products
	public static List<Product> getAllProducts() {
		Session session = init();
	    Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			Query query = session.createQuery("FROM main.java.customer.entity.Product");
			List<Product> products = query.getResultList();
			
			tx.commit();
			session.close();
			
			return products;
			
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
			return null;
		}
	}*/
	
	//g. Get All products by Category (in custom response to be only product name, price and category)
	// category should be in url
	public static List<ProductResponse> getAllProductsByCategory(Integer categoryID) {

		Session session = init();
		Transaction tx = null;

		List<ProductResponse> result = new ArrayList<>();

		try {

			tx = session.beginTransaction();			
		
			Query query = session.createQuery("SELECT p FROM main.java.customer.entity.Product p JOIN p.category c WHERE c.id=:categoryID");
			query.setParameter("categoryID", categoryID);
			

			List<Product> products = query.getResultList();

			for (Product product : products) {

				ProductResponse response = new ProductResponse();
				response.setName(product.getName());
				response.setPrice(product.getPrice());
				response.setCategory(product.getCategory().getProductCategory());
				
				result.add(response);

			}

			tx.commit();
			session.close();

			return result;
			
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

		return null;
	}
	
	
	//h.Get all products by Origin
  /*   public static List<Product> getAllProductsByOrigin(String origin) {
			Session session = init();
		    Transaction tx = null;
			
			try {
				tx = session.beginTransaction();
				
				Query query = session.createQuery("FROM main.java.customer.entity.Product p WHERE p.origin=:origin");
				query.setParameter("origin", origin);
				
				List<Product> products = query.getResultList();
				
				tx.commit();
				session.close();
				
				return products;
				
			} catch (Exception e) {
				tx.rollback();
				System.out.println(e);
				return null;
			}
		}*/
	
	
	 //i. BONUS: Get all products with macedonian origin with the new Price discounted by 18% and the old Price
	//1) way  origin is in the URL
	 public static List<ProductResponse2> getAllProductsByDiscount(String origin) {
			Session session = init();
		    Transaction tx = null;
		    
		    List<ProductResponse2> result = new ArrayList<>();
		    
			try {
				tx = session.beginTransaction();
				
				Query query = session.createQuery("FROM main.java.customer.entity.Product p WHERE p.origin=:origin");
				query.setParameter("origin", origin);
				
				List<Product> products = query.getResultList();
				
				for (Product product : products) {

					ProductResponse2 response = new ProductResponse2();
					response.setName(product.getName());
					response.setPrice(product.getPrice());
					response.setNewPrice(product.getPrice()-(product.getPrice()*0.18));
					
					
					result.add(response);

				}
				
				tx.commit();
				session.close();
				
				return result;
				
			} catch (Exception e) {
				tx.rollback();
				System.out.println(e);
				return null;
			}
		}
	 
	 
	 //2)way   
	 public static List<ProductResponse2> getAllMacedonianProductsByDiscount() {
			Session session = init();
		    Transaction tx = null;
		    
		    List<ProductResponse2> result = new ArrayList<>();
		    
			try {
				tx = session.beginTransaction();
				
				Query query = session.createQuery("FROM main.java.customer.entity.Product p WHERE p.origin=:origin");
				query.setParameter("origin", "Macedonia");
				
				List<Product> products = query.getResultList();
				
				for (Product product : products) {

					ProductResponse2 response = new ProductResponse2();
					response.setName(product.getName());
					response.setPrice(product.getPrice());
					response.setNewPrice(product.getPrice()-(product.getPrice()*0.18));
					
					
					result.add(response);

				}
				
				tx.commit();
				session.close();
				
				return result;
				
			} catch (Exception e) {
				tx.rollback();
				System.out.println(e);
				return null;
			}
		}
		
  
  
}


