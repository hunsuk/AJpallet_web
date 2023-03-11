package com.example.azprojectpallet.controller;


import com.example.azprojectpallet.domain.*;
import com.example.azprojectpallet.dto.GetDataPallet;
import com.example.azprojectpallet.dto.GetPalletIndex;
import com.example.azprojectpallet.dto.SendMapInfo;
import com.example.azprojectpallet.dto.SendReservationInfo;
import com.example.azprojectpallet.repo.*;
import com.example.azprojectpallet.service.PalletService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping
public class RestApiController {
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

    @GetMapping("/startInit")
    public String startPage(ServletRequest servletRequest){
        Device currentDevice = DeviceUtils.getCurrentDevice((HttpServletRequest) servletRequest);
        User user = userRepository.findByEmail("daw916@naver.com").get();
        RentItem sendRentItem = new RentItem();
        sendRentItem.setRentAccount(user);
        sendRentItem.setRentStartDay(Date.valueOf("2023-01-26"));
        sendRentItem.setRentReturnDay(Date.valueOf("2023-01-30"));
        sendRentItem.setManagePart("경기지점");
        sendRentItem.setPosition("37.623010237639974,126.8022433746701");
        sendRentItem.setPalletId("A11-1");
        sendRentItem.setStatus(PalleItemState.Sending);

        RentItem sendRentItem2 = new RentItem();
        sendRentItem2.setRentAccount(user);
        sendRentItem2.setRentStartDay(Date.valueOf("2023-01-26"));
        sendRentItem2.setRentReturnDay(Date.valueOf("2023-01-30"));
        sendRentItem2.setManagePart("경기지점");
        sendRentItem2.setPalletId("A12-1");
        sendRentItem2.setPosition("37.69433943042704,126.82049939110969");
        sendRentItem2.setStatus(PalleItemState.Sending);

        RentItem sendRentItem3 = new RentItem();
        sendRentItem3.setRentAccount(user);
        sendRentItem3.setRentStartDay(Date.valueOf("2023-01-26"));
        sendRentItem3.setRentReturnDay(Date.valueOf("2023-01-30"));
        sendRentItem3.setManagePart("경기지점");
        sendRentItem3.setPalletId("A12-2");
        sendRentItem3.setPosition("37.597135998683015,126.85897533741162");
        sendRentItem3.setStatus(PalleItemState.Missing);

        RentItem sendRentItem4 = new RentItem();
        sendRentItem4.setRentAccount(user);
        sendRentItem4.setRentStartDay(Date.valueOf("2023-01-26"));
        sendRentItem4.setRentReturnDay(Date.valueOf("2023-01-30"));
        sendRentItem4.setManagePart("경기지점");
        sendRentItem4.setPalletId("A12-3");
        sendRentItem4.setPosition("37.5801957594563,126.90406970064154");
        sendRentItem4.setStatus(PalleItemState.Missing);

        Truck truck = new Truck();
        truck.setCarNumber("가13432");
        truckRepository.save(truck);

        Truck truck2 = new Truck();
        truck2.setCarNumber("나32332");
        truckRepository.save(truck2);

        Spot spot = new Spot();
        spot.setName("경기지점");
        spotRepository.save(spot);

        Spot spot2 = new Spot();
        spot2.setName("서울지점");
        spotRepository.save(spot2);

        Reservation reservation = new Reservation();
        reservation.setOrderAccount(user);
        reservation.setStatus("접수중");
        reservation.setRentStartDate(Date.valueOf("2023-01-29"));
        reservation.setRentEndDate(Date.valueOf("2023-01-31"));
        reservation.setOrderPallet("A11,21,B12,10");
        reservation.setOrderTruck(truck);
        reservation.setManageSpot(spot);

        Reservation reservation2 = new Reservation();
        reservation2.setOrderAccount(user);
        reservation2.setStatus("접수중");
        reservation2.setRentStartDate(Date.valueOf("2023-02-06"));
        reservation2.setRentEndDate(Date.valueOf("2023-02-12"));
        reservation2.setOrderPallet("A11,21,B12,10");
        reservation2.setOrderTruck(truck2);
        reservation2.setManageSpot(spot2);


        rentItemRepository.save(sendRentItem);
        rentItemRepository.save(sendRentItem2);
        rentItemRepository.save(sendRentItem3);
        rentItemRepository.save(sendRentItem4);

        reservationRepository.save(reservation);
        reservationRepository.save(reservation2);

        if (!currentDevice.isNormal()) {
            log.info("Hello desktop user!");
        }

        return "ok init";
    }


    @GetMapping("/getSendingPallet")
    public SendMapInfo getSendingPallet(@AuthenticationPrincipal User user){
        List<RentItem> rentItems = new ArrayList<>();
        if (!user.getAuth().toString().equals("ROLE_USER")){
            List<User> users= userRepository.findAll();
            for (int i = 0; i < users.size(); i++){
                List<RentItem>rentTemp = rentItemRepository.findByRentAccount(users.get(i));
                for (int j  = 0; j < rentTemp.size(); j++){
                    rentItems.add(rentTemp.get(j));
                }
            }
        }else{
            rentItems = rentItemRepository.findByRentAccount(user);
        }

        List<String> latitudes = new ArrayList<>();
        List<String> hardness = new ArrayList<>();
        List<String> id = new ArrayList<>();
        List<Integer> status = new ArrayList<>();

        for (int i = 0; i < rentItems.size(); i++){
            String[] index = rentItems.get(i).getPosition().split(",");
            latitudes.add(index[0]);
            hardness.add(index[1]);
        }

        for (int i = 0; i < rentItems.size(); i++){
            id.add(rentItems.get(i).getPalletId());
            status.add(rentItems.get(i).getStatus().ordinal());
        }

        return new SendMapInfo(latitudes,hardness,status,id);
    }


    @GetMapping("/getRservationPallet")
    public SendReservationInfo getReservationPallet(@AuthenticationPrincipal User user){
        List<Reservation> reservations = reservationRepository.findByOrderAccount(user);
        List<String> startDates = new ArrayList<>();
        List<String> endDates = new ArrayList<>();
        List<String> orderDates = new ArrayList<>();
        List<Long> id = new ArrayList<>();
        for (int i = 0; i < reservations.size(); i++){

            startDates.add(reservations.get(i).getRentStartDate().toString());
            endDates.add(reservations.get(i).getRentEndDate().toString());
            orderDates.add(reservations.get(i).getOrderPallet());
            id.add(reservations.get(i).getId());
        }
        return new SendReservationInfo(startDates,endDates,orderDates,id);
    }

    @GetMapping("/delReservation")
    public void DelReservation(@AuthenticationPrincipal User user,@RequestParam(value = "id") String id){
        List<Reservation> reservations = reservationRepository.findByOrderAccount(user);

        for (int i = 0; i < reservations.size(); i++){
            if (reservations.get(i).getId() == Long.valueOf(id)){
                reservationRepository.delete(reservations.get(i));
            }
        }
    }

    @GetMapping("/getPalletInfo")
    public String GetPalletInfo(@RequestParam("id") String id){
        log.info("enter the request pallet Info: " + id);
            RentItem rentItem = rentItemRepository.findByPalletId(id);
        log.info(rentItem.getPosition());
        log.info(rentItem.getPalletId());
        log.info(rentItem.getStatus().toString());
        return rentItem.getPosition() + "," + rentItem.getPalletId() + "," + rentItem.getStatus().toString();
    }

    @GetMapping("/getIndexPallet")
    public void GetIndexPallet(@RequestParam("lat") String lat , @RequestParam("lon") String lon, @RequestParam("id") String id){
        log.info("enter the index pallet");
        log.info(lat + "/" + lon + "/" + id);
        RentItem rentItem = rentItemRepository.findByPalletId(id);
        rentItem.setPosition(lat + "," + lon);
        rentItemRepository.save(rentItem);
    }

    @PostMapping("/getPalletAdd")
    public String GetPalletAdd(@RequestBody GetDataPallet param){
        System.out.println(param.getId());
        System.out.println(param.getManage());
        ArrayList<String> id = param.getId();
        ArrayList<String> manager = param.getManage();

        for (int i = 0; i < id.size(); i++){
            RentItem rentItem = new RentItem();
            rentItem.setPalletId(id.get(i));
            rentItem.setManagePart(manager.get(i));
            rentItemRepository.save(rentItem);
        }
        return "ok";
    }


}
