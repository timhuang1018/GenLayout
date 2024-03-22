package tim.huang.genlayout.data


class Dish(val name: String,
           val tranlatedName: String,
           val price: Double, val description: String, val image: String? = "", val tag: String = "")


class VendorConfig(
    val dollarSign: String,
    val showDecimal: Boolean,

)


fun provideData(): List<Dish> {
    return listOf(
        Dish(name = "Insalata di gamberi" , tranlatedName = "意式鮮蝦沙拉", price = 680.00, description = "Prawns salad with strawberry, stracciatella and Balsamico reduction"),
        Dish(name = "Arancini" , tranlatedName = "藜麥菌菇炸飯", price = 320.00, 	description = "fried rice balls filled with mozzarella and mushrooms truffle mayo"),
        Dish(name = "Testaroli al pesto", tranlatedName = "招牌千層麵手工蝦醬麵", price = 680.00, description = "Ligurian style pasta with basil pesto, zucchini, seasonal seafood and scallops"),
        Dish(name = "Rigatoni Gricia al tartufo" , tranlatedName = "意式黑松露碳烤厚切熟成", price =780.00, description = "Roman classic pasta tossed with guanciale, black pepper, pecorino cheese and black truffle"),
        Dish(name = "Cannotto pugliese" , tranlatedName = "傳統香腸蘑菇煙燻紅酒燉煮法", price = 720.00, description = "pizza roll with sausage and spicy broccoli, sautéed clam"),
        Dish(name = "Misto di mare" , tranlatedName = "融合台灣鮮魚", price = 1280.00, description = "Seafood platter, carabineros shrimps, mussels, squid, halibut & clams"),
        Dish(name = "Milanese" , tranlatedName = "米蘭風味豬排", price = 1080.00,	description = "Yilan pork chop (12 oz.), rocket and tomato salad"),
        Dish(name = "Costina" , tranlatedName = "美嫩肉骨牛小排", price = 1580.00, description = "Roasted US beef bone in short rib, asparagus"),
    )
}