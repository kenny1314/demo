package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.*;
import com.psu.kurs.demo.entity.*;
import com.psu.kurs.demo.services.MenuService;
import com.psu.kurs.demo.services.OtherService;
import com.psu.kurs.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@EnableTransactionManagement
@Controller
public class CreateOrderController {

    private static Logger logger = LoggerFactory.getLogger(CreateOrderController.class);

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    PlatformsRepository platformsRepository;

    @Autowired
    GenresRepository genresRepository;

    @Autowired
    LanguagesRepository languagesRepository;

    @Autowired
    AgeLimitsRepository ageLimitsRepository;

    @Autowired
    PublishersRepository publishersRepository;

    @Autowired
    ImagesTRepository imagesTRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Autowired
    RequestsRepository requestsRepository;

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    FinalOrderRepository finalOrderRepository;

    @Autowired
    AddressDRepository addressDRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    TypeOfDeliveryRepository typeOfDeliveryRepository;

    @Autowired
    MenuService menuService;

    @Autowired
    OtherService otherService;


    @PersistenceContext
    private EntityManager entityManager;

    //корзина
    // registration order
    @GetMapping("/basket")
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String basket(Model model, Principal principal, HttpServletRequest request) {

        model = menuService.getMenuItems(model); //get menu items

        Basket basket;

        User user = userService.findByUsername(principal.getName());
        if (basketRepository.existsById(user.getId())) {
            basket = basketRepository.getOne(user.getId());
        } else {
            basket = new Basket();
            basket.setId(user.getId());
            basketRepository.save(basket);
        }

        List<Products> productsListBasket = new ArrayList<>();

        if (basket.getRequestsList() != null) {
            if (basket.getRequestsList().size() > 0) {
                for (Requests req : basket.getRequestsList()) {
                    productsListBasket.add(req.getProducts());
                }

                model.addAttribute("requestsList", basket.getRequestsList());
                model.addAttribute("productsListBasket", productsListBasket);

            }
        } else {
            model.addAttribute("requestsList", null);
            logger.info("not req");
        }

        model.addAttribute("currentURL", otherService.getCurrentUrl(request));
        model.addAttribute("finalPrice", basket.getFinalPrice());

        return "basket";
    }


    //registration order на какую страницу перенаправить
    @PostMapping("/actionDefinition")
    public String actionDefinition(@RequestParam(name = "typeOfDelivery", required = false) String typeOfDelivery,
                                   @RequestParam(name = "finalPrice", required = false) String finalPrice, Model model
    ) {

        if (typeOfDelivery.equals("Курьер")) {
            return "forward:/createOrder";
        }
        if (typeOfDelivery.equals("Самовывоз")) {
            return "redirect:/storeSelection";
        }

        return "redirect:/basket";
    }

    //оформление курьёр
    // registration order
    @PostMapping("/createOrder")
    public String createOrder(Model model, Principal principal) {
        model = menuService.getMenuItems(model);

        User user = userService.findByUsername(principal.getName());

        Address address = new Address(user.getId(), user.getAddress().getCity(), user.getAddress().getStreet(), user.getAddress().getFlatNumber());

        model.addAttribute("address", address);

        if (user != null) {
            Basket basket = basketRepository.getOne(userService.findByUsername(principal.getName()).getId());
            model.addAttribute("finalPrice", basket.getFinalPrice());
            logger.info("finalPrice: " + basket.getFinalPrice());
        } else {
            model.addAttribute("finalPrice", "notPrice");
        }

        return "createOrder";
    }


    //выбор магазина
    //registration order
    @GetMapping("/storeSelection")
    public String storeSelection(Model model, Principal principal) {

        model = menuService.getMenuItems(model); //get menu items

        return "store_selection";
    }


    @GetMapping("/orderIsProcessed")
    public String orderIsProcessed00(@RequestParam(name = "fb", required = false) String radioValue, Model model, Principal principal, HttpServletRequest request) {
        System.out.println("get orderIsProccessed");
        return "orderComplete";
    }

    //выбрать магазин и добавить нужный адрес в заказ
    //registration order
    //вывод значения с радиокнопки
//нужна транзакция
    @Transactional
    @PostMapping("/orderIsProcessed")
    public String orderIsProcessed(@RequestParam(name = "fb", required = false) String radioValue, Model model, Principal principal, HttpServletRequest request) {

        System.out.println(")))))))))PrincipaL: " + principal.getName());

        model = menuService.getMenuItems(model); //get menu items

        //видимо нет корзины
        Basket basket = basketRepository.getOne(userService.findByUsername(principal.getName()).getId());

        System.out.println("exists:" + basketRepository.existsById(userService.findByUsername(principal.getName()).getId()));

        if (basketRepository.existsById(userService.findByUsername(principal.getName()).getId()) == false) {
//            rollback

//            FinalOrder finalOrder = new FinalOrder();
//            finalOrder.setId(10099999L);
//            finalOrder.setDate(new Date().toString());
//            finalOrder.setFinalPrice(9009999);
//            finalOrder.setUser(userService.findByUsername(principal.getName()));
//            finalOrderRepository.save(finalOrder);
//            throw new NullPointerException("&&&test tranx on");
            return "procOrderError";
        }

        List<Requests> requestsList = basket.getRequestsList(); //&&&

        Long inxIns = 1L;
        List<FinalOrder> finalOrderList = finalOrderRepository.findAll();
        if (finalOrderList.size() > 0) {

            AdminController adminController = new AdminController();
            Long actualCount = 1L;
            try {
                inxIns = adminController.getLastId(finalOrderRepository, FinalOrder.class.getCanonicalName());
                inxIns++;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        FinalOrder finalOrder = new FinalOrder();
        finalOrder.setId(inxIns);
        finalOrder.setDate(new Date().toString());
        finalOrder.setFinalPrice(basket.getFinalPrice());
        finalOrder.setUser(userService.findByUsername(principal.getName()));
        finalOrderRepository.save(finalOrder);
        finalOrderRepository.flush();

        //устонавливает finalOrder в requests и удаляет корзину из requests
        for (Requests rq : requestsList) {
            rq.setFinalOrder(finalOrder);
            rq.setBasket(null);
        }
        requestsRepository.saveAll(requestsList);

        basket.setRequestsList(null); //бесполезно
        basketRepository.deleteById(basket.getId());

        List<AddressD> addressDList = addressDRepository.findAll();
        Long idAd = 1L;
        if (addressDList.size() > 0) {
            idAd = addressDList.get(addressDList.size() - 1).getId() + 1;
        }

        AddressD addressD0 = null;
        if (radioValue.equals("obsh")) {
            addressD0 = addressDRepository.getOne(10001L);
        } else {
            addressD0 = addressDRepository.getOne(10002L);
        }

        //найти последнее в списке с id пользователя
        List<Delivery> deliveryList = deliveryRepository.findAll();
        Long idL = 1L;
        if (deliveryList.size() > 0) {
            idL = deliveryList.get(deliveryList.size() - 1).getId() + 1;
        }

        //возможно вставить получение последнего id
        Delivery delivery = new Delivery();
        delivery.setId(idL);
        delivery.setAddressD(addressD0);
        delivery.setDate(new Date().toString());
        delivery.setTypeOfDelivery(typeOfDeliveryRepository.getOne(1L));

        deliveryRepository.save(delivery);

        finalOrder.setDelivery(delivery);
        finalOrderRepository.save(finalOrder);

        return "orderComplete";
    }

    //registration order
    @GetMapping("/delProdBask/{id}")
    public String delProdBasket(@PathVariable(value = "id", required = false) String id, Principal principal) {

        User user = userService.findByUsername(principal.getName());

        if (requestsRepository.existsById(Long.valueOf(id))) {
            requestsRepository.deleteById(Long.valueOf(id));

            Basket basket = basketRepository.getOne(user.getId());

            double finalPrice = 0;

            for (Requests value : basket.getRequestsList()) {
                finalPrice += value.getPrice() * value.getNumberOfDays();
            }
            basket.setFinalPrice(finalPrice);

            basketRepository.save(basket);
        } else {
            logger.info("product doesn't exist" + id);
        }

        return "redirect:/basket";
    }


    //оформление заказа если курьер
    //@Transactional
    @PostMapping("/completeСheckout")
    public String completeCheckout(@RequestParam(name = "city", required = false) String city,
                                   @RequestParam(name = "street", required = false) String street,
                                   @RequestParam(name = "flat_number", required = false) String flat_number,
                                   Principal principal, Model model) {

        model = menuService.getMenuItems(model); //get menu items

        Basket basket = basketRepository.getOne(userService.findByUsername(principal.getName()).getId());

        List<Requests> requestsList = basket.getRequestsList();

        Long inxIns = 1L;
        List<FinalOrder> finalOrderList = finalOrderRepository.findAll();
        if (finalOrderList.size() > 0) {
            inxIns = finalOrderList.get(finalOrderList.size() - 1).getId() + 1;
        }


        FinalOrder finalOrder = new FinalOrder();
        finalOrder.setId(inxIns);
        finalOrder.setDate(new Date().toString());
        finalOrder.setFinalPrice(basket.getFinalPrice());
        finalOrder.setUser(userService.findByUsername(principal.getName()));
        finalOrderRepository.save(finalOrder);

        for (Requests rq : requestsList) {
            rq.setFinalOrder(finalOrder);
            rq.setBasket(null);
        }
        requestsRepository.saveAll(requestsList);
        basket.setRequestsList(null); //бесполезно
        basketRepository.deleteById(basket.getId());


        List<AddressD> addressDList = addressDRepository.findAll();
        Long idAd = 1L;
        if (addressDList.size() > 0) {
            idAd = addressDList.get(addressDList.size() - 1).getId() + 1;
        }

        AddressD addressD = new AddressD();
        addressD.setId(idAd);
        addressD.setCity(city);
        addressD.setStreet(street);
        addressD.setFlatNumber(flat_number);
        addressDRepository.save(addressD);

        //найти последнее в списке с id пользователя
        List<Delivery> deliveryList = deliveryRepository.findAll();
        Long idL = 1L;
        if (deliveryList.size() > 0) {
            idL = deliveryList.get(deliveryList.size() - 1).getId() + 1;
        }

        //возможно вставить получение последнего id
        Delivery delivery = new Delivery();
        delivery.setId(idL);
        delivery.setAddressD(addressDRepository.getOne(idAd));
        delivery.setDate("date");
        delivery.setTypeOfDelivery(typeOfDeliveryRepository.getOne(2L));

        deliveryRepository.save(delivery);

        finalOrder.setDelivery(delivery);

        finalOrderRepository.save(finalOrder);

        return "orderComplete";
    }


    //registration order
    //сохранить товар в корзину
    @PostMapping("/testDB/{id}")
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String testDB(@PathVariable("id") String id, @RequestParam(name = "currentURL", required = false) String currentURL, @RequestParam("inputplus") String numberOfDays, Model model, Principal principal) {

        logger.info("principal: " + principal.getName());
        logger.info("_______________________________________ " + numberOfDays + "______________");

        model = menuService.getMenuItems(model); //get menu items

        Requests requests = new Requests();

        Products products = productsRepository.getOne(Long.valueOf(id));

        Date date = new Date();
        User user = userService.findByUsername(principal.getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
        requests.setDate(simpleDateFormat.format(new Date()));

        Basket basket = null;

        //если корзины нет, то создаём
        if (basketRepository.existsById(user.getId())) {
            basket = basketRepository.getOne(user.getId());
        } else {
            basket = new Basket();
            basket.setId(user.getId());
            basketRepository.save(basket);
        }

        requests.setBasket(basket);

        requests.setNumberOfDays(Integer.parseInt(numberOfDays));
        requests.setPrice(products.getOneDayPrice());
        requests.setProducts(products);

        requestsRepository.save(requests); //добавить request чтоб отображалось в корзине

        boolean trAdd = false;

        List<Requests> requestsList = requestsRepository.findAll();

        //если такой товар уже есть и мы добавляем его снова и удаляем старый request
        if (requestsRepository.findAll().size() > 0) {
            for (int i = 0; i < requestsRepository.findAll().size(); i++) {
                if ((requestsList.get(i).getBasket() != null) && (requestsList.get(i).getBasket().getId() == requests.getBasket().getId()) &&
                        (requestsList.get(i).getProducts().getId() == requests.getProducts().getId())) {

                    Long oldID = requestsList.get(i).getId(); //получить старое id товара в request
                    logger.info("id old: " + oldID);
                    logger.info("((((Такая корзина и продукт уже есть уже есть");

                    basketRepository.save(basket);

                    requestsRepository.delete(requestsRepository.getOne(oldID)); //удалить старый request
                    requestsRepository.save(requests); //сохранить новый

                    logger.info("add to db");
                    trAdd = true;
                    break;
                }
            }
        }
        if (!trAdd) {
            logger.info("tradd: " + trAdd);
            basketRepository.save(basket);
            requestsRepository.save(requests);
        }

        requestsList = requestsRepository.findAll();
        int counter = 0;
        double finalPrice = 0;
        double coefUser=1.0;
        if(user.getCount_reqests()!=0.0){
            coefUser=Double.valueOf(user.getCount_reqests());
            if(coefUser>500){
                coefUser=0.9;
            }else {
                coefUser=1;
            }
        }
//        coefUser = Double.valueOf(user.getCount_reqests());

        for (Requests value : requestsList) {
            //если request id совпадает с корзиной, то считаем общую сумму в корзине
            if ((value.getBasket() != null) && (value.getBasket().getId() == basket.getId())) {
                finalPrice += value.getPrice()*coefUser * value.getNumberOfDays();
                counter++;
            }
        }
        logger.info("counter: " + counter);

        basket.setFinalPrice(finalPrice);
        basketRepository.save(basket);

        logger.info("currentURL: " + currentURL);

        if (currentURL == null) {
            return "redirect:/";
        } else {
            return "redirect:/" + currentURL;
        }

    }


}

