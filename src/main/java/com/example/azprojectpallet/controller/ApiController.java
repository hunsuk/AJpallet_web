package com.example.azprojectpallet.controller;


import com.example.azprojectpallet.domain.*;
import com.example.azprojectpallet.dto.GetReservationInfo;
import com.example.azprojectpallet.dto.SendAdminReservationInfo;
import com.example.azprojectpallet.dto.SendReservationInfo;
import com.example.azprojectpallet.dto.SendReservationType;
import com.example.azprojectpallet.repo.*;
import com.example.azprojectpallet.service.PalletService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Controller
public class ApiController {

    @Autowired
    private PalletRepository palletRepository;

    @Autowired
    PalletService palletService;
    @Autowired
    RentItemRepository rentItemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    TruckRepository truckRepository;

    @Autowired
    SpotRepository spotRepository;


    @GetMapping("/test1")
    public String test1(){
        return "mobile/preloading";
    }
    @GetMapping("/test2")
    public String test2(){
        return "mobile/login";
    }
    @GetMapping("/test3")
    public String test3(){
        return "mobile/signup";
    }
    @GetMapping("/test4")
    public String test4(){ return "mobile/main";}
    @GetMapping("/test5")
    public String test5(){
        return "mobile/item";
    }
    @GetMapping("/test6")
    public String test6(){
        return "mobile/cart";
    }
    @GetMapping("/test7")
    public String test7(){
        return "mobile/mypage";
    }


    @GetMapping("/start")
    public String startPage(ServletRequest servletRequest){
        return "start";
    }
    @GetMapping("/login")
    public String loginPage(Model model){

        return "login";
    }

    @GetMapping("/signup")
    public String singupPage(){
        return "signup";
    }
    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal User user, Model model){
        if (!user.getAuth().toString().equals("ROLE_USER")){

            return "adminDash/adminMainDash";
        }


        List<RentItem> rentItems = rentItemRepository.findByRentAccount(user);
        for (int i = 0; i < rentItems.size(); i++){
            System.out.println(rentItems.get(i).getPosition());
        }
        model.addAttribute("userName",user.getCorpName()+" "+user.getManagerName());
        model.addAttribute("userAuth",user.getAuth());
        return "main";
    }

    @GetMapping("/orderStatus")
    public String orderStatus(Model model){
        PageRequest pageRequest = PageRequest.of(0,10);
        List<Reservation> reservations= reservationRepository.findAll(pageRequest).getContent();
        List<SendAdminReservationInfo> reservationInfos = new ArrayList<>();

        for (int i = 0; i < reservations.size(); i++){
            SendAdminReservationInfo temp = new SendAdminReservationInfo();
            temp.setId(reservations.get(i).getId());
            temp.setCustomer(reservations.get(i).getOrderAccount().getCorpName());
            temp.setSpot(reservations.get(i).getManageSpot().getName());
            temp.setStatus(reservations.get(i).getStatus());
            temp.setOrderDay(reservations.get(i).getCreatedDate().toString().substring(0,10));
            temp.setGetDay(reservations.get(i).getRentStartDate().toString());
            temp.setReturnDay(reservations.get(i).getRentEndDate().toString());
            temp.setTruckNumber(reservations.get(i).getOrderTruck().getCarNumber());
            temp.setOrderIntent(reservations.get(i).getOrderPallet());

            reservationInfos.add(temp);
        }

        model.addAttribute("orders",reservationInfos);
        return "adminDash/adminOrderManageDashboard";
    }
    @GetMapping("/orderDetail")
    public String orderDetail(@RequestParam(value="id") String id, Model model){
        System.out.println(id);
        if(id.equals("2")) {
            model.addAttribute("orderModify","null");

        }
        if(id.equals("1")){
            model.addAttribute("orderModify","2023.1.17,2023.1.19");
        }
        return "adminDash/adminOrderDetailDashboard";
    }

    @PostMapping("/requestDetail")
    public String requestDetail(@AuthenticationPrincipal User user, @ModelAttribute(value = "select_pallet") String select_pallet, Model model){
        System.out.println(select_pallet);
        String[] parm = select_pallet.split(",");
        List<SendReservationType> pallets =new ArrayList<>();
        for (int i = 0; i < parm.length; i++){
             Pallet pallet = palletRepository.findByFront(parm[i]).get();
             pallets.add(new SendReservationType(pallet.getId().toString(),pallet.getImg_uri(),pallet.getFront()));

        }
        System.out.println(pallets.size());


        model.addAttribute("pallets",pallets);
        model.addAttribute("userName",user.getManagerName());
        return "reservationBuket";
    }

    @PostMapping("/requestReservationSend")
    public String requestReservationSend(@AuthenticationPrincipal User user, @ModelAttribute GetReservationInfo reservationInfo){
        Truck truck = truckRepository.getReferenceById(1L);
        Spot spot = spotRepository.getReferenceById(3L);

        for (int i = 0; i < reservationInfo.getChecked().split(",").length; i++){
            Reservation reservation = new Reservation();

            reservation.setOrderAccount(user);

            reservation.setStatus("접수중");

            reservation.setRentStartDate(Date.valueOf(reservationInfo.getStartDay().split(",")[i]));

            reservation.setRentEndDate(Date.valueOf(reservationInfo.getEndDay().split(",")[i]));
            reservation.setManageSpot(spot);
            reservation.setOrderTruck(truck);


            System.out.println(reservationInfo.getCount().split(",")[i].equals(""));
            String count = reservationInfo.getCount().split(",")[i].equals("")  ? "1" : reservationInfo.getCount().split(",")[i];

            String order = palletRepository.findById(Long.valueOf(reservationInfo.getChecked().split(",")[i])).get().getName()+ "," + count;
            reservation.setOrderPallet(order);

            reservationRepository.save(reservation);
        }

        return "redirect:/";
    }

    @GetMapping("/deliverStatus")
    public String deliverStatus(){

        return "adminDash/adminDeliverManageDashboard";
    }

    @GetMapping("/deliverDetail")
    public String deliverDetail(@RequestParam(value="id") String id, Model model) {
        System.out.println(id);

        return "adminDash/adminDeliverDetailDashboard";
    }

    @GetMapping("/addPallet")
    public String addPallet(){
        return "adminDash/adminAddPallet";
    }

    @GetMapping("/returnList")
    public String returnList(@RequestParam(value="id")String id, Model model){
        System.out.println(id);

        return "adminDash/adminReturnDashboard";
    }

    @GetMapping("/dashMain")
    public String dashMain(){
        return "adminDash/adminMainDash";
    }

    @GetMapping("/myPage")
    public String myPage(@AuthenticationPrincipal User user ,Model model){
        model.addAttribute("user_info",user);
        return "Myinfo";
    }

    @GetMapping("/check_my_request")
    public String checkMyRequest(@AuthenticationPrincipal User user,Model model) throws UnsupportedEncodingException {
        List<Pallet> pallets = palletService.findAll();
        model.addAttribute("userAuth",user.getAuth());
        model.addAttribute("palletItem_list",pallets);
        return "CheckPalletRequest";
    }

    @GetMapping("/publish_req")
    public String publish_req(@AuthenticationPrincipal User user,Model model) throws UnsupportedEncodingException {
        List<Pallet> pallets = palletService.findAll();
        model.addAttribute("userAuth",user.getAuth());
        model.addAttribute("palletItem_list",pallets);
        return "PalletRequest";
    }

    @GetMapping("/dashboard_admin")
    public String dashboard(Model model){

        return "adminDashboard";
    }
    @GetMapping("/request_check_popup")
    public String request_check_popup(){

        return "popup/Request_Check_Popup";
    }

}
