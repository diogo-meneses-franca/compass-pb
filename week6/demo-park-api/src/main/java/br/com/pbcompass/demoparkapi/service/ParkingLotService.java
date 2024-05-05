package br.com.pbcompass.demoparkapi.service;

import br.com.pbcompass.demoparkapi.entity.ParkClient;
import br.com.pbcompass.demoparkapi.entity.Parking;
import br.com.pbcompass.demoparkapi.entity.ParkingSpace;
import br.com.pbcompass.demoparkapi.utils.ParkingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParkingLotService {

    private final ParkingService parkingService;
    private final ParkClientService parkClientService;
    private final ParkingSpaceService parkingSpaceService;

    public Parking checkin(Parking parking){
        ParkClient client = parkClientService.findByCpf(parking.getClient().getCpf());
        parking.setClient(client);
        ParkingSpace space = parkingSpaceService.findFirstByStatusFree();
        space.setStatus(ParkingSpace.ParkingSpaceStatus.OCCUPIED);
        parking.setParkingSpace(space);
        parking.setCheckin(LocalDateTime.now());
        parking.setInvoice(ParkingUtils.generateInvoice());
        return parkingService.save(parking);
    }

    public Parking checkout(String invoice) {
        Parking parking = parkingService.findByInvoice(invoice);
        LocalDateTime checkoutTime = LocalDateTime.now();
        parking.setCheckout(checkoutTime);
        BigDecimal cost = ParkingUtils.calculateCost(parking.getCheckin(), checkoutTime);
        parking.setValue(cost);
        long parkingTimes = parkingService.getHowManyTimesTheClientMadeACompleteParking(parking.getClient().getCpf());
        BigDecimal discount = ParkingUtils.calculateDiscount(cost, parkingTimes);
        parking.setDiscount(discount);
        parking.getParkingSpace().setStatus(ParkingSpace.ParkingSpaceStatus.FREE);
        return parkingService.save(parking);


    }
}
