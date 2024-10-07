package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.dto.FavoriteDriverDTO;
import com.QuickGo.backend.dto.TripRequestDetailDTO;
import com.QuickGo.backend.dto.common.ResponseMessage;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.models.FavoriteDriver;
import com.QuickGo.backend.models.Trip;
import com.QuickGo.backend.models.User;
import com.QuickGo.backend.repository.FavoriteDriverRepository;
import com.QuickGo.backend.repository.TripRepository;
import com.QuickGo.backend.repository.UserRepository;
import com.QuickGo.backend.service.MailService;
import com.QuickGo.backend.service.TripService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    TripRepository tripRepository;
    @Autowired
    FavoriteDriverRepository favoriteDriverRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> saveTripRequest(TripRequestDetailDTO requestDetailDTO) throws Exception {

        if (requestDetailDTO.getPassengerCode() == null || requestDetailDTO.getPassengerCode().isEmpty()) {
            throw new CustomException("Passenger code cannot be empty.");
        }
        if (requestDetailDTO.getDriveCode() == null || requestDetailDTO.getDriveCode().isEmpty()) {
            throw new CustomException("Driver code cannot be empty.");
        }
        if (requestDetailDTO.getPickupLat() == 0 || requestDetailDTO.getPickupLng() == 0) {
            throw new CustomException("Pickup location must have valid coordinates.");
        }
        if (requestDetailDTO.getDropLat() == 0 || requestDetailDTO.getDropLng() == 0) {
            throw new CustomException("Drop location must have valid coordinates.");
        }
        List<Trip> passengerRequests = tripRepository.findTripByPassengerCodeAndStatus(requestDetailDTO.getPassengerCode(), "REQUEST");
        if (!passengerRequests.isEmpty())
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.TOO_MANY_REQUESTS.value(), "You have already requested a driver"), HttpStatus.OK);


        // Map DTO to entity
        Trip trip = modelMapper.map(requestDetailDTO, Trip.class);

        userRepository.findByUserCodeIn(List.of(requestDetailDTO.getPassengerCode(), requestDetailDTO.getDriveCode()))
                .forEach(user -> {
                    if (user.getUserCode().equals(requestDetailDTO.getPassengerCode())) {
                        trip.setPassenger(user);
                    } else if (user.getUserCode().equals(requestDetailDTO.getDriveCode())) {
                        trip.setDriver(user);
                    }
                });

        trip.setCreateDateTime(new Date());
        trip.setUpdateDateTime(new Date());
        trip.setStatus("REQUEST");
        trip.setIsActive(1);


        Trip savedTrip = tripRepository.save(trip);
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", savedTrip), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> saveFavoriteDriver(FavoriteDriverDTO favoriteDriverDTO) throws Exception {
        if (favoriteDriverDTO.getPassengerCode() == null || favoriteDriverDTO.getPassengerCode().isEmpty()) {
            throw new CustomException("Passenger code cannot be empty.");
        }
        if (favoriteDriverDTO.getDriverCode() == null || favoriteDriverDTO.getDriverCode().isEmpty()) {
            throw new CustomException("Driver code cannot be empty.");
        }


        Optional<FavoriteDriver> value = favoriteDriverRepository.findFavoriteDriverByPassengerCodeAndDriverCode(favoriteDriverDTO.getPassengerCode(), favoriteDriverDTO.getDriverCode());
        if (value.isPresent()) {
            value.get().setUpdateDateTime(new Date());
            if (value.get().getIsActive() == 0) {
                value.get().setIsActive(1);
            } else if (value.get().getIsActive() == 1) {
                value.get().setIsActive(0);
            }
            FavoriteDriver update = favoriteDriverRepository.save(value.get());
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", update), HttpStatus.OK);
        } else {
            FavoriteDriver driver = modelMapper.map(favoriteDriverDTO, FavoriteDriver.class);
            driver.setIsActive(1);
            driver.setCreateDateTime(new Date());

            FavoriteDriver save = favoriteDriverRepository.save(driver);
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", save), HttpStatus.OK);

        }


    }

    @Override
    public ResponseEntity<?> getDriverTrip(FavoriteDriverDTO favoriteDriverDTO) throws Exception {
        // Check if the driverCode or passengerCode is null or empty
        if (favoriteDriverDTO.getDriverCode() == null || favoriteDriverDTO.getDriverCode().isEmpty()) {
            throw new CustomException("Driver code cannot be empty.");
        }

        List<Trip> trips = tripRepository.findTripByDriveCodeAndStatus(favoriteDriverDTO.getDriverCode(), "REQUEST");
        if (trips == null || trips.isEmpty()) {
            throw new CustomException("No trips found for the provided driver.");
        }

        // Convert the list of Trip entities to TripRequestDetailDTO using Stream
        List<TripRequestDetailDTO> tripDTOs = trips.stream()
                .map(trip -> TripRequestDetailDTO.builder()
                        .tripID(trip.getTripID())
                        .passengerCode(trip.getPassengerCode())
                        .driveCode(trip.getDriveCode())
                        .totalAmount(trip.getTotalAmount())
                        .paymentMethod(trip.getPaymentMethod())
                        .status(trip.getStatus())
                        .driverComment(trip.getDriverComment())
                        .pickupLat(trip.getPickupLat())
                        .pickupLng(trip.getPickupLng())
                        .dropLat(trip.getDropLat())
                        .dropLng(trip.getDropLng())
                        .passengerComment(trip.getPassengerComment())
                        .createDateTime(trip.getCreateDateTime() + "")
                        .updateDateTime(trip.getUpdateDateTime() + "")
                        .isActive(trip.getIsActive())
                        .build())
                .collect(Collectors.toList());

        // Return the list of TripRequestDetailDTO in the ResponseEntity
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", tripDTOs), HttpStatus.OK);
    }

    @Override
    public ResponseMessage cancelTripRequest(TripRequestDetailDTO requestDetailDTO) {

        if (requestDetailDTO.getPassengerCode() == null || requestDetailDTO.getPassengerCode().isEmpty()) {
            return new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Passenger code cannot be empty.");
        }

        List<Trip> passengerRequests = tripRepository.findTripByPassengerCodeAndDriveCodeAndStatus(requestDetailDTO.getPassengerCode(), requestDetailDTO.getDriveCode(), "REQUEST");
        if (passengerRequests.isEmpty()) {
            return new ResponseMessage(HttpStatus.NOT_FOUND.value(), "You have not requested any driver.");
        }

        Trip trip = passengerRequests.get(0);
        trip.setStatus("CANCELLED");
        trip.setPassengerComment("Trip request cancelled by passenger");
        trip.setUpdateDateTime(new Date());
        tripRepository.save(trip);

        mailService.sendEmailAsync(trip.getPassenger().getEmail(), "Trip Cancellation Notice", generateTripCancelEmailBody(trip));
        return new ResponseMessage(HttpStatus.OK.value(), "Trip request cancelled successfully.");

    }

    @Override
    public ResponseMessage driverCancelTripRequest(TripRequestDetailDTO requestDetailDTO) {
        if (requestDetailDTO.getPassengerCode() == null || requestDetailDTO.getPassengerCode().isEmpty()) {
            return new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Passenger code cannot be empty.");
        }

        List<Trip> passengerRequests = tripRepository.findTripByPassengerCodeAndDriveCodeAndStatus(requestDetailDTO.getPassengerCode(), requestDetailDTO.getDriveCode(), "REQUEST");
        if (passengerRequests.isEmpty()) {
            return new ResponseMessage(HttpStatus.NOT_FOUND.value(), "You have not requested any passenger.");
        }

        Trip trip = passengerRequests.get(0);
        trip.setStatus("CANCELLED");
        trip.setPassengerComment("Trip request cancelled by driver");
        trip.setUpdateDateTime(new Date());
        tripRepository.save(trip);
        mailService.sendEmailAsync(trip.getPassenger().getEmail(), "Trip Cancellation Notice", generateTripCancelEmailBody(trip));
        return new ResponseMessage(HttpStatus.OK.value(), "Trip request cancelled successfully.");
    }

    @Override
    public ResponseMessage acceptTripRequest(TripRequestDetailDTO requestDetailDTO) throws CustomException {

        if (requestDetailDTO.getPassengerCode() == null || requestDetailDTO.getPassengerCode().isEmpty()) {
            return new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Passenger code cannot be empty.");
        }

        tripRepository
                .findTripByPassengerCodeAndDriveCodeAndStatus(requestDetailDTO.getPassengerCode(), requestDetailDTO.getDriveCode(), "REQUEST")
                .stream()
                .findFirst()
                .map(trip -> {
                    trip.setStatus("ACCEPTED");
                    trip.setDriverComment("Trip request accepted by driver");
                    trip.setUpdateDateTime(new Date());

                    Trip save = tripRepository.save(trip);

                    mailService.sendEmailAsync(
                            trip.getPassenger().getEmail(),
                            "Trip request accepted",
                            generateEmailBody(trip)
                    );
                    return save;
                })
                .orElseThrow(() -> new CustomException("You have not requested any driver."));

        List<Trip> driverRequests = tripRepository.findTripByDriveCodeAndStatus(requestDetailDTO.getDriveCode(), "REQUEST")
                .stream()
                .peek(driverRequest -> {
                    driverRequest.setStatus("CANCELLED");
                    driverRequest.setDriverComment("Trip request cancelled by system due to accepting another trip request");
                    driverRequest.setUpdateDateTime(new Date());
                })
                .collect(Collectors.toList());

        tripRepository.saveAll(driverRequests);

        return new ResponseMessage(HttpStatus.OK.value(), "Trip request accepted successfully.");
    }

    @Override
    public ResponseMessage endTripRequest(TripRequestDetailDTO requestDetailDTO) {

        if (requestDetailDTO.getPassengerCode() == null || requestDetailDTO.getPassengerCode().isEmpty()) {
            return new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Passenger code cannot be empty.");
        }

        List<Trip> passengerRequests = tripRepository.findTripByPassengerCodeAndDriveCodeAndStatus(requestDetailDTO.getPassengerCode(), requestDetailDTO.getDriveCode(), "ACCEPTED");
        if (passengerRequests.isEmpty()) {
            return new ResponseMessage(HttpStatus.NOT_FOUND.value(), "You have not accepted any trip request.");
        }

        Trip trip = passengerRequests.get(0);

        if (!"ACCEPTED".equals(trip.getStatus())) {
            return new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "You have not accepted any trip request.");
        }

        trip.setStatus("COMPLETED");
        trip.setPassengerComment("Trip completed successfully");
        trip.setUpdateDateTime(new Date());
        tripRepository.save(trip);

        return new ResponseMessage(HttpStatus.OK.value(), "Trip completed successfully.");
    }

    public String generateEmailBody(Trip trip) {
        User driver = trip.getDriver();

        String emailTemplate = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Trip Request Accepted</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 0;
                        }
                        .container {
                            background-color: #ffffff;
                            width: 80%%;
                            max-width: 600px;
                            margin: 20px auto;
                            padding: 20px;
                            border-radius: 10px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                        }
                        .header {
                            background-color: #007bff;
                            color: #ffffff;
                            text-align: center;
                            padding: 10px;
                            border-top-left-radius: 10px;
                            border-top-right-radius: 10px;
                        }
                        .header h1 {
                            margin: 0;
                            font-size: 24px;
                        }
                        .content {
                            margin: 20px 0;
                            line-height: 1.6;
                        }
                        .content p {
                            margin: 10px 0;
                        }
                        .footer {
                            text-align: center;
                            color: #666666;
                            font-size: 14px;
                            padding-top: 10px;
                            border-top: 1px solid #dddddd;
                        }
                        .footer p {
                            margin: 5px 0;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>Trip Request Accepted</h1>
                        </div>
                        <div class="content">
                            <p>Dear Passenger,</p>
                            <p>Your trip request has been accepted by the driver.</p>
                            <p>
                                <strong>Driver:</strong> %s<br>
                                <strong>Vehicle Number:</strong> %s<br>
                                <strong>Vehicle Type:</strong> %s<br>
                                <strong>Vehicle Color:</strong> %s<br>
                                <strong>Driver Contact Number:</strong> %s<br>
                            </p>
                            <p>Thank you for choosing our service!</p>
                        </div>
                        <div class="footer">
                            <p>&copy; 2024 Your Company Name. All rights reserved.</p>
                        </div>
                    </div>
                </body>
                </html>
                """;

        return emailTemplate.formatted(
                driver.getName(),
                driver.getVehicle().getVehicleNumber(),
                driver.getVehicle().getType(),
                driver.getVehicle().getColor(),
                trip.getContactNumber()
        );

    }

    private String generateTripCancelEmailBody(Trip trip) {
        String emailTemplate = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Trip Request Cancelled</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 0;
                        }
                        .container {
                            background-color: #ffffff;
                            width: 80%%;
                            max-width: 600px;
                            margin: 20px auto;
                            padding: 20px;
                            border-radius: 10px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                        }
                        .header {
                            background-color: #ff4b4b;
                            color: #ffffff;
                            text-align: center;
                            padding: 10px;
                            border-top-left-radius: 10px;
                            border-top-right-radius: 10px;
                        }
                        .header h1 {
                            margin: 0;
                            font-size: 24px;
                        }
                        .content {
                            margin: 20px 0;
                            line-height: 1.6;
                        }
                        .content p {
                            margin: 10px 0;
                        }
                        .footer {
                            text-align: center;
                            color: #666666;
                            font-size: 14px;
                            padding-top: 10px;
                            border-top: 1px solid #dddddd;
                        }
                        .footer p {
                            margin: 5px 0;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>Trip Request Cancelled</h1>
                        </div>
                        <div class="content">
                            <p>Dear %s,</p>
                            <p>Your trip request has been cancelled.</p>
                            <p>
                                <strong>Driver:</strong> %s<br>
                                <strong>Vehicle Number:</strong> %s<br>
                                <strong>Vehicle Type:</strong> %s<br>
                                <strong>Vehicle Color:</strong> %s<br>
                            </p>
                            <p>If you have any questions, feel free to contact our support team.</p>
                        </div>
                        <div class="footer">
                            <p>&copy; 2024 Your Company Name. All rights reserved.</p>
                        </div>
                    </div>
                </body>
                </html>
                """;

        User driver = trip.getDriver();
        User passenger = trip.getPassenger();

        return emailTemplate.formatted(
                passenger.getName(),
                driver.getName(),
                driver.getVehicle().getVehicleNumber(),
                driver.getVehicle().getType(),
                driver.getVehicle().getColor()
        );
    }


}
