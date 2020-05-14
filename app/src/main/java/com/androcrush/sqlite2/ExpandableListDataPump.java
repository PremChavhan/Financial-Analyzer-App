package com.androcrush.sqlite2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> food = new ArrayList<String>();
        food.add("Food & Drinks");
        food.add("Cafe");
        food.add("Bar");
        food.add("Groceries");
        food.add("Restaurent,Fast-food");

        List<String> shop = new ArrayList<String>();
        shop.add("Shopping");
        shop.add("Clothes & shoes");
        shop.add("Drug-store,Chemist");
        shop.add("Electronics,accessories");
        shop.add("Free time");
        shop.add("Gifts,joy");
        shop.add("Health and beauty");
        shop.add("Home,garden");
        shop.add("Jewels,accessories");
        shop.add("Kids");
        shop.add("Pets,animals");
        shop.add("Stationary,tools");


        List<String> house = new ArrayList<String>();
        house.add("Housing");
        house.add("Energy, Utilities");
        house.add("Maintenance, repaires");
        house.add("Mortgage");
        house.add("Property Insurance");
        house.add("Rent");
        house.add("Services");


        List<String> trans = new ArrayList<String>();
        trans.add("Transportation");
        trans.add("Business trips");
        trans.add("Long distance");
        trans.add("Public transport");
        trans.add("Taxi");


        List<String> vehicle = new ArrayList<String>();
        vehicle.add("Vehicle");
        vehicle.add("Fuel");
        vehicle.add("Leasing");
        vehicle.add("Parking");
        vehicle.add("Rentals");
        vehicle.add("Vehicle insurance");
        vehicle.add("Vehicle maintenance");


        List<String> life = new ArrayList<String>();
        life.add("Life & Entertainment");
        life.add("Active sports, fitness");
        life.add("Alcohol, tobacco");
        life.add("Books, audio, subscriptions");
        life.add("Charity, gifts");
        life.add("Culture, sports event");
        life.add("Education, development");
        life.add("Healthcare, doctor");
        life.add("Hobbies");
        life.add("Holiday, trips, hotels");
        life.add("Life events");
        life.add("Lottery, gambling");
        life.add("Education development");
        life.add("TV, Streaming");
        life.add("Wellness, beauty");


        List<String> comm = new ArrayList<String>();
        comm.add("Communication, PC");
        comm.add("Internet");
        comm.add("Phone, cell phone");
        comm.add("Postal services");
        comm.add("Software, apps, games");


        List<String> finance = new ArrayList<String>();
        finance.add("Financial expenses");
        finance.add("Advisory");
        finance.add("Charges, fees");
        finance.add("Child support");
        finance.add("Fines");
        finance.add("Insurances");
        finance.add("Loan,interests");
        finance.add("Taxes");


        List<String> invest = new ArrayList<String>();
        invest.add("Investments");
        invest.add("Collections");
        invest.add("Financial Investments");
        invest.add("Realty");
        invest.add("Savings");
        invest.add("Vehicles, chattels");


        List<String> income = new ArrayList<String>();
        income.add("Income");
        income.add("Checks, coupons");
        income.add("Child support");
        income.add("Dues & grants");
        income.add("Gifts");
        income.add("Interests, dividends");
        income.add("Lending, renting");
        income.add("Lottery, gambling");
        income.add("Refund (tax,purchase)");
        income.add("Rental income");
        income.add("Sale");
        income.add("Wage, invoices");


        List<String> other = new ArrayList<String>();
        other.add("Others");
        other.add("Missing");

        expandableListDetail.put("Food & Drinks", food);
        expandableListDetail.put("Shopping", shop);
        expandableListDetail.put("Housing", house);
        expandableListDetail.put("Transportation", trans);
        expandableListDetail.put("Vehicle", vehicle);
        expandableListDetail.put("Life & Entertainment", life);
        expandableListDetail.put("Communication, PC", comm);
        expandableListDetail.put("Financial expenses", finance);
        expandableListDetail.put("Investments", invest);
        expandableListDetail.put("Others", other);
        return expandableListDetail;
    }
}
