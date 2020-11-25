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
import javax.ws.rs.core.Response;

import main.java.customer.entity.Product;
import main.java.customer.entity.ShoppingCart;
import main.java.customer.services.ShoppingCartService;


@Path("/buy")
public class ShoppingCartController {

	@GET
	@Path("/init")
	@Produces(MediaType.APPLICATION_JSON)
	public String init() {                               
		ShoppingCartService.initService();                

		return "Init";
	}

	
	// Create Shopping Cart
	// d. Add product to shopping cart- addProductToSC(product)
	@POST
	@Path("/cart/{customerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createShoppingCart(@PathParam("customerId") Integer customerId, ShoppingCart cart) {

		ShoppingCartService.createCart(customerId, cart);

	}

	// e. Remove Product from Shopping Cart- removeProductFromSC
	@DELETE
	@Path("/removeProductFromSC/{productID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String removeProductFromSC(@PathParam("productID") Integer productID, ShoppingCart cartID) {
		ShoppingCartService.removeProductFromSC(productID, cartID);
		return "Product successfully removed from your Shopping Cart!";
	}
	
	
	//f. Get shopping cart by Cart ID
	@GET
	@Path("/getCart/{cartID}")
	@Produces(MediaType.APPLICATION_JSON)
	public ShoppingCart getCartByID(@PathParam("cartID")Integer cartID) {
		return ShoppingCartService.GetCartByID(cartID);
	}
	
	
	
	
	

}

