# order_mgt
Limit order management for a low latency trading platform

Suggested Modifications or Additions to the Order and/or Orderbook classes to make them better suited to support real-life, latency-sensitive trading operations

- Make the Order class immutable by making the class and all the fields final.
- Override equals and hashCode methods to ensure consistency for manipulating in a collection.
- Ensure there is only one copy of OrderBook (using Singleton).
- Instead of TreeMap in OrderBookImpl, use a threadsafe data structure like ConcurrentSkipListMap.
- Code to Interface by adding all the required methods for OrderBook in an Interface (e.g. OrderBook) and
then provide implementation of OrderBook methods (e.g. OrderBookImpl).
- Ensure code blocks that will be accessed by multiple threads are threadsafe using thread locks but minimize to sections that will
involve changes to data, so it doesn't impact on performance.
