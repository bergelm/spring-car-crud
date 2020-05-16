package com.example.sbweek4.service;

import com.example.sbweek4.model.Car;
import com.example.sbweek4.model.Color;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class CarService {

    private List<Car> cars;

    private Set<Long> idSet;



    public CarService() {
        Car car1 = new Car(1, "BMW", "i8", Color.BLACK);
        Car car2 = new Car(2, "Fiat", "500", Color.RED);
        Car car3 = new Car(3, "Honda", "Jazz", Color.RED);

        cars = new ArrayList<>();
        idSet = new HashSet<>();

        cars.add(car1);
        cars.add(car2);
        cars.add(car3);

        idSet.add(car1.getId());
        idSet.add(car2.getId());
        idSet.add(car3.getId());
    }

    public List<Car> getCars() {
        return cars;
    }

    public Optional<Car> getCarById(long id){
        Optional<Car> foundCar = cars.stream().filter(car->car.getId()==id).findFirst();
        return foundCar;
    }

    public List<Car> getCarsSameColor(Color color){
        List<Car> carsSameColor = new ArrayList<>();
        for(Car c: cars){
            if(c.getColor() == color) carsSameColor.add(c);
        }

        return carsSameColor;

    }


    public void addCar(Car car){
        cars.add(car);
        idSet.add(car.getId());
    }



    public void deleteCar(long id){
        Car redundantCar = cars.stream().filter(car->car.getId()==id).findFirst().get();
        cars.remove(redundantCar);
        idSet.remove(id);
    }

    public void updateCar(Car newCar){
        Optional<Car> redundantCar = cars.stream().filter(car->car.getId()==newCar.getId()).findFirst();
        if(redundantCar.isPresent()){
            cars.remove(redundantCar.get());
            cars.add(newCar);
        }
        else cars.add(newCar);


    }

    public Set<Long> getIdSet() {
        return idSet;
    }



}
