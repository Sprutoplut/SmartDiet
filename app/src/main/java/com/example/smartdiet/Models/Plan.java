package com.example.smartdiet.Models;

public class Plan {
    public String log;
    public Double Kal;
    public Double Bel;
    public Double Jir;
    public Double Ugl;
    public Double IMT;
    public Double BOO;
    public String Target;
    public Plan(){}

    public Plan(String log, Double Kal,Double Bel,Double Jir,Double Ugl,Double IMT,Double BOO, String Target) {
        this.log = log;
        this.Kal = Kal;
        this.Bel = Bel;
        this.Jir = Jir;
        this.Ugl = Ugl;
        this.IMT = IMT;
        this.BOO = BOO;
        this.Target = Target;
    }

}
