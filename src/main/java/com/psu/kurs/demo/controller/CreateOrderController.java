package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.*;
import com.psu.kurs.demo.entity.*;
import com.psu.kurs.demo.services.MenuService;
import com.psu.kurs.demo.services.OtherService;
import com.psu.kurs.demo.services.UserService;
import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@EnableTransactionManagement
@Controller
public class CreateOrderController {

    private static Logger logger = LoggerFactory.getLogger(CreateOrderController.class);

    @Autowired
    UserRepository userRepository;
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

        System.out.println(request);
        model = menuService.getMenuItems(model); //get menu items

        Basket basket;
        Double basketSum = 0.0;

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

                for (Products prod : productsListBasket) {
                    basketSum += prod.getFullPrice();
                }

                model.addAttribute("requestsList", basket.getRequestsList());
                model.addAttribute("productsListBasket", productsListBasket);
                model.addAttribute("basketSum", basketSum);

            }
        } else {
            model.addAttribute("requestsList", null);
            logger.info("not req");
        }

        model.addAttribute("currentURL", otherService.getCurrentUrl(request));
        model.addAttribute("finalPrice", basket.getFinalPrice());

        return "basket";
    }


    //TODO dd
    //registration order на какую страницу перенаправить
    @PostMapping("/actionDefinition")
    public String actionDefinition(@RequestParam(name = "typeOfDelivery", required = false) String typeOfDelivery,
                                   @RequestParam(name = "basketSum", required = false) String basketSum,
                                   Model model, Principal principal, RedirectAttributes redirectAttributes
    ) {

        User user = userService.findByUsername(principal.getName());

        if (user != null && basketSum != null) {
            Double basketSumDoub = Double.valueOf(basketSum);
            if (user.getBalance() < basketSumDoub) {
                redirectAttributes.addFlashAttribute("error", true);
                return "redirect:/basket";
            }
        }

        if (typeOfDelivery.equals("Курьер")) {
            redirectAttributes.addFlashAttribute("basketSum", basketSum);
            return "redirect:/createOrder";
        }
        if (typeOfDelivery.equals("Самовывоз")) {
            redirectAttributes.addFlashAttribute("basketSum", basketSum);
            return "redirect:/storeSelection";
        }

        return "redirect:/basket";
    }

    //оформление курьёр
    // registration order
    @GetMapping("/createOrder")
    public String createOrder(Model model, Principal principal, RedirectAttributes redirectAttributes,
                              HttpServletRequest request,
                              HttpServletResponse response
    ) {
        model = menuService.getMenuItems(model);

        User user = userService.findByUsername(principal.getName());

        Address address = new Address(user.getId(), user.getAddress().getCity(), user.getAddress().getStreet(), user.getAddress().getFlatNumber());

        model.addAttribute("address", address);

        Double basketSum = 0.0;

        if (user != null) {
            Basket basket = basketRepository.getOne(userService.findByUsername(principal.getName()).getId());

            for (Requests requests : basket.getRequestsList()) {
                basketSum += requests.getProducts().getFullPrice();
            }
            model.addAttribute("finalPrice", basket.getFinalPrice());
            model.addAttribute("basketSum", basketSum);
            System.out.println("basket sum create order0: " + basketSum);

            logger.info("finalPrice: " + basket.getFinalPrice());
        } else {
            model.addAttribute("finalPrice", "notPrice");
            model.addAttribute("basketSum", "nullmm");
            System.out.println("basket sum create order0: " + basketSum);
        }

        return "createOrder";
    }


    //выбор магазина
    //registration order
    @GetMapping("/storeSelection")
    public String storeSelection(Model model, Principal principal, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        if (request.getAttribute("basketSum") != null) {
            redirectAttributes.addFlashAttribute("basketSum", request.getAttribute("basketSum"));
        }

        model = menuService.getMenuItems(model); //get menu items

        return "store_selection";
    }


    @GetMapping("/orderIsProcessed")
    public String orderIsProcessed00(@RequestParam(name = "fb", required = false) String radioValue, Model model, Principal principal, HttpServletRequest request) {
        System.out.println("get orderIsProccessed");
        return "orderComplete";
    }

    //самовывоз
    //выбрать магазин и добавить нужный адрес в заказ
    //registration order
    //вывод значения с радиокнопки
//нужна транзакция
    @Transactional
    @PostMapping("/orderIsProcessed")
    public String orderIsProcessed(@RequestParam(name = "fb", required = false) String radioValue,
                                   @RequestParam(name = "basketSum", required = false) String basketSum,
                                   Model model, Principal principal, HttpServletRequest request) {

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
        finalOrder.setDate(new Date());
        finalOrder.setFinalPrice(basket.getFinalPrice());

        finalOrder.setUser(userService.findByUsername(principal.getName()));

        //установить флаг доставки, потому что самовывоз
        finalOrder.setIdDelivered(true);
        //установить флаг доставки, потому что самовывоз

        Double totalPrice = 0.0;

        for (Requests requests : basket.getRequestsList()) {
            totalPrice += requests.getProducts().getFullPrice();
        }
        finalOrder.setTotalPrice(totalPrice);
        System.out.println("TOTAL PRICE САМОВЫВОЗ: " + totalPrice);

        finalOrderRepository.save(finalOrder);
        finalOrderRepository.flush();

        //устонавливает finalOrder в requests и удаляет корзину из requests
        for (Requests rq : requestsList) {
            rq.setFinalOrder(finalOrder);
            rq.setBasket(null);
        }
        requestsRepository.saveAll(requestsList);

        //уменьшитль количество игр в наличии
        List<Products> productsList = new ReduceGameService().changeNumbOfGame(basket, productsRepository,false);
        productsRepository.saveAll(productsList);


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

        //вычесть сумму заказа у пользователя
        System.out.println("SUUUUUUMM ZAK: " + basketSum);

        User user = userService.findByUsername(principal.getName());
        if (user != null && basketSum != null) {
            Double basketSumDoub = Double.valueOf(basketSum);
            user.setBalance(user.getBalance() - basketSumDoub);
            userRepository.save(user);
            System.out.println("Деньги списаны самовывоз");
        }

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


    //курьер
    //оформление заказа если курьер
    //@Transactional
    @PostMapping("/completeСheckout")
    public String completeCheckout(@RequestParam(name = "city", required = false) String city,
                                   @RequestParam(name = "street", required = false) String street,
                                   @RequestParam(name = "flat_number", required = false) String flat_number,
                                   @RequestParam(name = "basketSum", required = false) String basketSum,
                                   Principal principal, Model model) throws ParseException {

        model = menuService.getMenuItems(model); //get menu items
        model.addAttribute("basketSum", basketSum);

        Basket basket = basketRepository.getOne(userService.findByUsername(principal.getName()).getId());

        List<Requests> requestsList = basket.getRequestsList();

        Long inxIns = 1L;
        List<FinalOrder> finalOrderList = finalOrderRepository.findAll();
        if (finalOrderList.size() > 0) {
            inxIns = finalOrderList.get(finalOrderList.size() - 1).getId() + 1;
        }

        FinalOrder finalOrder = new FinalOrder();
        finalOrder.setId(inxIns);

        Date date=new Date();
        System.out.println("get time completeСheckout: "+date.toString());
        System.out.println("get time completeСheckout: "+date.getTime());
        System.out.println("get time completeСheckout: "+date.getYear());
        System.out.println("get time completeСheckout: "+date.getMonth());
        System.out.println("get time completeСheckout: "+date.getHours());

        String formattedData=new SimpleDateFormat("yyyy:MM:dd hh:mm:ss").format(date);
        System.out.println("formattedData: "+formattedData);

        Date date2=new Date(new SimpleDateFormat("yyyy:MM:dd hh:mm:ss").parse(formattedData).getTime());
        System.out.println("date2: "+date2);

        finalOrder.setDate(date2);
        finalOrder.setFinalPrice(basket.getFinalPrice());
        finalOrder.setUser(userService.findByUsername(principal.getName()));

        Double totalPrice = 0.0;

        for (Requests requests : basket.getRequestsList()) {
            totalPrice += requests.getProducts().getFullPrice();
        }
        finalOrder.setTotalPrice(totalPrice);
        System.out.println("TOTAL PRICE КУРЬЕР: " + totalPrice);

        finalOrderRepository.save(finalOrder);

        List<Products> productsList = new ReduceGameService().changeNumbOfGame(basket, productsRepository,false);
        productsRepository.saveAll(productsList);

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

        //user отнять сумму
        System.out.println("basket sum: completecheckout: " + basketSum);

        User user = userService.findByUsername(principal.getName());
        if (user != null && basketSum != null) {
            Double basketSumDoub = Double.valueOf(basketSum);
            user.setBalance(user.getBalance() - basketSumDoub);
            userRepository.save(user);
            System.out.println("Деньги списаны");
        }

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

        double discountRateNew = 1.0;
        if (user.getDiscount_rate() != null) {
            discountRateNew = user.getDiscount_rate();
        }

        System.out.println("before: " + products.getOneDayPrice() * discountRateNew);
        System.out.println("before: " + Precision.round(products.getOneDayPrice() * discountRateNew, 2));


        requests.setNumberOfDays(Integer.parseInt(numberOfDays));
        requests.setPrice(Precision.round(products.getOneDayPrice() * discountRateNew, 2));
        requests.setProducts(products);

        //почему оно добавляется
//        requestsRepository.save(requests); //добавить request чтоб отображалось в корзине

        boolean trAdd = false;

        List<Requests> requestsList = requestsRepository.findAll();

        //TODO странно работает удаляет и добавляет заново, если такого нет
        //если такой товар уже есть и мы добавляем его снова и удаляем старый request
        if (requestsRepository.findAll().size() > 0) {
            for (int i = 0; i < requestsRepository.findAll().size(); i++) {
                if ((requestsList.get(i).getBasket() != null) && (requestsList.get(i).getBasket().getId() == requests.getBasket().getId()) &&
                        (requestsList.get(i).getProducts().getId() == requests.getProducts().getId())) {

                    Long oldID = requestsList.get(i).getId(); //получить старое id товара в request
                    logger.info("id old: " + oldID);
                    logger.info("((((Такая корзина и продукт уже есть уже есть");

                    requestsRepository.delete(requestsRepository.getOne(oldID)); //удалить старый request
                    //странное с id
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

        for (Requests value : requestsList) {
            //если request id совпадает с корзиной, то считаем общую сумму в корзине
            if ((value.getBasket() != null) && (value.getBasket().getId() == basket.getId())) {
                finalPrice += value.getPrice() * value.getNumberOfDays();
                counter++;
            }
        }
        logger.info("counter: " + counter);

        System.out.println("finalprice: " + finalPrice);
        finalPrice = Precision.round(finalPrice, 2);
        System.out.println("finalprice: " + finalPrice);

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

