import org.example.Product
import org.example.Supermarket
import org.example.SupermarketChain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class SupermarketChainTest {
    private val allProducts = mapOf(
        1 to Product(1, "Carne", 10.0),
        2 to Product(2, "Pescado", 20.0),
        3 to Product(3, "Pollo", 30.0),
        4 to Product(4, "Cerdo", 45.0),
        5 to Product(5, "Ternera", 50.0),
        6 to Product(6, "Cordero", 65.0)
    )

    private lateinit var supermarketA: Supermarket
    private lateinit var supermarketB: Supermarket
    private lateinit var chain: SupermarketChain

    @BeforeTest
    fun setup() {
        supermarketA = Supermarket(
            1,
            "Supermercado A",
            allProducts,
            mapOf(
                1 to 20, 2 to 20, 3 to 20, 4 to 20, 5 to 20, 6 to 20
            ))
        supermarketB = Supermarket(
            2,
            "Supermercado B",
            allProducts,
            mapOf(
                1 to 10, 2 to 0, 3 to 10, 4 to 12, 5 to 23, 6 to 0
            ))

        chain = SupermarketChain(listOf(supermarketA, supermarketB), allProducts)
    }

    @Test
    fun testRegistrarVenta() {
        val total = supermarketA.registrarVenta(1, 2)
        assertEquals(20.0, total)
        assertEquals(2, supermarketA.cantidadVendida(1))
    }

    @Test
    fun testStockInsuficiente() {
        assertFailsWith<IllegalArgumentException> {
            supermarketA.registrarVenta(1, 50)
        }
    }

    @Test
    fun testIngresosPorProducto() {
        supermarketA.registrarVenta(2, 3)
        assertEquals(60.0, supermarketA.ingresosPorProducto(2))
    }

    @Test
    fun testIngresosTotalesCadena() {
        supermarketA.registrarVenta(1, 2)
        supermarketA.registrarVenta(2, 2)
        assertTrue(chain.ingresosTotales() > 0)
    }

    @Test
    fun testTop5ProductosVendidos() {
        supermarketA.registrarVenta(1, 2)
        supermarketA.registrarVenta(2, 5)
        val result = chain.top5ProductosVendidos()
        assertTrue(result.contains("Pescado: 5"))
    }

    @Test
    fun testSupermercadoMayorIngreso() {
        supermarketA.registrarVenta(1, 2)
        val result = chain.supermercadoMayorIngreso()
        assertTrue(result.contains("Supermercado A"))
    }
}