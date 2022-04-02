package se.project.dao.api.user;

import se.project.model.user.Customer;

public interface UserService {

   public Customer getUserByUsername(String cust);
   public Customer getUserById(String id);
   public String getNameById(String id);
} 
