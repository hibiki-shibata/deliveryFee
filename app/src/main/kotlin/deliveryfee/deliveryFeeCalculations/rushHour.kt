// Configurate RUSH HOUR
package rushHourKt

import java.time.DayOfWeek
import java.time.OffsetDateTime

//Spec:During the Friday rush, 3 - 7 PM(UTC), the delivery fee (the total fee including possible surcharges) will be multiplied by 1.2x. However, the fee still cannot be more than the max (15€).
class RushHour {

    companion object{
        // Friday
        val RUSH_HOUR_FRIDAY_START: Int = 15
        val RUSH_HOUR_FRIDAY_END: Int = 19
        val RUSH_HOUR_FRIDAY_DAYS = setOf(DayOfWeek.FRIDAY)

        // val RUSH_HOUR_WEDNESDAY_START = 11
        // val RUSH_HOUR_WEDNESDAY_END = 14
        // val RUSH_HOUR_WEDNESDAY_DAYS = setOf(DayOfWeek.WEDNESDAY)
        
    }


    fun isRushHour(deliveryTime: OffsetDateTime): Boolean{
        val isFridayRushHour = isDayOfWeekRushHour(deliveryTime, RUSH_HOUR_FRIDAY_START, RUSH_HOUR_FRIDAY_END, RUSH_HOUR_FRIDAY_DAYS)
        // val isWednesdayRushHour = isDayOfWeekRushHour(deliveryTime, RUSH_HOUR_WEDNESDAY_START, RUSH_HOUR_WEDNESDAY_END, RUSH_HOUR_WEDNESDAY_DAYS)
        return isFridayRushHour 
    }

    private fun isDayOfWeekRushHour(deliveryTime: OffsetDateTime, startTime: Int, endTime: Int, rushHourDays: Set<DayOfWeek>): Boolean {
        val isBetweenRushHours = deliveryTime.hour in startTime..endTime
        val isRushHourDay = deliveryTime.dayOfWeek in rushHourDays
        return isBetweenRushHours && isRushHourDay
    }


    fun calculateRushHourFee(originalFee: Int, deliveryFeeMaxCap: Int): Int {
        val updatedFee: Int = originalFee * 12 / 10

        val deliveryFee: Int = if (updatedFee >= deliveryFeeMaxCap) deliveryFeeMaxCap else updatedFee

    
        if(originalFee < 0 || deliveryFeeMaxCap < deliveryFee || deliveryFee < 0) throw Exception("error in calculateRushHourFee")
    return deliveryFee
}

}