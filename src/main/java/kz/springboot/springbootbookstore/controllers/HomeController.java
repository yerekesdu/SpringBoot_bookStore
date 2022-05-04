package kz.springboot.springbootbookstore.controllers;

import kz.springboot.springbootbookstore.entities.Books;
import kz.springboot.springbootbookstore.entities.TypeOfBook;
import kz.springboot.springbootbookstore.entities.Users;
import kz.springboot.springbootbookstore.repositories.TypeOfBookRepository;
import kz.springboot.springbootbookstore.services.BookService;
import kz.springboot.springbootbookstore.services.TypeOfBookService;
import kz.springboot.springbootbookstore.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.print.Book;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private BookService bookService;

    @Autowired
    private TypeOfBookService typeOfBookService;

    @Autowired
    private UserService userService;

    @Value("${file.avatar.viewPath}")
    private String viewPath;

    @Value("${file.avatar.uploadPath}")
    private String uploadPath;

    @Value("${file.avatar.defaultPicture}")
    private String defaultPicture;


    @GetMapping(value = "/")
    public String index(Model model){

        List<Books> books = bookService.getAllBooks();
        model.addAttribute("books", books);

        List<TypeOfBook> typeOfBooks = typeOfBookService.getAllTypeOfBook();
        model.addAttribute("typeOfBooks", typeOfBooks);

        model.addAttribute("currentUser", getUserData());

        return "index";
    }

    @PostMapping(value = "/addbook")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN')")
    public String addBook(Model model,
                            @RequestParam(name = "bookName") String bookName,
                            @RequestParam(name = "description") String description,
                            @RequestParam(name = "author") String author,
                            @RequestParam(name = "price") int price,
                            @RequestParam(name = "amount") int amount,
                            @RequestParam(name = "typeOfBook") Long typeOfBookId){

        TypeOfBook typeOfBook = typeOfBookService.getTypeOfBook(typeOfBookId);

        Books book = new Books();
        book.setBookName(bookName);
        book.setDescription(description);
        book.setAuthor(author);
        book.setPrice(price);
        book.setAmount(amount);
        book.setTypeOfBook(typeOfBook);
        bookService.addBook(book);

        model.addAttribute("currentUser", getUserData());

        return "redirect:/";
    }

    @GetMapping(value = "/addbook")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN')")
    public String addBookG(Model model){

        List<TypeOfBook> typeOfBooks = typeOfBookService.getAllTypeOfBook();
        model.addAttribute("typeOfBooks", typeOfBooks);

        model.addAttribute("currentUser", getUserData());

        return "addbook";

    }

    @GetMapping(value = "/editbook/{bookID}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String editBookG(Model model,
                            @PathVariable(name = "bookID") Long id){

        Books book = bookService.getBook(id);

        model.addAttribute("book", book);

        List<TypeOfBook> typeOfBooks = typeOfBookService.getAllTypeOfBook();
        model.addAttribute("typeOfBooks", typeOfBooks);

        model.addAttribute("currentUser", getUserData());

        return "editbook";

    }


    @PostMapping(value = "/editbook")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN')")
    public String editBook(Model model,
                           @RequestParam(name = "id") Long id,
                          @RequestParam(name = "bookName") String bookName,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "author") String author,
                          @RequestParam(name = "price") int price,
                          @RequestParam(name = "amount") int amount,
                           @RequestParam(name = "typeOfBook") Long typeOfBookId){

        Books book = bookService.getBook(id);
        TypeOfBook typeOfBook = typeOfBookService.getTypeOfBook(typeOfBookId);


        if(book!=null) {
            book.setBookName(bookName);
            book.setDescription(description);
            book.setAuthor(author);
            book.setPrice(price);
            book.setAmount(amount);
            book.setTypeOfBook(typeOfBook);
            bookService.saveBook(book);
        }

        model.addAttribute("currentUser", getUserData());

        return "redirect:/";
    }

    @PostMapping(value = "/deletebook")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN')")
    public String deleteBook(Model model,
                             @RequestParam(name = "id") Long id){

        Books book = bookService.getBook(id);
        bookService.deleteBook(book);

        model.addAttribute("currentUser", getUserData());

        return "redirect:/";
    }


    @GetMapping(value = "/details/{bookID}")
    public String details(Model model, @PathVariable(name = "bookID") Long id) {

        Books book = bookService.getBook(id);
        model.addAttribute("book", book);

        List<TypeOfBook> typeOfBooks = typeOfBookService.getAllTypeOfBook();
        model.addAttribute("typeOfBooks", typeOfBooks);

        model.addAttribute("currentUser", getUserData());

        return "details";

    }

    @GetMapping(value = "/403")
    public String accessDenied(Model model){

        model.addAttribute("currentUser", getUserData());

        return "403";

    }

    @GetMapping(value = "/login")
    public String login(Model model){

        model.addAttribute("currentUser", getUserData());

        return "login";

    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){

        model.addAttribute("currentUser", getUserData());
        return "profile";

    }

    @GetMapping(value = "/register")
    public String register(Model model){

        model.addAttribute("currentUser", getUserData());

        return "register";

    }

    @PostMapping(value = "/register")
    public String toRegister(@RequestParam(name = "user_email") String email,
                             @RequestParam(name = "user_full_name") String fullName,
                             @RequestParam(name = "user_password") String password,
                             @RequestParam(name = "re_user_password") String rePassword){

        if(password.equals(rePassword)){

            Users newUser = new Users();
            newUser.setFullName(fullName);
            newUser.setPassword(password);
            newUser.setEmail(email);

            if(userService.createUser(newUser) !=null  ){
                return "redirect:/register?success";
            }

        }

        return "redirect:/register?error";

    }

    private Users getUserData(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User) authentication.getPrincipal();
            Users myUser = userService.getUserByEmail(secUser.getUsername());
            return myUser;
        }

        return null;
    }

    @PostMapping(value = "/uploadavatar")
    @PreAuthorize("isAuthenticated()")
    public String uploadAvatar(@RequestParam(name = "user_ava") MultipartFile file){

        if(file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")){

            try{

                Users currentUser = getUserData();
                String picName =  DigestUtils.sha1Hex("avatar_" + currentUser.getId() + "_!Picture");
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadPath + picName + ".jpg");
                Files.write(path, bytes);

                currentUser.setUserAvatar(picName);
                userService.saveUser(currentUser);

                return "redirect:/profile?success";

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return "redirect:/";
    }

    @GetMapping(value = "/viewphoto/{url}", produces = {MediaType.IMAGE_JPEG_VALUE})
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody byte[] viewProfilePhoto(@PathVariable(name = "url") String url) throws IOException{

        String pictureURL = viewPath + defaultPicture;

        if(url!=null && !url.equals("null")){
            pictureURL = viewPath + url + ".jpg";
        }

        InputStream in;

        try{

            ClassPathResource resource = new ClassPathResource(pictureURL);
            in = resource.getInputStream();

        }catch (Exception e){

            ClassPathResource resource = new ClassPathResource(viewPath + defaultPicture);
            in = resource.getInputStream();
            e.printStackTrace();
        }

        return IOUtils.toByteArray(in);

    }


}
