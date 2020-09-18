package service;

import model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService{
    private static final List<Customer> customers;

    static {
        customers = new ArrayList<>();
        customers.add(new Customer(1,"Hiếu", "hieu@gmail.com", "hà nội", "a.jpg"));
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers);
    }

    @Override
    public void save(Customer customer) {
        customers.add(customer);
    }

    @Override
    public Customer findById(int id) {
        return customers.stream().filter(c -> c.getId() == (id)).findFirst().orElse(null);
    }

    @Override
    public void update(Customer customer) {
        Customer customer1 = findById(customer.getId());
        customer1.setName(customer.getName());
        customer1.setEmail(customer.getEmail());
        customer1.setAddress(customer.getAddress());
        customer1.setAddress(customer.getImage());
    }

    @Override
    public void remove(int id) {
        customers.removeIf(c -> c.getId() == (id));
    }
}
