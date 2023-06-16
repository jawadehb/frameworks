package be.ehb.derdehands.controller;

import be.ehb.derdehands.dao.ProductDao;
import be.ehb.derdehands.entities.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;


@Controller
public class ProductController {

    private ProductDao mProductDao;


    @Autowired
    public ProductController(ProductDao mProductDao) {
        this.mProductDao = mProductDao;
    }

    @ModelAttribute("products")
    public Iterable<Product> getAllProducts(){
        return mProductDao.findAll();
    }

    @GetMapping({"/", "/index"})
    public String showIndex(ModelMap modelMap){
        return "index";
    }

    @GetMapping("/about")
    public String showAbout(){
        return "about";
    }

    @ModelAttribute("productToSave")
    public Product productForForm(){
        return new Product();
    }

    @GetMapping("/newproduct")
    public String showNewProduct(ModelMap modelMap){
        return "newproduct";
    }

    @PostMapping("/newproduct")
    public String insertProduct(@Valid @ModelAttribute("productToSave") Product product,
                              BindingResult result){
        if(result.hasErrors()){
            return "/newproduct";
        }
        mProductDao.save(product);
        return "redirect:/index";
    }

    @GetMapping("/product/{id}")
    public String showProductDetails(@PathVariable("id") int id, Model model) {
        Optional<Product> optionalProduct = mProductDao.findById(id);
        Product product = optionalProduct.orElse(null);
        model.addAttribute("product", product);
        return "productdetails";
    }




}
