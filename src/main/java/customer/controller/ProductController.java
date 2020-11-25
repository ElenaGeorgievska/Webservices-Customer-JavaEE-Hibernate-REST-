package main.java.customer.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import main.java.customer.entity.Product;
import main.java.customer.entity.ProductResponse;
import main.java.customer.entity.ProductResponse2;
import main.java.customer.entity.ShoppingCart;
import main.java.customer.services.ProductService;
import main.java.customer.services.ShoppingCartService;



@Path("/product")
public class ProductController {
  
	
  //b. Create product
  @POST
  @Path("/create")
  @Consumes(MediaType.APPLICATION_JSON)
  public String createProduct(Product product) {
    ProductService.createProduct(product);
    return "Product " + product.getName() + " is created successfully";
  }
  
  
  //b. Update product
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateProduct(Product product) {
	ProductService.updateProduct(product);
	return "Product: " + product.getName() + " was updated successfully";
  }
  
  
  //b.Delete product
  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  public String deleteProduct(Product product) {
	ProductService.deleteProduct(product);
	return "Product with id: " + product.getId() + " was deleted successfully";
  }
  
  
  
  /* //c. Get All Products / View all available products
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<Product> getAllProducts() {
	 return ProductService.getAllProducts();
	}*/
   
   
  ///g. Get All products by Category (in custom response to be only product name, price and category)
 	// category should be in url
    @GET
	@Path("/getAllProductsByCategory/{categoryID}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductResponse> getAllProductsByCategory(@PathParam("categoryID")Integer categoryID) {
		return ProductService.getAllProductsByCategory(categoryID);
	}
    
    
    //h. Get all products by Origin
  /*  @GET
   	@Path("/getAllProductsByOrigin/{origin}")
   	@Produces(MediaType.APPLICATION_JSON)
   	public List<Product> getAllProductsByOrigin(@PathParam("origin")String origin) {
   		return ProductService.getAllProductsByOrigin(origin);
   	}*/
    
    
    //i. BONUS: Get all products with macedonian origin with the new Price discounted by 18% and the old Price
    // 1) way
    @GET
   	@Path("/getAllProductsByDiscount/{origin}")
   	@Produces(MediaType.APPLICATION_JSON)
   	public List<ProductResponse2> getAllProductsByDiscount(@PathParam("origin")String origin) {
   		return ProductService.getAllProductsByDiscount(origin);
   	}
    
    
    //2)way
    @GET
   	@Path("/getAllMacedonianProductsByDiscount")
   	@Produces(MediaType.APPLICATION_JSON)
   	public List<ProductResponse2> getAllMacedonianProductsByDiscount() {
   		return ProductService.getAllMacedonianProductsByDiscount();
   	}

  
  
	
}
