fun main() {
    val prices = listOf(100, 7, 1, 2, 39,3, 4, 5,9)

    println(maxProfit(prices))
    println(maxProfit2(prices))
}

/**
 * Find the minimum price in the list complexity o(n^2)
 */
fun maxProfit2(prices: List<Int>): Int {
    var maxProfit = prices.min() - prices.max()
    for( buyPrice in prices){
        for (sellPrice in prices.subList(1, prices.size)) {
            val profit = sellPrice - buyPrice
            if (profit > maxProfit) {
                maxProfit = profit
            }
        }
    }
    return maxProfit
}

/**
 * Find the minimum price in the list complexity o(n)
 */
fun maxProfit(prices: List<Int>): Int {
    var maxProfit = 0
    var minBuyPrice = prices.max() ?: 0
    prices.forEach { salePrice ->
        if (salePrice < minBuyPrice) {
            minBuyPrice = salePrice
        } else if (salePrice - minBuyPrice > maxProfit) {
            maxProfit = salePrice - minBuyPrice
        }
    }
    return maxProfit
}