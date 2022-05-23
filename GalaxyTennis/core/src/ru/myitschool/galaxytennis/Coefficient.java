package ru.myitschool.galaxytennis;

public class Coefficient {
    float galaxy, planet, plate;

    Coefficient(float galaxy, float planet, float plate){
        this.galaxy = galaxy;
        this.planet = planet;
        this.plate = plate;
    }

    float get_sum(){
        return galaxy + planet + plate;
    }
}
