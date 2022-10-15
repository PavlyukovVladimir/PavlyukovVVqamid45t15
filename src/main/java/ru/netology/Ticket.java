package ru.netology;

import org.jetbrains.annotations.NotNull;


public class Ticket implements Comparable<Ticket>, Identifiable {
    static private int count = 0;
    private final int id;
    private long costInKopecks;
    private String airportFrom;
    private String airportTo;
    private int minutesOnWay;


    public Ticket(long costInKopecks, String airportFrom, String airportTo, int minutesOnWay) {
        if (costInKopecks >= 0) {
            this.costInKopecks = costInKopecks;
        } else {
            throw new IllegalArgumentException(
                    String.format("Cost of ticket is %d, should be not negative", costInKopecks));
        }
        if (minutesOnWay > 0) {
            this.minutesOnWay = minutesOnWay;
        } else {
            throw new IllegalArgumentException(
                    String.format("Minutes on the way is %d, should be positive", minutesOnWay));
        }
        if (airportFrom != null) {
            this.airportFrom = airportFrom;
        } else {
            throw new NullPointerException("The AirportFrom must be not null");
        }
        if (airportTo != null) {
            this.airportTo = airportTo;
        } else {
            throw new NullPointerException("The AirportTo must be not null");
        }
        id = ++count;
    }

    @Override
    public int compareTo(@NotNull Ticket o) {
        long selfCost = this.getCostInKopecks();
        long otherCost = o.getCostInKopecks();
        return Long.compare(selfCost, otherCost);
    }

    @Override
    public int getId() {
        return id;
    }

    public long getCostInKopecks() {
        return costInKopecks;
    }

    public double getCostInRub() {
        return costInKopecks / 100.0;
    }

    public String getAirportFrom() {
        return airportFrom;
    }

    public String getAirportTo() {
        return airportTo;
    }

    public int getMinutesOnWay() {
        return minutesOnWay;
    }

    // Если мы хотим сравнивать билеты м/у собой,
    // то id нужно исключить из рассмотрения,
    // т.к. я реализовал программу так, чтобы в одной сессии не могли создаваться билеты с одинаковым id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return getCostInKopecks() == ticket.getCostInKopecks() &&
                getMinutesOnWay() == ticket.getMinutesOnWay() &&
                getAirportFrom().equals(ticket.getAirportFrom()) &&
                getAirportTo().equals(ticket.getAirportTo());
    }

    // по заданию не нужно, но захотелось добавить "для порядка"
    @Override
    public String toString() {
        return String.format(
                "Ticket{id: %d, cost in RUB: %.2f, airport from: %s, airport to: %s, minutes on way: %d}",
                id, costInKopecks / 100.0, airportFrom, airportTo, minutesOnWay);
    }
}
