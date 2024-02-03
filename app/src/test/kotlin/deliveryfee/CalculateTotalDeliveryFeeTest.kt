// Testing each functions of CalculateTotalDeliveryFee.kt
package CalculateTotalDeliveryFeeKt

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import serverKt.FeeCalcRequest
import java.time.OffsetDateTime


// https://github.com/woltapp/engineering-internship-2024

class testEachFunctionsOfDeliveryfee {

    val Deliveryfee = Deliveryfee()

    @Test fun `calculateOrderSurcharge has to be 10€ - cartValue`() {
        // calculateOrderSurcharge(cart_value)
        val minus = assertFailsWith<Exception> {
            Deliveryfee.calculateOrderSurcharge(-100000)
        }        
        val zero = Deliveryfee.calculateOrderSurcharge(0)
        val mid = Deliveryfee.calculateOrderSurcharge(790)
        val just = Deliveryfee.calculateOrderSurcharge(1000)
        val large = Deliveryfee.calculateOrderSurcharge(10000)

        assertEquals("error in calculateOrderSurcharge", minus.message)
        assertEquals(1000, zero)
        assertEquals(210, mid)
        assertEquals(0, just)
        assertEquals(0, large)   
        
    }

    
    @Test fun `Distancefee always has to be above 1 euro and additional fee is charged 1 euro for next every 500m`() {
        // calculateDistanceFee(delivery_distance)
        val minus = assertFailsWith<Exception> {
            Deliveryfee.calculateDistanceFee(-100000)
        }   
        val zero = Deliveryfee.calculateDistanceFee(0)
        val minimum = Deliveryfee.calculateDistanceFee(500)
        val middle = Deliveryfee.calculateDistanceFee(800)
        val just = Deliveryfee.calculateDistanceFee(1000)
        val large = Deliveryfee.calculateDistanceFee(1700)
        val large2 = Deliveryfee.calculateDistanceFee(2050) 

        assertEquals("error in calculateDistanceFee", minus.message)
        assertEquals(100, zero)
        assertEquals(100, minimum)
        assertEquals(200, middle)
        assertEquals(200, just)
        assertEquals(400, large)
        assertEquals(500, large2)
    }

    
    @Test fun `For case the number of items more than 5, 50 cent is added for each items above and including the fifth item and Bulk fee for more than 12 items of 1€ + 20 cent `() {
        // calculateItemSurcharge(number_of_items)
        val minus = assertFailsWith<Exception> {
            Deliveryfee.calculateItemSurcharge(-100000)
        }   
        val zero = Deliveryfee.calculateItemSurcharge(0)
        val small = Deliveryfee.calculateItemSurcharge(1)
        val just = Deliveryfee.calculateItemSurcharge(5)
        val large = Deliveryfee.calculateItemSurcharge(7)
        val twelve = Deliveryfee.calculateItemSurcharge(12)
        val superLarge = Deliveryfee.calculateItemSurcharge(17)

        assertEquals("error in calculateItemSurcharge", minus.message)
        assertEquals(0, zero)
        assertEquals(0, small)
        assertEquals(50, just)
        assertEquals(150, large)
        assertEquals(400, twelve)
        assertEquals(770, superLarge)
      
    }


    @Test fun `Friday 3pm - 7pm has to be considered as RushHour`(){
        val FridayOutOfRushTime = Deliveryfee.isRushHour(OffsetDateTime.parse("2024-02-02T13:00:00Z"))
        val FridayRushhourEdge1 = Deliveryfee.isRushHour(OffsetDateTime.parse("2024-02-02T15:00:00Z"))
        val FridayRushhourMiddleTime = Deliveryfee.isRushHour(OffsetDateTime.parse("2024-02-02T17:00:00Z"))
        val FridayRushhourEdge2 = Deliveryfee.isRushHour(OffsetDateTime.parse("2024-02-02T19:00:00Z"))
        val NotFriday = Deliveryfee.isRushHour(OffsetDateTime.parse("2024-02-03T17:00:00Z"))

        assertEquals(false, FridayOutOfRushTime)
        assertEquals(true, FridayRushhourEdge1)
        assertEquals(true, FridayRushhourMiddleTime)
        assertEquals(true, FridayRushhourEdge2)
        assertEquals(false, NotFriday)

    }


    @Test fun `During the RushHours total surcharge will be multiplied by 12 devided by 10x - However, the fee still cannot be more than the max 15€`(){
        // calculateRushHourFee(deliveryFee_wihtout_rushHour, deliveryFeeMaxCap)
        val deliveryFeeMaxCap: Int = 1500
        
        val minus = assertFailsWith<Exception> {
            Deliveryfee.calculateRushHourFee(-100000, deliveryFeeMaxCap)
        }   
        val zero = Deliveryfee.calculateRushHourFee(0, deliveryFeeMaxCap)
        val generalCase = Deliveryfee.calculateRushHourFee(1000, deliveryFeeMaxCap)
        val just = Deliveryfee.calculateRushHourFee(1500, deliveryFeeMaxCap)
        val larger = Deliveryfee.calculateRushHourFee(2000, deliveryFeeMaxCap)

        assertEquals("error in calculateRushHourFee", minus.message)
        assertEquals(0, zero)
        assertEquals(1200, generalCase)
        assertEquals(1500, just)
        assertEquals(1500, larger)

    }

}








// 10 euro minmimun value ok 
// 1 euro per 500 m, min 1 euro ok 
// 50 cent per numItem which above 4(=<5) ok
// above 12 of numItem, bulkfee 1.20 euro ok
// fee wont be more than 15 euro
// rushHour 1.2 times increase, max 15 euro
// max amount is 1500
// freeDelivery when cart is above 200 euro
