package main.java.customer.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//Shopping Cart(id, Product_id, customer_id, createdOn)

@Entity
@Table
public class ShoppingCart {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@ManyToMany(targetEntity=main.java.customer.entity.Product.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	  @JoinTable(name="shopping_cart_product", joinColumns = {
	      @JoinColumn(name="shopping_cart_id")},
	  inverseJoinColumns = {@JoinColumn(name = "product_id")})
	private List<Product> products;
	
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Customer customer;
	
	private Date createdOn;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	
	public ShoppingCart(List<Product> products, Customer customer, Date createdOn) {
		super();
		this.products = products;
		this.customer = customer;
		this.createdOn = createdOn;
	}

	
	public ShoppingCart() {
		
	}
	
	

}

