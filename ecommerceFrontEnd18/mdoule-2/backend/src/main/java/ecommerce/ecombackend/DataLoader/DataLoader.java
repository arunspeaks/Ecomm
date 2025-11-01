package ecommerce.ecombackend.DataLoader;

import ecommerce.ecombackend.model.Product;
import ecommerce.ecombackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            List<Product> products = Arrays.asList(
                    Product.builder().name("Wireless Bluetooth Headphones").category("electronics").price(79.99).stock(25).brand("TechSound").description("High-quality wireless headphones with noise cancellation and 30-hour battery life. Perfect for music lovers and professionals.").imageUrl("https://images.pexels.com/photos/1649771/pexels-photo-1649771.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Smartphone Pro Max").category("electronics").price(1299.99).stock(12).brand("TechMax").description("Latest smartphone with advanced camera system, 5G connectivity, and premium design.").imageUrl("https://images.pexels.com/photos/788946/pexels-photo-788946.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("4K Gaming Monitor").category("electronics").price(449.99).stock(18).brand("ViewMax").description("32-inch 4K gaming monitor with 144Hz refresh rate and HDR support.").imageUrl("https://images.pexels.com/photos/777001/pexels-photo-777001.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Wireless Keyboard & Mouse Set").category("electronics").price(89.99).stock(35).brand("KeyTech").description("Ergonomic wireless keyboard and mouse combo with long battery life.").imageUrl("https://images.pexels.com/photos/2115256/pexels-photo-2115256.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Smart Watch Series 8").category("electronics").price(399.99).stock(22).brand("WristTech").description("Advanced smart watch with health monitoring, GPS, and 5-day battery life.").imageUrl("https://images.pexels.com/photos/437037/pexels-photo-437037.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),

                    // Clothing
                    Product.builder().name("Cotton T-Shirt").category("clothing").price(19.99).stock(50).brand("ComfortWear").description("100% cotton t-shirt available in multiple colors and sizes. Soft and breathable fabric.").imageUrl("https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Denim Jeans").category("clothing").price(69.99).stock(30).brand("DenimStyle").description("Classic fit denim jeans made from premium cotton blend. Durable and stylish.").imageUrl("https://images.pexels.com/photos/1598505/pexels-photo-1598505.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Winter Jacket").category("clothing").price(149.99).stock(15).brand("WarmCoats").description("Waterproof winter jacket with thermal insulation. Perfect for cold weather.").imageUrl("https://images.pexels.com/photos/1040945/pexels-photo-1040945.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Running Shoes").category("clothing").price(129.99).stock(40).brand("SpeedFit").description("Lightweight running shoes with advanced cushioning and breathable design.").imageUrl("https://images.pexels.com/photos/1598508/pexels-photo-1598508.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Dress Shirt").category("clothing").price(49.99).stock(25).brand("FormalWear").description("Premium dress shirt perfect for business and formal occasions.").imageUrl("https://images.pexels.com/photos/1682699/pexels-photo-1682699.jpeg").build(),

                    // Books
                    Product.builder().name("JavaScript Programming Book").category("books").price(29.99).stock(15).brand("TechBooks").description("Comprehensive guide to modern JavaScript programming techniques and best practices.").imageUrl("https://images.pexels.com/photos/1181671/pexels-photo-1181671.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Python for Beginners").category("books").price(34.99).stock(20).brand("CodeLearn").description("Step-by-step guide to learning Python programming from scratch.").imageUrl("https://images.pexels.com/photos/1181244/pexels-photo-1181244.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Digital Marketing Mastery").category("books").price(39.99).stock(18).brand("MarketPro").description("Complete guide to digital marketing strategies and social media growth.").imageUrl("https://images.pexels.com/photos/1181675/pexels-photo-1181675.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Photography Fundamentals").category("books").price(44.99).stock(12).brand("PhotoGuide").description("Learn the art of photography with practical tips and techniques.").imageUrl("https://images.pexels.com/photos/1181467/pexels-photo-1181467.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Business Strategy Guide").category("books").price(54.99).stock(8).brand("BizBooks").description("Advanced strategies for business growth and management success.").imageUrl("https://images.pexels.com/photos/1181263/pexels-photo-1181263.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),

                    // Home & Garden
                    Product.builder().name("Garden Tool Set").category("home").price(89.99).stock(8).brand("GreenThumb").description("Complete set of essential gardening tools including spade, rake, and pruning shears.").imageUrl("https://images.pexels.com/photos/1301856/pexels-photo-1301856.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Coffee Maker Deluxe").category("home").price(199.99).stock(14).brand("BrewMaster").description("Programmable coffee maker with built-in grinder and thermal carafe.").imageUrl("https://images.pexels.com/photos/32640492/pexels-photo-32640492.jpeg").build(),
                    Product.builder().name("Air Purifier Pro").category("home").price(299.99).stock(10).brand("CleanAir").description("HEPA air purifier with smart controls and air quality monitoring.").imageUrl("https://images.pexels.com/photos/4239091/pexels-photo-4239091.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Vacuum Cleaner Robot").category("home").price(449.99).stock(6).brand("AutoClean").description("Smart robot vacuum with mapping technology and app control.").imageUrl("https://images.pexels.com/photos/38325/vacuum-cleaner-carpet-cleaner-housework-housekeeping-38325.jpeg").build(),
                    Product.builder().name("LED Desk Lamp").category("home").price(59.99).stock(28).brand("BrightLight").description("Adjustable LED desk lamp with USB charging port and touch controls.").imageUrl("https://images.pexels.com/photos/1036936/pexels-photo-1036936.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),

                    // Sports
                    Product.builder().name("Yoga Mat").category("sports").price(24.99).stock(30).brand("FitLife").description("Non-slip yoga mat with extra cushioning for comfortable practice.").imageUrl("https://images.pexels.com/photos/868483/pexels-photo-868483.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Dumbbells Set").category("sports").price(149.99).stock(20).brand("StrengthMax").description("Adjustable dumbbells set with multiple weight options for home workouts.").imageUrl("https://images.pexels.com/photos/416717/pexels-photo-416717.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Resistance Bands Kit").category("sports").price(39.99).stock(45).brand("FlexFit").description("Complete resistance bands kit with different resistance levels and accessories.").imageUrl("https://images.pexels.com/photos/4162449/pexels-photo-4162449.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Basketball").category("sports").price(34.99).stock(25).brand("CourtMaster").description("Official size basketball with superior grip and durability.").imageUrl("https://images.pexels.com/photos/1661950/pexels-photo-1661950.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Tennis Racket Pro").category("sports").price(189.99).stock(12).brand("AceTennis").description("Professional tennis racket with carbon fiber frame and premium strings.").imageUrl("https://images.pexels.com/photos/209977/pexels-photo-209977.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),

                    // Beauty & Health
                    Product.builder().name("Skincare Set Premium").category("beauty").price(89.99).stock(22).brand("GlowSkin").description("Complete skincare routine with cleanser, toner, serum, and moisturizer.").imageUrl("https://images.pexels.com/photos/3685538/pexels-photo-3685538.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Electric Toothbrush").category("beauty").price(79.99).stock(30).brand("SmileTech").description("Rechargeable electric toothbrush with multiple cleaning modes and timer.").imageUrl("https://images.pexels.com/photos/4465124/pexels-photo-4465124.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Hair Dryer Professional").category("beauty").price(129.99).stock(18).brand("StylePro").description("Professional hair dryer with ionic technology and multiple heat settings.").imageUrl("https://images.pexels.com/photos/973402/pexels-photo-973402.jpeg").build(),
                    Product.builder().name("Makeup Palette Deluxe").category("beauty").price(64.99).stock(35).brand("ColorMax").description("Professional makeup palette with eyeshadows, blush, and highlighting powders.").imageUrl("https://images.pexels.com/photos/2113855/pexels-photo-2113855.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Essential Oils Set").category("beauty").price(49.99).stock(28).brand("PureScent").description("Collection of premium essential oils for aromatherapy and wellness.").imageUrl("https://images.pexels.com/photos/4160132/pexels-photo-4160132.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),

                    // Automotive
                    Product.builder().name("Car Phone Mount").category("automotive").price(24.99).stock(55).brand("DriveTech").description("Universal car phone mount with 360-degree rotation and secure grip.").imageUrl("https://images.pexels.com/photos/3874334/pexels-photo-3874334.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Tire Pressure Gauge").category("automotive").price(19.99).stock(40).brand("AutoCheck").description("Digital tire pressure gauge for accurate readings and tire maintenance.").imageUrl("https://images.pexels.com/photos/462394/pexels-photo-462394.jpeg").build(),
                    Product.builder().name("Car Emergency Kit").category("automotive").price(79.99).stock(20).brand("SafeDrive").description("Complete emergency kit with jumper cables, flashlight, and first aid supplies.").imageUrl("https://images.pexels.com/photos/1319839/pexels-photo-1319839.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Car Dash Camera").category("automotive").price(149.99).stock(15).brand("RecordMax").description("HD dash camera with night vision and loop recording capabilities.").imageUrl("https://images.pexels.com/photos/7235607/pexels-photo-7235607.jpeg").build(),
                    Product.builder().name("Car Vacuum Cleaner").category("automotive").price(59.99).stock(32).brand("CleanCar").description("Portable car vacuum cleaner with powerful suction and multiple attachments.").imageUrl("https://images.pexels.com/photos/6196228/pexels-photo-6196228.jpeg").build(),

                    // Toys & Games
                    Product.builder().name("Board Game Classic").category("toys").price(34.99).stock(25).brand("FunTime").description("Classic strategy board game for family entertainment and game nights.").imageUrl("https://images.pexels.com/photos/278888/pexels-photo-278888.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("LEGO Building Set").category("toys").price(89.99).stock(18).brand("BuildMax").description("Creative building set with 500+ pieces for endless construction possibilities.").imageUrl("https://images.pexels.com/photos/374820/pexels-photo-374820.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("RC Drone with Camera").category("toys").price(199.99).stock(12).brand("SkyTech").description("Remote control drone with HD camera and GPS return-to-home feature.").imageUrl("https://images.pexels.com/photos/2876511/pexels-photo-2876511.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Puzzle 1000 Pieces").category("toys").price(19.99).stock(30).brand("PuzzlePro").description("Beautiful landscape puzzle with 1000 pieces for hours of entertainment.").imageUrl("https://images.pexels.com/photos/3661193/pexels-photo-3661193.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Gaming Console Controller").category("toys").price(69.99).stock(35).brand("GameMax").description("Wireless gaming controller with ergonomic design and responsive buttons.").imageUrl("https://images.pexels.com/photos/1298601/pexels-photo-1298601.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),

                    // Food & Beverages
                    Product.builder().name("Premium Coffee Beans").category("food").price(24.99).stock(45).brand("RoastMaster").description("Single-origin coffee beans roasted to perfection for rich flavor.").imageUrl("https://images.pexels.com/photos/894695/pexels-photo-894695.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Organic Honey").category("food").price(16.99).stock(38).brand("PureNature").description("Raw organic honey harvested from wildflower meadows.").imageUrl("https://images.pexels.com/photos/1638813/pexels-photo-1638813.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Protein Powder").category("food").price(39.99).stock(28).brand("FitNutrition").description("Whey protein powder with vanilla flavor for muscle building and recovery.").imageUrl("https://images.pexels.com/photos/4162538/pexels-photo-4162538.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Green Tea Premium").category("food").price(18.99).stock(42).brand("ZenLeaf").description("Premium green tea leaves with antioxidants and natural flavor.").imageUrl("https://images.pexels.com/photos/1417945/pexels-photo-1417945.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Dark Chocolate Box").category("food").price(29.99).stock(33).brand("CocoaLux").description("Assorted dark chocolate collection with various flavors and fillings.").imageUrl("https://images.pexels.com/photos/918327/pexels-photo-918327.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),

                    // Additional Electronics
                    Product.builder().name("Portable Speaker").category("electronics").price(89.99).stock(26).brand("SoundMax").description("Waterproof portable speaker with 360-degree sound and 12-hour battery.").imageUrl("https://images.pexels.com/photos/1649771/pexels-photo-1649771.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),
                    Product.builder().name("Tablet 10-inch").category("electronics").price(299.99).stock(19).brand("TabletPro").description("High-resolution 10-inch tablet with fast processor and long battery life.").imageUrl("https://images.pexels.com/photos/1334597/pexels-photo-1334597.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),

                    // Additional Clothing
                    Product.builder().name("Leather Wallet").category("clothing").price(49.99).stock(35).brand("LeatherCraft").description("Genuine leather wallet with multiple card slots and RFID protection.").imageUrl("https://images.pexels.com/photos/1598505/pexels-photo-1598505.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),

                    // Additional Sports
                    Product.builder().name("Fitness Tracker").category("sports").price(129.99).stock(24).brand("FitTrack").description("Advanced fitness tracker with heart rate monitor and sleep tracking.").imageUrl("https://images.pexels.com/photos/437037/pexels-photo-437037.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build(),

                    // Additional Home
                    Product.builder().name("Smart Thermostat").category("home").price(199.99).stock(16).brand("SmartHome").description("WiFi-enabled smart thermostat with app control and energy saving features.").imageUrl("https://images.pexels.com/photos/4239091/pexels-photo-4239091.jpeg?auto=compress&cs=tinysrgb&w=300&h=200&fit=crop").build()

            );
            productRepository.saveAll(products);
        }
    }
}

