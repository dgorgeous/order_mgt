package org.mizuho;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import static org.mizuho.Constants.BID;
import static org.mizuho.Constants.INVALID_ORDER_SIZE_MESSAGE;

public class OrderBook {

    private final TreeMap<Double, ConcurrentLinkedQueue<Order>> bids = new TreeMap<>(Comparator.reverseOrder());
    private final TreeMap<Double, ConcurrentLinkedQueue<Order>> offers = new TreeMap<>();

    private TreeMap<Double, ConcurrentLinkedQueue<Order>> getOrders(char side) {
           return (side == BID) ? bids : offers;
    }

    public Order getOrder(long id) {
        Order order = bids.values().stream().flatMap(Collection::stream).filter(e -> e.getId() == id).findAny().orElse(null);

        if (order != null) {
            return order;
        }
        return offers.values().stream().flatMap(Collection::stream).filter(e -> e.getId() == id).findAny().orElse(null);

    }



    public void addOrder(Order o) {
        TreeMap<Double, ConcurrentLinkedQueue<Order>> limitOrders = getOrders(o.getSide());
        ConcurrentLinkedQueue<Order> orders = limitOrders.get(o.getPrice());
        if(orders == null){
            orders = new ConcurrentLinkedQueue<>();
        }
        orders.add(o);

        synchronized (limitOrders) {
            limitOrders.put(o.getPrice(), orders);
        }
    }
    


    public void removeOrder(long id) {
        Order order = getOrder(id);
        if(order != null){
               getOrders(order.getSide())
                       .get(order.getPrice())
                       .remove(order);
        }
    }

    public void modifyOrderSize(long id, long size) {
        if(size <= 0){
            throw new IllegalArgumentException(INVALID_ORDER_SIZE_MESSAGE);
        }

        Order order = getOrder(id);
        if(order != null){
            synchronized (order) {
                order = new Order(order.getId(), order.getPrice(), order.getSide(), size);
            }
        }
    }


    public double getPrice(char side, int level) {
        TreeMap<Double, ConcurrentLinkedQueue<Order>> limitOrders = getOrders(side);
        return limitOrders.navigableKeySet().stream().skip(level-1).limit(1).findFirst().orElse(Constants.INVALID_PRICE);
    }


    public long getTotalSize(char side, int level) {
        TreeMap<Double, ConcurrentLinkedQueue<Order>> limitOrders = getOrders(side);
        Double priceAtLevel = limitOrders.navigableKeySet().stream().skip(level-1).limit(1).findFirst().orElse(null);
        if(priceAtLevel != null){
            return limitOrders.get(priceAtLevel).stream().map(Order::getSize).reduce(0l, Long::sum);
        }
        return 0l;
    }


    public Collection<Order> getAllOrders(char side) {
        return getOrders(side)
                .values()
                .stream()
                .flatMap(Collection::stream).
                collect(Collectors.toCollection(LinkedList::new));
    }
}
