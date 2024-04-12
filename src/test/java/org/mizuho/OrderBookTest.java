package org.mizuho;

import static org.junit.Assert.assertTrue;
import static org.mizuho.Constants.*;

import org.junit.Test;

/**
 * Unit test for limit order management.
 */
public class OrderBookTest
{

    @Test
    public void addOrderShouldAnswerWithTrue()
    {
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(getBidOrder(1l,97.25,1_000l));
        orderBook.addOrder(getOfferOrder(2l,101.99,750l));

        assertTrue( orderBook.getAllOrders(BID).size() == 1);
        assertTrue( orderBook.getAllOrders(OFFER).size() == 1);
    }


    @Test
    public void removeOrderShouldAnswerWithTrue()
    {
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(getBidOrder(1l,97.25,1_000l));
        orderBook.addOrder(getOfferOrder(2l,101.99,750l));

        assertTrue( orderBook.getAllOrders(BID).size() == 1);
        assertTrue( orderBook.getAllOrders(OFFER).size() == 1);

        orderBook.removeOrder(1l);
        assertTrue( orderBook.getAllOrders(BID).size() == 0);
        assertTrue( orderBook.getAllOrders(OFFER).size() == 1);

        orderBook.removeOrder(2l);
        assertTrue( orderBook.getAllOrders(BID).size() == 0);
        assertTrue( orderBook.getAllOrders(OFFER).size() == 0);
    }

    @Test
    public void testWhenModifyOrderSizeIsWithValidSize()
    {
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(getBidOrder(1l,97.25,1_000l));
        orderBook.addOrder(getOfferOrder(2l,101.99,750l));

        orderBook.modifyOrderSize(1l, 887l);
        assertTrue( orderBook.getOrder(1l).getSize() == 887l);

        orderBook.modifyOrderSize(2l, 1_750l);
        assertTrue( orderBook.getOrder(2l).getSize() == 1_750l);
    }

    @Test
    public void testWhenModifyOrderSizeIsWithInvalidSize()
    {
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(getBidOrder(1l,97.25,1_000l));
        orderBook.addOrder(getOfferOrder(2l,101.99,750l));

        try {
            orderBook.modifyOrderSize(1l, -22l);
        }catch(Exception e){
            assertTrue( e.getMessage().equals(INVALID_ORDER_SIZE_MESSAGE));
        }


        orderBook.modifyOrderSize(2l, 1_750l);
        assertTrue( orderBook.getOrder(2l).getSize() == 1_750l);
    }

    @Test
    public void testWhenGetPriceReturnsValidPrice()
    {
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(getBidOrder(1l,97.25,1_000l));
        orderBook.addOrder(getBidOrder(2l,121.19,800l));
        orderBook.addOrder(getBidOrder(3l,110.15,600l));
        orderBook.addOrder(getBidOrder(4l,110.15,815l));
        orderBook.addOrder(getOfferOrder(5l,101.99,750l));
        orderBook.addOrder(getOfferOrder(6l,102.17,910l));
        orderBook.addOrder(getOfferOrder(7l,119.22,1_010l));
        orderBook.addOrder(getOfferOrder(8l,101.99,1_500l));

        assertTrue( orderBook.getPrice(BID, 2) == 110.15);
        assertTrue( orderBook.getPrice(OFFER, 1) == 101.99);
    }

    @Test
    public void testWhenGetPriceReturnsInvalidPrice()
    {
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(getBidOrder(1l,97.25,1_000l));
        orderBook.addOrder(getBidOrder(2l,121.19,800l));
        orderBook.addOrder(getBidOrder(3l,110.15,600l));
        orderBook.addOrder(getBidOrder(4l,110.15,815l));
        orderBook.addOrder(getOfferOrder(5l,101.99,750l));
        orderBook.addOrder(getOfferOrder(6l,102.17,910l));
        orderBook.addOrder(getOfferOrder(7l,119.22,1_010l));
        orderBook.addOrder(getOfferOrder(8l,101.99,1_500l));

        assertTrue( orderBook.getPrice(BID, 6) == INVALID_PRICE);
        assertTrue( orderBook.getPrice(OFFER, 7) == INVALID_PRICE);
    }

    @Test
    public void getTotalSizeShouldAnswerWithTrue()
    {
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(getBidOrder(1l,97.25,1_000l));
        orderBook.addOrder(getBidOrder(2l,121.19,800l));
        orderBook.addOrder(getBidOrder(3l,110.15,600l));
        orderBook.addOrder(getBidOrder(4l,110.15,815l));
        orderBook.addOrder(getOfferOrder(5l,101.99,750l));
        orderBook.addOrder(getOfferOrder(6l,102.17,910l));
        orderBook.addOrder(getOfferOrder(7l,119.22,1_010l));
        orderBook.addOrder(getOfferOrder(8l,101.99,1_500l));

        assertTrue( orderBook.getAllOrders(BID).size() == 4);
        assertTrue( orderBook.getAllOrders(OFFER).size() == 4);

        assertTrue( orderBook.getTotalSize(BID, 2) == 1_415l);

        assertTrue( orderBook.getTotalSize(OFFER, 1) == 2_250l);
    }

    public void getAllOrdersShouldAnswerWithTrue()
    {
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(getBidOrder(1l,97.25,1_000l));
        orderBook.addOrder(getBidOrder(2l,121.19,800l));
        orderBook.addOrder(getBidOrder(3l,110.15,600l));
        orderBook.addOrder(getBidOrder(4l,110.15,815l));
        orderBook.addOrder(getOfferOrder(5l,101.99,750l));
        orderBook.addOrder(getOfferOrder(6l,102.17,910l));
        orderBook.addOrder(getOfferOrder(7l,119.22,1_010l));
        orderBook.addOrder(getOfferOrder(8l,101.99,1_500l));

        assertTrue( orderBook.getAllOrders(BID).size() == 4);
        assertTrue( orderBook.getAllOrders(OFFER).size() == 4);

    }


    private Order getBidOrder(long id, double price, long size){
        return new Order(id, price, BID, size);
    }

    private Order getOfferOrder(long id, double price, long size){
        return new Order(id, price, OFFER, size);
    }


}
