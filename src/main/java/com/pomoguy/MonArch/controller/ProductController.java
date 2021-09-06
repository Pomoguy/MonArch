package com.pomoguy.MonArch.controller;


import com.pomoguy.MonArch.dao.ProductRepo;
import com.pomoguy.MonArch.model.User;
import com.pomoguy.MonArch.model.archcatalog.Product;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;



@Controller
@RequestMapping("/products")
//@PreAuthorize("hasAuthority('ADMIN')")
public class ProductController {

    @Autowired
    private ProductRepo productRepo;

    @PersistenceContext
    private EntityManager em;


    @GetMapping
    public String productGetList(Model model) {
        model.addAttribute("products", productRepo.findAll());
        return "archcatalog/products/productList";
    }

    @GetMapping("/add")
    public String productGetFormAdd(Model model) {
        return "archcatalog/products/productEdit";
    }

    @PostMapping("/add")
    public String productAdd(@AuthenticationPrincipal User user,
                              @RequestParam String name,
                              @RequestParam String description,
                              Model model) {

        Product product = new Product(name,user,description);

        product.setCreateDateTime();
        product.setUpdateDateTime();
        product.setUpdatedBy(user.getUsername());
        productRepo.save(product);
        return "redirect:/products/";
    }


    @GetMapping("{product}/profile")
    public String productGetProfile(@PathVariable Product product, Model model) {
        model.addAttribute("isHistoryObj", false);
        model.addAttribute("product", product);

        return "archcatalog/products/form/productProfile";
    }


    @GetMapping("{product}/history")
    public String productGetHistory(@PathVariable Product product, Model model) {
        AuditQuery query = AuditReaderFactory.get(em)
                .createQuery()
                .forRevisionsOfEntity(Product.class,false,false)
                .add(AuditEntity.property("id").eq(product.getId()));
        List<Object []> audit = query.getResultList();
        Collections.reverse(audit);
        model.addAttribute("product", product);
        model.addAttribute("audit", audit);
        model.addAttribute("isHistoryObj", false);
        return "archcatalog/products/form/productHistory";
    }

    @GetMapping("{product}/history/{rev}/profile")
    public String historyProductGetProfile(@PathVariable Product product, @PathVariable Integer rev, Model model) {
        AuditQuery query = AuditReaderFactory.get(em)
                .createQuery()
                .forEntitiesAtRevision(Product.class, rev)
                .add(AuditEntity.property("id").eq(product.getId()));;
        product = (Product) query.getSingleResult();
        model.addAttribute("isHistoryObj", true);
        model.addAttribute("product", product);
        return "archcatalog/products/form/productProfile";
    }



    @GetMapping("{product}/docs")
    public String productGetDocs(@PathVariable Product product, Model model) {
        List<Object> docs = null;
        model.addAttribute("docs", docs);
        model.addAttribute("product", product);
        model.addAttribute("isHistoryObj", false);
        return "archcatalog/products/form/productDocs";
    }


    @GetMapping("/edit/{product}")
    public String productGetFormEdit(@PathVariable Product product, Model model) {
        model.addAttribute("product", product);
        return "archcatalog/products/productEdit";
    }


    @PostMapping("/edit/{product}")
    public String productEdit(@PathVariable Product product,
                               @AuthenticationPrincipal User user,
                               @RequestParam String name,
                               @RequestParam String description,
                               Model model) {

        product.setDescription(description);
        product.setName(name);
        product.setUpdateDateTime();
        product.setUpdatedBy(user.getUsername());

        productRepo.save(product);
        return "redirect:/products/" + product.getId() + "/profile";
    }

}