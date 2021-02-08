import com.cubetiqs.money.Money
import com.cubetiqs.money.MoneyConfig
import com.cubetiqs.money.MoneyCurrency
import com.cubetiqs.money.MoneyExchangeUtils
import org.junit.Assert
import org.junit.Test

class MoneyTests {
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

        // Is valid for money config?
        Assert.assertTrue(MoneyConfig.isValid())

        val moneyUsd = Money(2.0)
        val moneyKhr = MoneyExchangeUtils.exchange(moneyUsd, MoneyCurrency.create("KHR"))

        // Is correct exchange?
        Assert.assertEquals(8000.0, moneyKhr.getMoneyValue(), 0.0)
    }
}