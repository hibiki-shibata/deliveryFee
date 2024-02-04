package rushHourKt

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import java.time.OffsetDateTime

class RushHourTest{
    
    val RushHour = RushHour();

    @Test fun `Friday 3pm - 7pm has to be considered as RushHour`(){
        val FridayOutOfRushTime = RushHour.isRushHour(OffsetDateTime.parse("2024-02-02T13:00:00Z"))
        val FridayRushhourEdge1 = RushHour.isRushHour(OffsetDateTime.parse("2024-02-02T15:00:00Z"))
        val FridayRushhourMiddleTime = RushHour.isRushHour(OffsetDateTime.parse("2024-02-02T17:00:00Z"))
        val FridayRushhourEdge2 = RushHour.isRushHour(OffsetDateTime.parse("2024-02-02T19:00:00Z"))
        val NotFriday = RushHour.isRushHour(OffsetDateTime.parse("2024-02-03T17:00:00Z"))

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
            RushHour.calculateRushHourFee(-100000, deliveryFeeMaxCap)
        }   
        val zero = RushHour.calculateRushHourFee(0, deliveryFeeMaxCap)
        val generalCase = RushHour.calculateRushHourFee(1000, deliveryFeeMaxCap)
        val just = RushHour.calculateRushHourFee(1500, deliveryFeeMaxCap)
        val larger = RushHour.calculateRushHourFee(2000, deliveryFeeMaxCap)

        assertEquals("error in calculateRushHourFee", minus.message)
        assertEquals(0, zero)
        assertEquals(1200, generalCase)
        assertEquals(1500, just)
        assertEquals(1500, larger)

    }
}