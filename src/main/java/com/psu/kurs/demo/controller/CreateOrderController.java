package com.psu.kurs.demo.controller;

//import com.psu.kurs.demo.CityNameDTO;

import com.psu.kurs.demo.CityNameDTO;
import com.psu.kurs.demo.dao.*;
import com.psu.kurs.demo.entity.*;
import com.psu.kurs.demo.services.MenuService;
import com.psu.kurs.demo.services.OtherService;
import com.psu.kurs.demo.services.UserService;
import org.hibernate.Session;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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


    @GetMapping("/testdelivery")
    public @ResponseBody
    String testDelivery() {

        Long idL = 3L;

        AddressD addressD = new AddressD();
        addressD.setId(idL);
        addressD.setCity("nv");
        addressD.setFlatNumber("99");
        addressD.setStreet("street");
        addressDRepository.save(addressD);

        TypeOfDelivery typeOfDelivery = new TypeOfDelivery();
        typeOfDelivery.setId(idL);
        typeOfDelivery.setType("Курьер");
        typeOfDeliveryRepository.save(typeOfDelivery);

        Delivery delivery = new Delivery();
        delivery.setId(idL);
        delivery.setAddressD(addressDRepository.getOne(idL));
        delivery.setDate("date");
        delivery.setTypeOfDelivery(typeOfDelivery);
        deliveryRepository.save(delivery);

        FinalOrder finalOrder = finalOrderRepository.getOne(1L);
        finalOrder.setDelivery(delivery);
        finalOrderRepository.save(finalOrder);


//        TypeOfDelivery typeOfDelivery=new TypeOfDelivery();
//        typeOfDelivery.setId(2L);
//        typeOfDelivery.setType("Самовывоз");
//
//        typeOfDeliveryRepository.save(typeOfDelivery);

//        Delivery delivery=deliveryRepository.getOne(2L);
//        delivery.setTypeOfDelivery(typeOfDeliveryRepository.getOne(2L));
//        deliveryRepository.save(delivery);

        //////////////////////////////////////////////////////////////////////
        //        AddressD addressD = new AddressD();
//        addressD.setId(2L);
//        addressD.setCity("nv");
//        addressD.setFlatNumber("99");
//        addressD.setStreet("street");
//        addressDRepository.save(addressD);

//        Delivery delivery = new Delivery();
//        delivery.setId(2L);
//        delivery.setAddressD(addressDRepository.getOne(2L));
//        delivery.setDate("date");
//        deliveryRepository.save(delivery);

//        FinalOrder finalOrder = finalOrderRepository.getOne(1L);
//
//        logger.info(finalOrder.toString());
//
//        finalOrder.setDelivery(deliveryRepository.getOne(2L));
//        finalOrderRepository.save(finalOrder);


//        TypeOfDelivery typeOfDelivery=new TypeOfDelivery();
//        typeOfDelivery.setId(2L);
//        typeOfDelivery.setType("Самовывоз");
//
//        typeOfDeliveryRepository.save(typeOfDelivery);

//        Delivery delivery=deliveryRepository.getOne(2L);
//        delivery.setTypeOfDelivery(typeOfDeliveryRepository.getOne(2L));
//        deliveryRepository.save(delivery);
        //////////////////////////////////////////////////////////////////////


//
//        Delivery delivery = new Delivery();
//        delivery.setId(1L);
////        delivery.setAddressD();
//        delivery.setDate("date");
//        finalOrder.setDelivery(delivery);
//        delivery.setFinalOrder(finalOrder);
//
//        finalOrderRepository.save(finalOrder);
//        deliveryRepository.save(delivery);


        return "xmm";

    }


    //registration order
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

    //registration order
    @PostMapping("/createOrder")
    public String createOrder(Model model, Principal principal) {
        model = menuService.getMenuItems(model);

        User user = userService.findByUsername(principal.getName());

        Address address = new Address(user.getId(), user.getAddress().getCity(), user.getAddress().getStreet(), user.getAddress().getFlatNumber());
//        Address address = new Address(user.getId(), "Новополоцк", "Молодёжная 69", "420");

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

    //оформление заказа
    @PostMapping("/completeСheckout1")
    public @ResponseBody
    String completeCheckout1(Principal principal) {
        //тип доставки
        //тут поля
        FinalOrder finalOrder = finalOrderRepository.getOne(userService.findByUsername(principal.getName()).getId());

        Long idL = 100L;

        AddressD addressD = new AddressD();
        addressD.setId(idL);
        addressD.setCity("полоцк");
        addressD.setFlatNumber("420");
        addressD.setStreet("street");
        addressDRepository.save(addressD);

        //найти последнее в списке с id пользователя

        Delivery delivery = new Delivery();
        delivery.setId(idL); //нужно автомтически
        delivery.setAddressD(addressDRepository.getOne(idL));
        delivery.setDate("date");
        delivery.setTypeOfDelivery(typeOfDeliveryRepository.getOne(2L));


        deliveryRepository.save(delivery);

        finalOrder.setDelivery(delivery);

        finalOrderRepository.save(finalOrder);


        return "vot tak vot";
    }


    //registration order
    //выбор магазина
    @GetMapping("/storeSelection")
    public String storeSelection(Model model, Principal principal) {

        model = menuService.getMenuItems(model); //get menu items


        return "store_selection";
    }

    //выбрать кнопку и добавить нужный адрес
    //registration order
    //вывод значения с радиокнопки
    @PostMapping("/orderIsProcessed")
    public String orderIsProcessed(@RequestParam(name = "fb", required = false) String radioValue, Model model, Principal principal) {


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

        AddressD addressD0=null;
        if (radioValue.equals("obsh")) {
            addressD0 = addressDRepository.getOne(10001L);
        }else {
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
        delivery.setId(idL); //нужно автомтически
        delivery.setAddressD(addressD0);
        delivery.setDate(new Date().toString());
        delivery.setTypeOfDelivery(typeOfDeliveryRepository.getOne(1L));

        deliveryRepository.save(delivery);

        finalOrder.setDelivery(delivery);

        finalOrderRepository.save(finalOrder);

        return "orderComplete";
    }


    //хз что делает
    //TODO можно написать запрос sql sum
    @GetMapping("/createOrder1")
    public @ResponseBody
    String createOrder1(Principal principal) {

        User user = userService.findByUsername(principal.getName());

        FinalOrder finalOrder = new FinalOrder();

        finalOrder.setId(user.getId());
        finalOrder.setDate("new data");

        Basket basket = basketRepository.getOne(user.getId());

        List<Requests> requestsList = requestsRepository.findAll();

        double finalPrice = 0;
        for (Requests value : requestsList) {
            if (value.getBasket().getId() == basket.getId()) {
                finalPrice += value.getPrice() * value.getNumberOfDays();
            }
        }
        basket.setFinalPrice(finalPrice);

        basketRepository.save(basket);

        finalOrderRepository.save(finalOrder);

        //выборка requests и выбор цены


        return "userid: " + user.getId();
    }


    //registration order
    @GetMapping("/delProdBask/{id}")
    public String delProdBasket(@PathVariable(value = "id", required = false) String id, Principal principal) {

        User user = userService.findByUsername(principal.getName());

        if (requestsRepository.existsById(Long.valueOf(id))) {
            requestsRepository.deleteById(Long.valueOf(id));

            List<Requests> requestsList = requestsRepository.findAll();
            Basket basket = basketRepository.getOne(user.getId());
            double finalPrice = 0;
            for (Requests value : requestsList) {
                if (value.getBasket().getId() == basket.getId()) {
                    finalPrice += value.getPrice() * value.getNumberOfDays();
                }
            }

            FinalOrder finalOrder = finalOrderRepository.getOne(user.getId());

            basket.setFinalPrice(finalPrice);
            finalOrder.setFinalPrice(finalPrice);

            basketRepository.save(basket);
            finalOrderRepository.save(finalOrder);
        } else {
            logger.info("product doesn't exist" + id);
        }

        return "redirect:/basket";
    }

//    public class CityNameDTO {
//
//        public CityNameDTO() {
//        }
//
//        public CityNameDTO(String city) {
//            this.city = city;
//        }
//
//
//        private String city;
//
//        public String getCity() {
//            return city;
//        }
//
//        public void setCity(String city) {
//            this.city = city;
//        }
//
//        @Override
//        public String toString() {
//            return "CityNameDTO{" +
//                    "city='" + city + '\'' +
//                    '}';
//        }
//    }

    @PersistenceContext
    private EntityManager entityManager;

    //registration order
    @GetMapping("/basket")
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String basket(Model model, Principal principal, HttpServletRequest request) {

        Session session = null;
        if (entityManager == null
                || (session = entityManager.unwrap(Session.class)) == null) {

            throw new NullPointerException();
        }

//        System.out.println("No persons: " +
//                session.createSQLQuery("select count(number_of_days) as c from cursovaya.requests")
//                        .addScalar("c", IntegerType.INSTANCE)
//                        .uniqueResult());

//        List<Object[]> listReq=session.createSQLQuery("select rq.id,rq.number_of_days from cursovaya.requests as rq").list();
//        List<Object[]> listPlatformm = session.createSQLQuery("select pl.id,pl.name from cursovaya.platforms as pl").list();

//        System.out.println("size qer: "+listReq.size());
//        System.out.println("size qer: " + listPlatformm.size());
//
//
//        for (Object[] p : listPlatformm) {
//            System.out.println("id: " + p[0] + " name: " + p[1]);
//        }
//
//        List<Platforms> platformsList11 = session.createSQLQuery("select p.* from cursovaya.platforms as p")
//                .addEntity(Platforms.class)
//                .list();
//
//        for (Platforms pl : platformsList11) {
//            System.out.println("cpu: " + pl.getCpu());
//        }

//        List<Object[]> obj = session.createSQLQuery("select addr.city,addr.flat_number from cursovaya.delivery as deliv" +
//                " join cursovaya.addressd as addr on deliv.addressd_id = addr.id and deliv.id=4").list();
        List<Object[]> obj = session.createSQLQuery("select addr.city,addr.flat_number from cursovaya.delivery as deliv" +
                " join cursovaya.addressd as addr on deliv.addressd_id = addr.id and deliv.id=:id").setInteger("id", 3).
                list();


        System.out.println("siz: " + obj.size());

        for (Object[] p : obj) {
            System.out.println("city: " + p[0] + " flatNumber: " + p[1]);
        }


//        @AllArgsConstructor
//        @NoArgsConstructor

//TODO
        CityNameDTO cityNameDTO = new CityNameDTO();
        cityNameDTO.city = "cit";
        System.out.println(cityNameDTO.toString());

//        session.createSQLQuery("select adr.id,adr.city from cursovaya.addressd as adr")
//                .setResultTransformer(Transformers.aliasToBean(CityNameDTO.class))
//                .list()
//                .forEach(System.out::println);


        session.getNamedQuery("findCityById").setParameter("id", 2).list().forEach(System.out::println);


//
//        List<Object[]> listOBj= session.createSQLQuery("select adr.id,adr.city from cursovaya.addressd as adr").list();
//
//
//        for (Object[] p : listOBj) {
//            System.out.println("id: " + p[0].getClass() + " city: " + p[1]);
//        }


//                .forEach(System.out::println);


//        session.createQuery("FROM user");


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
//        model.addAttribute("reL",22);

        model.addAttribute("currentURL", otherService.getCurrentUrl(request));
        model.addAttribute("finalPrice", basket.getFinalPrice());

        return "basket";
    }

    //оформление заказа
//    @Transactional
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


        //тип доставки
        //тут поля
//        FinalOrder finalOrder = finalOrderRepository.getOne(userService.findByUsername(principal.getName()).getId());

        //возможно вставить получение последнего id
//        Long idL = 100L;

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
        delivery.setId(idL); //нужно автомтически
        delivery.setAddressD(addressDRepository.getOne(idAd));
        delivery.setDate("date");
        delivery.setTypeOfDelivery(typeOfDeliveryRepository.getOne(2L));


        deliveryRepository.save(delivery);

        finalOrder.setDelivery(delivery);

        finalOrderRepository.save(finalOrder);


//        return "vot tak vot " + city + " " + street + " " + flat_number;

        return "orderComplete";
    }


    @GetMapping("/tesFin")
    public @ResponseBody
    String tesFin() {
        List<FinalOrder> finalOrderList = finalOrderRepository.findAll();
        logger.info(finalOrderList.get(finalOrderList.size() - 1).toString());
        return finalOrderList.get(finalOrderList.size() - 1).toString();
    }

    //registration order
    //сохранить в корзину
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

//        FinalOrder finalOrder = null;

        if (basketRepository.existsById(user.getId())) {
            basket = basketRepository.getOne(user.getId());
        } else {
            basket = new Basket();
            basket.setId(user.getId());
            basketRepository.save(basket);
        }

//        if (finalOrderRepository.existsById(user.getId())) {
//            finalOrder = finalOrderRepository.getOne(user.getId());
//        } else {
//            finalOrder = new FinalOrder();
//            finalOrder.setId(user.getId());
//            finalOrderRepository.save(finalOrder);
//        }

        requests.setBasket(basket);

        requests.setNumberOfDays(Integer.parseInt(numberOfDays));
        requests.setPrice(products.getOneDayPrice());
        requests.setProducts(products);
//        requests.setFinalOrder(finalOrder);

        requestsRepository.save(requests); //для заполнения таблицы

        boolean trAdd = false;

        List<Requests> requestsList = requestsRepository.findAll();

        if (requestsRepository.findAll().size() > 0) {
            for (int i = 0; i < requestsRepository.findAll().size(); i++) {
                if ((requestsList.get(i).getBasket() != null) && (requestsList.get(i).getBasket().getId() == requests.getBasket().getId()) &&
                        (requestsList.get(i).getProducts().getId() == requests.getProducts().getId())) {

                    Long oldID = requestsList.get(i).getId();
                    logger.info("id old: " + oldID);
                    logger.info("((((Такая корзина и продукт уже есть уже есть");

//                    basket.setFinalPrice(basket.getFinalPrice()-requestsRepository.getOne(oldID).getPrice());
                    basketRepository.save(basket);

                    requestsRepository.delete(requestsRepository.getOne(oldID));
//                    requestsRepository.deleteById(oldID);
//                    logger.info("drop the mic: " + oldID);
                    requestsRepository.save(requests);
//                    requestsRepository.saveAndFlush(requests);
//                    requestsRepository.deleteAllInBatch();

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
            //чёт тут
            if ((value.getBasket() != null) && (value.getBasket().getId() == basket.getId())) {
                finalPrice += value.getPrice() * value.getNumberOfDays();
                counter++;
            }
        }
        logger.info("counter: " + counter);
        basket.setFinalPrice(finalPrice);
//        finalOrder.setFinalPrice(finalPrice);
        basketRepository.save(basket);

//        finalOrder.setDate(simpleDateFormat.format(new Date()));
//        finalOrderRepository.save(finalOrder);
        logger.info("currentURL: " + currentURL);

        if (currentURL == null) {
            return "redirect:/";
        } else {
            return "redirect:/" + currentURL;
        }

    }


    //registration order
    //сохранить в корзину
    @PostMapping("/testDB1/{id}")
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String testDB1(@PathVariable("id") String id, @RequestParam(name = "currentURL", required = false) String currentURL, @RequestParam("inputplus") String numberOfDays, Model model, Principal principal) {

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

        FinalOrder finalOrder = null;

        if (basketRepository.existsById(user.getId())) {
            basket = basketRepository.getOne(user.getId());
        } else {
            basket = new Basket();
            basket.setId(user.getId());
            basketRepository.save(basket);
        }

        if (finalOrderRepository.existsById(user.getId())) {
            finalOrder = finalOrderRepository.getOne(user.getId());
        } else {
            finalOrder = new FinalOrder();
            finalOrder.setId(user.getId());
            finalOrderRepository.save(finalOrder);
        }

        requests.setBasket(basket);

        requests.setNumberOfDays(Integer.parseInt(numberOfDays));
        requests.setPrice(products.getOneDayPrice());
        requests.setProducts(products);
        requests.setFinalOrder(finalOrder);

        requestsRepository.save(requests); //для заполнения таблицы

        boolean trAdd = false;

        List<Requests> requestsList = requestsRepository.findAll();

        if (requestsRepository.findAll().size() > 0) {
            for (int i = 0; i < requestsRepository.findAll().size(); i++) {
                if ((requestsList.get(i).getBasket().getId() == requests.getBasket().getId()) &&
                        (requestsList.get(i).getProducts().getId() == requests.getProducts().getId())) {

                    Long oldID = requestsList.get(i).getId();
                    logger.info("id old: " + oldID);
                    logger.info("((((Такая корзина и продукт уже есть уже есть");

                    basketRepository.save(basket);

                    requestsRepository.deleteById(oldID);
                    logger.info("drop the mic: " + oldID);
                    requestsRepository.save(requests);
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

        double finalPrice = 0;
        for (Requests value : requestsList) {
            if (value.getBasket().getId() == basket.getId()) {
                finalPrice += value.getPrice() * value.getNumberOfDays();
            }
        }
        basket.setFinalPrice(finalPrice);
        finalOrder.setFinalPrice(finalPrice);
        basketRepository.save(basket);

        finalOrder.setDate(simpleDateFormat.format(new Date()));
        finalOrderRepository.save(finalOrder);
        logger.info("currentURL: " + currentURL);

        if (currentURL == null) {
            return "redirect:/";
        } else {
            return "redirect:/" + currentURL;
        }

    }

}

