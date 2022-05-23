package ru.myitschool.galaxytennis;

public class Galaxy {
    String image;
    int num;
    float x, y, width, height;

    Galaxy(int num, String image, float x, float y, float width, float height){
        this.num = num;
        this.image = image;
        this.x = x; this.y = y;
        this.width = width; this.height = height;
    }
}
