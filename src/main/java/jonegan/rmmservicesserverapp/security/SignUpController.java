package jonegan.rmmservicesserverapp.security;

import jonegan.rmmservicesserverapp.entities.Customer;
import jonegan.rmmservicesserverapp.repositories.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class SignUpController {
    private final CustomerRepository customerRepository;

    public SignUpController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<SignUpRequest> createCustomer(@RequestBody SignUpRequest request) {

        System.out.println("hi");
        if (customerRepository.existsById(request.getUsername())) {
            return new ResponseEntity<>(request, HttpStatus.CONFLICT);
        }
        Customer customer = new Customer();
        customer.setId(request.getUsername());
        customer.setHashedPass(SecurityUtil.hashPassword(request.getPassword()));
        customer.setSubscribedServices(Collections.emptySet());
        customerRepository.save(customer);
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

}
