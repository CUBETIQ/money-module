import com.cubetiqs.libra.moneyutils.Money
import com.cubetiqs.libra.moneyutils.plus
import com.cubetiqs.libra.moneyutils.times
import com.cubetiqs.libra.moneyutils.timesAssign
import org.junit.Assert
import org.junit.Test

class MoneyTests {
    @Test
    fun test() {
        val money = Money(10.0)
        val money2 = Money(20.0)
        money *= money
        println((money + money2) * money2)
        Assert.assertEquals(10, 10)
    }
}