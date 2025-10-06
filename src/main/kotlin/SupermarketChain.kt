package org.example

class SupermarketChain(
    val supermarkets: List<Supermarket>,
    val allProducts: Map<Int, Product>
) {
    /**
     * Retorna los 5 productos más vendidos en toda la cadena.
     * Formato: "<nombre>: cantidad - <nombre>: cantidad ..."
     */
    fun top5ProductosVendidos(): String {
        val ventasTotales = mutableMapOf<Int, Int>()
        supermarkets.forEach { s ->
            allProducts.keys.forEach { id ->
                ventasTotales[id] = (ventasTotales[id] ?: 0) + s.cantidadVendida(id)
            }
        }
        return ventasTotales.entries
            .sortedByDescending { it.value }
            .take(5)
            .joinToString(" - ") { (id, cant) -> "${allProducts[id]!!.name}: $cant" }
    }

    /**
     * Retorna ingresos totales de toda la cadena.
     */
    fun ingresosTotales(): Double = supermarkets.sumOf { it.ingresosTotales() }

    /**
     * Retorna el supermercado con mayores ingresos.
     * Formato: "<nombre> (<id>). Ingresos totales: <ingresos>"
     */
    fun supermercadoMayorIngreso(): String {
        val max = supermarkets.maxByOrNull { it.ingresosTotales() }
            ?: throw IllegalStateException("No hay supermercados")
        return "${max.name} (${max.id}). Ingresos totales: ${max.ingresosTotales()}"
    }
}