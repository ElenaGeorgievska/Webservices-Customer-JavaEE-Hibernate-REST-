package main.java.customer.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import main.java.customer.entity.Customer;
import main.java.customer.services.CustomerService;


@Path("/customer")
public class CustomerController {
  
  //a. Create customer
  @POST
  @Path("/create")
  @Consumes(MediaType.APPLICATION_JSON)
  public String createUser(Customer customer) {
    CustomerService.createCustomer(customer);
    return "User " + customer.getFirstName() + " is created successfully";
  }
  
  
//a. Update customer
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateCustomer(Customer customer) {
	CustomerService.updateCustomer(customer);
	return "Customer: " + customer.getFirstName() + " was updated successfully";
  }
  
  
//a. Delete customer
  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
   public String deleteCustomer(Customer customer) {
	 CustomerService.deleteCustomer(customer);
	 return "Customer with id: " + customer.getId() + " was deleted successfully";
  }
  


}