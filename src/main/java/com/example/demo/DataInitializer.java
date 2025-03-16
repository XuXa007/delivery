//package com.example.demo;
//
//import com.example.demo.entity.Supplier;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private final SupplierRepository supplierRepository;
//    private final ProductRepository productRepository;
//
//    public DataInitializer(SupplierRepository supplierRepository, ProductRepository productRepository) {
//        this.supplierRepository = supplierRepository;
//        this.productRepository = productRepository;
//    }
//
//    @Override
//    public void run(String... args) {
//        // Поставщики
//        Supplier supplier1 = new Supplier();
//        supplier1.setName("Supplier 1");
//        supplierRepository.save(supplier1);
//
//        Supplier supplier2 = new Supplier();
//        supplier2.setName("Supplier 2");
//        supplierRepository.save(supplier2);
//
//        Supplier supplier3 = new Supplier();
//        supplier3.setName("Supplier 3");
//        supplierRepository.save(supplier3);
//
//        // Продукты
//        Product apple1 = new Product();
//        apple1.setName("Apple Golden");
//        apple1.setType("apple");
//        productRepository.save(apple1);
//
//        Product apple2 = new Product();
//        apple2.setName("Apple Red");
//        apple2.setType("apple");
//        productRepository.save(apple2);
//
//        Product pear1 = new Product();
//        pear1.setName("Pear Conference");
//        pear1.setType("pear");
//        productRepository.save(pear1);
//
//        Product pear2 = new Product();
//        pear2.setName("Pear Williams");
//        pear2.setType("pear");
//        productRepository.save(pear2);
//    }
//}
