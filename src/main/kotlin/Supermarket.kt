package org.example

class Supermarket(
    val id: Int,
    val name: String,
    private val products: Map<Int, Product>,
    val stockInicial: Map<Int, Int>
) {

    private val stock: MutableMap<Int, Int> = stockInicial.toMutableMap()
    private val sells: MutableMap<Int, Int> = mutableMapOf()

    /**
     * Registra la venta de un producto dado su ID y cantidad.
     * @throws IllegalArgumentException si el producto no existe o no hay suficiente stock.
     * @return el precio total de la venta.
     */
    fun registrarVenta(productId: Int, amount: Int): Double {
        val product = products[productId]
            ?: throw IllegalArgumentException("Producto $productId no encontrado")
        val productStock = stock[productId] ?: 0

        if (amount <= 0) {
            throw IllegalArgumentException("Cantidad debe ser positiva")
        }
        if (productStock < amount) {
            throw IllegalArgumentException("Stock insuficiente para ${product.name}")
        }

        // actualizar stock y ventas
        stock[productId] = productStock - amount
        sells[productId] = (sells[productId] ?: 0) + amount

        return product.price * amount
    }

    /**
     * Retorna la cantidad vendida de un producto.
     */
    fun cantidadVendida(idProducto: Int): Int = sells[idProducto] ?: 0

    /**
     * Retorna ingresos obtenidos de un producto.
     */
    fun ingresosPorProducto(productId: Int): Double {
        val product = products[productId]
            ?: throw IllegalArgumentException("Producto $productId no encontrado")
        return cantidadVendida(productId) * product.price
    }

    /**
     * Retorna ingresos totales de todas las ventas.
     */
    fun ingresosTotales(): Double =
        sells.entries.sumOf { (id, amount) -> products[id]!!.price * amount }

}