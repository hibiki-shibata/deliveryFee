package CalculateTotalDeliveryFee

import kotlinx.serialization.json.Json
import java.time.ZoneOffset
import java.time.DayOfWeek
import java.time.OffsetDateTime
import kotlin.math.ceil
import indexfile.FeeCalcRequest

class Deliveryfee{


    // calculation
    fun SumDeliveryFee(request: FeeCalcRequest): Int {

            val cartValue = request.cart_value
            val deliveryDistance = request.delivery_distance
            val numberOfItems = request.number_of_items
            val deliveryTime = OffsetDateTime.parse(request.time)

            //100€ Free delivery?
            if (cartValue >= 10000) {
                val deliveryFee:Int = 0
            return  deliveryFee
            }

            val smallOrderSurcharge: Int = calculateOrderSurcharge(cartValue)
            val distanceFee: Int = calculateDistanceFee(deliveryDistance)
            val itemSurcharge: Int = calculateItemSurcharge(numberOfItems)
            val bulkFee: Int = if (numberOfItems > 12) 120 else 0

            var deliveryFee:Int = distanceFee + itemSurcharge + bulkFee + smallOrderSurcharge

            // Rush Hour?
            val RushHour = RushHour()
            if (RushHour.isRushHour(deliveryTime)) {
                val RushdeliveryFee: Int = RushHour.calculateRushHourFee(deliveryFee)
                return RushdeliveryFee
            }
            
            // cap 15 euro
            if(deliveryFee >= 1500){deliveryFee = 1500}
            return deliveryFee

        
    }


    fun calculateOrderSurcharge(cartValue: Int): Int{
            val surcharges: Int
            surcharges = if (cartValue < 1000) {
                1000 - cartValue
            } else 0

        return surcharges
    }


    fun calculateDistanceFee(distance: Int): Int {
            val baseDistanceFee = 200
            val additionalDistance: Int = distance - 1000
            val additionalDistanceFee: Int = if (additionalDistance > 0) {
                (ceil(additionalDistance / 500.0) * 100).toInt() 
                //ceiling logic manual =>  + if (additionalDistance % 500 != 0) 100 else 0
            } else 0

        return baseDistanceFee + additionalDistanceFee
    }

    // If the number of items is five or more, an additional 50 cent surcharge is added for each item above and including the fifth item. 
    // An extra "bulk" fee applies for more than 12 items of 1,20€
    fun calculateItemSurcharge(numberOfItems: Int): Int {
            val baseItemSurcharge: Int = 50
            var additionalSurcharge: Int = if (numberOfItems >= 5) {
                val extraItems:Int = numberOfItems - 4
                baseItemSurcharge * extraItems 
            } else 0

            //Bulk fee
            additionalSurcharge += if (numberOfItems > 12) 120 else 0


        return additionalSurcharge
    }

    

    class RushHour {

        companion object{
            // Friday
            val RUSH_HOUR_FRIDAY_START: Int = 15
            val RUSH_HOUR_FRIDAY_END: Int = 19
            val RUSH_HOUR_FRIDAY_DAYS = setOf(DayOfWeek.FRIDAY)

            val RUSH_HOUR_WEDNESDAY_START = 11
            val RUSH_HOUR_WEDNESDAY_END = 14
            val RUSH_HOUR_WEDNESDAY_DAYS = setOf(DayOfWeek.WEDNESDAY)
            
        }


        fun isRushHour(deliveryTime: OffsetDateTime): Boolean{
            val isFridayRushHour = isDayOfWeekRushHour(deliveryTime, RUSH_HOUR_FRIDAY_START, RUSH_HOUR_FRIDAY_END, RUSH_HOUR_FRIDAY_DAYS)
            val isWednesdayRushHour = isDayOfWeekRushHour(deliveryTime, RUSH_HOUR_WEDNESDAY_START, RUSH_HOUR_WEDNESDAY_END, RUSH_HOUR_WEDNESDAY_DAYS)
            return isFridayRushHour || isWednesdayRushHour
        }

        private fun isDayOfWeekRushHour(deliveryTime: OffsetDateTime, startTime: Int, endTime: Int, rushHourDays: Set<DayOfWeek>): Boolean {
            val isBetweenRushHours = deliveryTime.hour in startTime..endTime
            val isRushHourDay = deliveryTime.dayOfWeek in rushHourDays
            return isBetweenRushHours && isRushHourDay
        }


        fun calculateRushHourFee(originalFee: Int): Int {
                val updatedFee = originalFee * 12 / 10
                val RushHourFee = if (updatedFee >= 1500) 1500 else updatedFee

            return RushHourFee
        }


 }



}