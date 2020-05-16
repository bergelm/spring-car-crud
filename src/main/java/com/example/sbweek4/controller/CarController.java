package com.example.sbweek4.controller;

import com.example.sbweek4.model.Car;
import com.example.sbweek4.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
public class CarController {


    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/car")
    public String get(Model model) {
        model.addAttribute("cars", carService.getCars());
        model.addAttribute("newCar", new Car());
        return "car";
    }


    @GetMapping("/car-same-color")
    public String getSameColor(@ModelAttribute Car car, Model model) {
        List<Car> carsSameColor = carService.getCarsSameColor(car.getColor());
        if (!carsSameColor.isEmpty()) {
            model.addAttribute("carsSameColor", carsSameColor);

            return "car-list-view";
        } else {
            return "car-not-found";
        }

    }


    @GetMapping("/id-search")
    public String getCarById(@ModelAttribute Car car, Model model) {
        Optional<Car> carById = carService.getCarById(car.getId());
        if (carById.isPresent()) {
            model.addAttribute("car", carById.get());
            return "car-view";
        }
        return "car-not-found";
    }


    @GetMapping("/delete-car/{id}")
    public String removeCar(@PathVariable long id) {

        carService.deleteCar(id);
        return "redirect:/car";
    }

    @GetMapping("/update-car/{id}")
    public String updateCar(@PathVariable long id, Model model) {

        Optional<Car> carToUpdate = carService.getCarById(id);
        if (carToUpdate.isPresent()) {
            model.addAttribute("carToUpdate", carToUpdate.get());
            return "update-car";
        }
        return "car-not-found";


    }

    @PostMapping("/add-car")
    public String addCar(@ModelAttribute Car car) {
        if (!carService.getIdSet().contains(car.getId())) {
            carService.addCar(car);
            return "redirect:/car";
        } else {
            return "incorrect-car-id";
        }

    }


    @PostMapping("/save-updated-car/{id}")
    public String saveUpdatedCar(@PathVariable Long id, @ModelAttribute Car carToUpdate) {
        carService.updateCar(carToUpdate);
        return "redirect:/car";
    }

}



