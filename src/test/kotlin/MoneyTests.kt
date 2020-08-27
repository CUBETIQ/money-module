import com.cubetiqs.libra.moneyutils.Money
import com.cubetiqs.libra.moneyutils.MoneyConfig
import com.cubetiqs.libra.moneyutils.MoneyCurrency
import com.cubetiqs.libra.moneyutils.MoneyExchangeUtils
import com.cubetiqs.libra.moneyutils.SpecialStringProcessor
import org.junit.Assert
import org.junit.Test

class MoneyTests {
    @Test
    fun money_operator_test() {
        // val money = Money(10.0)
        // val money2 = Money(20.0)
        // money *= money
        // println((money + money2) * money2)
        // Assert.assertEquals(100.0, money.value, 0.0)

        val test = SpecialStringProcessor().serialize(Money(1.0, "  usd   "))
        println(test)
    }

    @Test
    fun exchange_2usd_to_khr_test() {
        val properties = MoneyConfig
            .MoneyConfigProperties
            .MoneyConfigPropertiesBuilder()
            .setDeliEqual(':')
            .setDeliSplit(',')
            .build()

        MoneyConfig
            .setProperties(properties)
            .parse("USD:1,KHR:4000")

        Assert.assertTrue(MoneyConfig.isValid())

        println(MoneyConfig.getConfig())

        val moneyUsd = Money(2.0)
        val moneyKhr = MoneyExchangeUtils.exchange(moneyUsd, "KHR")

        Assert.assertEquals(8000.0, moneyKhr.getMoneyValue(), 0.0)
    }

    @Test
    fun money_exchange_config_builder_test() {
        MoneyConfig.propertiesBuilder
            .setDeliEqual('=')
            .setDeliSplit(';')

        MoneyConfig.parse("USD:1,KHR=4000,EUR=0.99")

        val moneyUsd = Money.ONE
        val moneyKhr = Money.create(20000.0, MoneyCurrency.KHR)

        val result = moneyUsd
    }
}