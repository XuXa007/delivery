package com.example.demo.controller;

import com.example.demo.Enum.ProductType;
import com.example.demo.dto.DeliveryDTO;
import com.example.demo.dto.ProviderDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ReportDTO;
import com.example.demo.service.DeliveryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProviderService;
import com.example.demo.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Контроллер для веб-интерфейса
 */
@Controller
@RequestMapping("/web")
public class WebController {

    private final ProviderService providerService;
    private final ProductService productService;
    private final DeliveryService deliveryService;
    private final ReportService reportService;

    public WebController(ProviderService providerService,
                         ProductService productService,
                         DeliveryService deliveryService,
                         ReportService reportService) {
        this.providerService = providerService;
        this.productService = productService;
        this.deliveryService = deliveryService;
        this.reportService = reportService;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    // Поставщики
    @GetMapping("/providers")
    public String listProviders(Model model) {
        List<ProviderDTO> providers = providerService.getAllProviders();
        model.addAttribute("providers", providers);
        model.addAttribute("newProvider", new ProviderDTO());
        return "providers/list";
    }

    @PostMapping("/providers")
    public String createProvider(@ModelAttribute("newProvider") ProviderDTO providerDTO) {
        providerService.createProvider(providerDTO);
        return "redirect:/web/providers";
    }

    @GetMapping("/providers/{id}")
    public String editProvider(@PathVariable Long id, Model model) {
        ProviderDTO provider = providerService.getProviderById(id);
        model.addAttribute("provider", provider);
        return "providers/edit";
    }

    @PostMapping("/providers/{id}")
    public String updateProvider(@PathVariable Long id, @ModelAttribute("provider") ProviderDTO providerDTO) {
        providerService.updateProvider(id, providerDTO);
        return "redirect:/web/providers";
    }

    // Продукты
    @GetMapping("/products")
    public String listProducts(Model model) {
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("newProduct", new ProductDTO());
        model.addAttribute("productTypes", ProductType.values());
        return "products/list";
    }

    @PostMapping("/products")
    public String createProduct(@ModelAttribute("newProduct") ProductDTO productDTO) {
        productService.createProduct(productDTO);
        return "redirect:/web/products";
    }

    @GetMapping("/products/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        ProductDTO product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("productTypes", ProductType.values());
        return "products/edit";
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute("product") ProductDTO productDTO) {
        productService.updateProduct(id, productDTO);
        return "redirect:/web/products";
    }

    // Поставки
    @GetMapping("/deliveries")
    public String listDeliveries(Model model) {
        List<DeliveryDTO> deliveries = deliveryService.getAllDeliveries();
        List<ProviderDTO> providers = providerService.getAllProviders();
        List<ProductDTO> products = productService.getAllProducts();

        model.addAttribute("deliveries", deliveries);
        model.addAttribute("providers", providers);
        model.addAttribute("products", products);
        model.addAttribute("newDelivery", new DeliveryDTO());

        return "deliveries/list";
    }

    @GetMapping("/deliveries/{id}")
    public String viewDelivery(@PathVariable Long id, Model model) {
        DeliveryDTO delivery = deliveryService.getDeliveryById(id);
        model.addAttribute("delivery", delivery);
        return "deliveries/view";
    }

    // Отчеты
    @GetMapping("/reports")
    public String reportForm(Model model) {
        model.addAttribute("startDate", LocalDateTime.now().minusMonths(1));
        model.addAttribute("endDate", LocalDateTime.now());
        return "reports/form";
    }

    @GetMapping("/reports/generate")
    public String generateReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Model model) {
        ReportDTO report = reportService.generateDeliveryReport(startDate, endDate);
        model.addAttribute("report", report);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "reports/view";
    }
}