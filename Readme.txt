Suggested Modifications or Additions to the Order and/or Orderbook classes to make them better suited to support real-life, latency-sensitive trading operations

1. Make the Order class immutable by making the class and all the fields final.
2. Override equals and hashCode methods to ensure consistency for manipulating in a collection.
3. Ensure there is only one copy of OrderBook (using Singleton).
4. Instead of TreeMap in OrderBookImpl, use a threadsafe data structure like ConcurrentSkipListMap.
5. Code to Interface by adding all the required methods for OrderBook in an Interface (e.g. OrderBook) and
then provide implementation of OrderBook methods (e.g. OrderBookImpl).
6. Ensure code blocks that will be accessed by multiple threads are threadsafe using thread locks but minimize to sections that will
involve changes to data, so it doesn't impact on performance.


