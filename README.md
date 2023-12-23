Exercises


  Add PetStore Employee
In this section, you will write controller and service layer methods to add an employee to an existing pet store. Here are the instructions.

Note: if you get stuck, after submission, you will have access to the Week 15 Coding Assignment solution.



    Controller class
In this section, you will create a method in the controller class that allows an employee to be added to a pet store.

1.     Create a method in the controller that will add an employee to the employee table. The method should be annotated with @PostMapping and @ResponseStatus.

a.     @PostMapping: This must be configured to allow the caller to send an HTTP POST request to "/pet_store/{petStoreId}/employee" where {petStoreId}  is the ID of the pet store in which to add the employee (for example, "/pet_store/1/employee"). Remember that the "/pet_store" part of the HTTP URI is defined at the class level in the @RequestMapping annotation.

b.     @ResponseStatus: This should be configured to return a 201 (Created) status code.

2.     The method should be public and return a PetStoreEmployee object.

3.     The method should accept the pet store ID and the PetStoreEmployee object as parameters. The pet store ID is passed in the URI and the PetStoreEmployee object is passed as JSON in the request body.

4.     Log the request.

5.     The method should call the saveEmployee() method in the pet store service and should return the results of that method call.



    DAO interface
In this section, you will create a new DAO interface to manage CRUD operations on the employee table. It is used by the service method to find an existing Employee row.



Note: if you get stuck, after submission, you will have access to the Week 15 Coding Assignment solution.



1.     Create a new DAO interface named EmployeeDao.

2.     The interface should extend JpaRepository. It is very similar to PetStoreDao but it refers to an Employee object in the Generic. Here is an example:



3.     The new DAO should be in the pet.store.dao package.



    Service class
In this section, you will write the saveEmployee() method in the service class. This will add code that is essentially similar to the savePetStore() method.

Here is the strategy in pseudocode:


This is pseudoCode for saveEmployee


Inject the EmployeeDao object into the pet store service class using the @Autowired annotation.

Add the @Transactional annotation as a method-level annotation from the org.springframework.transaction.annotation package. Set the readOnly attribute to false. (Note: a method-level annotation means that the annotation goes right above the method declaration.)

3. Create a method named findEmployeeById().

a. It should take the pet store ID and the employee ID as parameters.

b. Use the employeeDAO method findById() to return the Employee object. If the employee isn't found throw a new NoSuchElementException().

c. If the pet store ID in the Employee object's PetStore variable does not match the given pet store ID, throw a new IllegalArgumentException.

d. If everything is OK, the method should return the Employee object. 

4. Create a new method findOrCreateEmployee().

a. This method should take an employee ID as a parameter (this will be null if the employee is being created), as well as the pet store ID. It will return an Employee object if successful.

b. If the pet store ID is null, it should return a new Employee object.

c. If the pet store ID is not null, it should call the method, findEmployeeById().

5. Create a new method copyEmployeeFields().

a.  The method should take an Employee as a parameter and a PetStoreEmployee as a parameter.

b. Copy all matching PetStoreEmployee fields to the Employee object.

6.  Add a new method named saveEmployee().

a. This method should take a pet store ID and a PetStoreEmployee object as parameters. It must return a PetStoreEmployee object.

b. Call findPetStoreById() to find the pet store object.

c. Call findOrCreateEmployee() to retrieve an existing employee or to create a new one.

d. Call copyEmployeeFields() to copy the data in the pet store employee parameter (which ultimately came from the JSON in the HTTP POST request payload) to the Employee object.

e.  Set the PetStore object in the Employee object.

f. Add the Employee object into the Set of Employee objects in the PetStore object.

g. Save the employee by calling the save() method in the employee DAO. 

h. Convert the Employee object returned by the save method to a PetStoreEmployee object and return it.

7. Test it!  Use ARC or the REST client of your choice to send an HTTP POST request to 

http://localhost:8080/pet_store/{ID}/employee

where {ID} is the valid primary key value of an existing pet store row in the pet_store table. You can find sample JSON to create an employee in the student resource.




---------------------------------------------------------------------------


  Add PetStore Customer
In this section, you will add a customer to an existing pet store. 

Follow the instructions in the Add store employee section, above using the pseudocode instructions modified to use customer instead of employee. 

Modify the instructions to add the customer. 

Note: that the controller and service methods should use a PetStoreCustomer DTO instead of PetStoreEmployee. (You have already created PetStoreCustomer in the pet.store.controller.model package.)

Make sure that the request used to add a customer is an HTTP POST request sent to 

http://localhost:8080/pet_store/{ID}/customer

where {ID} is the primary key value of an existing pet store record. You can find sample JSON to add a customer in the student resources.

Note:  that customer and pet store have a many-to-many relationship. This means that a Customer object has a List of PetStore objects. This means that, in the method findCustomerById(), you will need to loop through the list of PetStore objects looking for the pet store with the given pet store ID. If not found, throw an IllegalArgumentException.




---------------------------------------------------------------------------

  List all Pet Stores

In this section you will write the methods to list all pet stores. This method will return summary data for the pet stores. In other words, it will return the pet store data but not the list of customers or employees. Here are the instructions.

1. Add a method to the controller that returns List<PetStoreData>. Remember to add the @GetMapping annotation. This annotation does not take a value (i.e., no ID) and the method takes no parameters. Create/call the retrieveAllPetStores() method in the service class.

2.  In the service class method retrieveAllPetStores():

a.  Add the @Transactional annotation.

b.  Call the findAll() method in the pet store DAO. Convert the List of PetStore objects to a List of PetStoreData objects.

c. Remove all customer and employee objects in each PetStoreData object. Here's how to remove the customer and employee objects in a loop if you need a hint.

d. Return the summary list.

3. Test the new method by sending a GET request to "/pet_store". You should see all the pet stores returned without customers and employees.





---------------------------------------------------------------------------


  Retrieve A Pet Store By Store ID


In this section you will write the controller and service methods to retrieve a pet store by its ID. You will test this with a valid ID.

Follow these instructions:

1. Add a controller method to retrieve a single pet store given the pet store ID. It will be very similar to the retrieve all pet stores method except that the @GetMapping annotation will take the pet store ID that is passed to the method as a parameter. Create/call the method in the service class.

2.  In the service class method, add an @Transactional annotation. Call the find by ID method written earlier and convert the results to a PetStoreData object.

3.  Test using a valid pet store ID. You should see the pet store data along with all customers and employees.



---------------------------------------------------------------------------

  Delete Pet Store
In this section, you will delete a pet store. You should see all employees of the pet store deleted as well. In addition, all customer join table records for the pet store should be deleted as well.

1. Add the deletePetStoreById() method in the controller.

a.  It should take the pet store ID as a parameter (remember to use @PathVariable). 

b.  Add the @DeleteMapping annotation. This should specify that the pet store ID is part of the URI. For example, the URI should be 

http://localhost:8080/pet_store/{ID}

where {ID} is the ID of the pet store to delete.

c. Log the request.

d. Call the deletePetStoreById() method in the service, passing the pet store ID as a parameter.

e. Return a Map<String, String> where the key is "message" and the value is a deletion successful message.

2. Add the deletePetStoreById() method in the service class.

a. Call findPetStoreById() to retrieve the PetStore entity.
b. Call the delete() method in the PetStoreDao interface, passing in the PetStore entity.

3.  Test it. When you delete a pet store...

a. All employees of that pet store should be removed as well.

b. All customer join table records should be removed but not the actual customer records. If the customer records are removed as well, make sure that the @ManyToMany annotation in the PetStore class has the attribute cascade set to CascadeType.PERSIST and not CascadeType.ALL.
