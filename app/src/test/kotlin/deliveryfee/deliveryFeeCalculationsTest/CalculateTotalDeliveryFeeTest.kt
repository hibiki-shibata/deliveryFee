// Testing each functions of CalculateTotaldeliveryFee.kt
package CalculateTotalDeliveryFeeKt

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


// https://github.com/woltapp/engineering-internship-2024

class TestEachFunctionsOfdeliveryFee {

    val deliveryFee = Deliveryfee()

    @Test fun `If the cart value is less than 10€, a small order surcharge is added to the delivery price The surcharge is the difference between the cart value and 10€`() {
        // calculateOrderSurcharge(cart_value)
        val minus = assertFailsWith<Exception> {
            deliveryFee.calculateOrderSurcharge(-100000)
        }        
        val zero = deliveryFee.calculateOrderSurcharge(0)
        val mid = deliveryFee.calculateOrderSurcharge(790)
        val just = deliveryFee.calculateOrderSurcharge(1000)
        val large = deliveryFee.calculateOrderSurcharge(10000)

        assertEquals("error in calculateOrderSurcharge", minus.message)
        assertEquals(1000, zero)
        assertEquals(210, mid)
        assertEquals(0, just)
        assertEquals(0, large)   
        
    }

    
    @Test fun `1€ is added for every additional 500 meters Even if the distance would be shorter than 500 meters, the minimum fee is always 1€`() {
        // calculateDistanceFee(delivery_distance)
        val minus = assertFailsWith<Exception> {
            deliveryFee.calculateDistanceFee(-100000)
        }   
        val zero = deliveryFee.calculateDistanceFee(0)
        val minimum = deliveryFee.calculateDistanceFee(500)
        val middle = deliveryFee.calculateDistanceFee(800)
        val just = deliveryFee.calculateDistanceFee(1000)
        val large = deliveryFee.calculateDistanceFee(1501)
        val large2 = deliveryFee.calculateDistanceFee(1499) 

        assertEquals("error in calculateDistanceFee", minus.message)
        assertEquals(100, zero)
        assertEquals(100, minimum)
        assertEquals(200, middle)
        assertEquals(200, just)
        assertEquals(400, large)
        assertEquals(300, large2)
    }

    
    @Test fun `If the number of items is five or more, an additional 50 cent surcharge is added for each item above and including the fifth item An extra "bulk" fee applies for more than 12 items of 1,20€`() {
        // calculateItemSurcharge(number_of_items)
        val minus = assertFailsWith<Exception> {
            deliveryFee.calculateItemSurcharge(-100000)
        }   
        val zero = deliveryFee.calculateItemSurcharge(0)
        val small = deliveryFee.calculateItemSurcharge(1)
        val just = deliveryFee.calculateItemSurcharge(5)
        val large = deliveryFee.calculateItemSurcharge(7)
        val twelve = deliveryFee.calculateItemSurcharge(12)
        val superLarge = deliveryFee.calculateItemSurcharge(17)

        assertEquals("error in calculateItemSurcharge", minus.message)
        assertEquals(0, zero)
        assertEquals(0, small)
        assertEquals(50, just)
        assertEquals(150, large)
        assertEquals(400, twelve)
        assertEquals(770, superLarge)
      
    } 


}
